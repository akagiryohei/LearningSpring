package com.example.todo.controller.task;

import com.example.todo.service.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/tasks")
    public String list(Model model) {
        // リスト型での初期化をしている
        var taskList = taskService.find() // List<TaskEntity> -> List<TaskDTO>
                .stream()
                // 1件ずつ取り出してTaskDTOに渡している
                // ラムダ式で書かれてる
                .map(TaskDTO::toDTO)
                .toList();

        // modelにtaskインスタンスを渡している
        model.addAttribute("taskList", taskList);
        return "tasks/list";
    }

    @GetMapping("/tasks/detail") // GET /tasks/${id}将来的にはこのようになる
    public String showDetail() {
        return "tasks/detail";
    }
}
