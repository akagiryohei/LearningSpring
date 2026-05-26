package com.example.todo.controller.task;

import com.example.todo.service.task.TaskEntity;
import com.example.todo.service.task.TaskService;
import com.example.todo.service.task.TaskStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    /**
     * タスク一覧画面表示
     * @param model
     * @return
     */
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

    /**
     * タスク詳細画面表示
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/tasks/{id}") // GET /tasks/${id}
    public String showDetail(@PathVariable("id") long id, Model model) {
        var taskEntity = taskService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found: id = " + id));
        model.addAttribute("task", TaskDTO.toDTO(taskEntity));
        return "tasks/detail";
    }

    /**
     * タスク作成画面表示
     * @return
     */
    // GET /tasks/creationForm
    @GetMapping("/tasks/creationForm")
    public String showCreationForm() {
        return "tasks/form";
    }

    /**
     * タスク作成
     * @param model
     * @return
     */
    // POST /tasks/
    @PostMapping("/tasks")
    public String create(TaskForm form, Model model) {
        var newEntity = new TaskEntity(null, form.summary(), form.description(), TaskStatus.valueOf(form.status()));
        taskService.create(newEntity);
        return this.list(model);
    }
}
