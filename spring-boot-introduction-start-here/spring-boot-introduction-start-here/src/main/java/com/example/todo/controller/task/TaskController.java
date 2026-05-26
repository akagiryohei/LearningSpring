package com.example.todo.controller.task;

import com.example.todo.service.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    /**
     * タスク一覧画面表示
     * @param model
     * @return
     */
    @GetMapping
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
    @GetMapping("/{id}") // GET /tasks/${id}
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
    @GetMapping("/creationForm")
    public String showCreationForm() {
        return "tasks/form";
    }

    /**
     * タスク作成
     * @param form
     * @return
     */
    // POST /tasks/
    @PostMapping
    public String create(@Validated TaskForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "tasks/form";

        }
        taskService.create(form.toEntity());
        return "redirect:/tasks";
    }
}
