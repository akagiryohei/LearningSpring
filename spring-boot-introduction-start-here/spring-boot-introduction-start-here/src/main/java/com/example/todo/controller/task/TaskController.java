package com.example.todo.controller.task;

import com.example.todo.service.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        var taskDTO = taskService.findById(id)
                .map(TaskDTO::toDTO)
                .orElseThrow(TaskNotFoundException::new);
        model.addAttribute("task", taskDTO);
        return "tasks/detail";
    }

    /**
     * タスク作成画面表示
     * @return
     */
    // GET /tasks/creationForm
    @GetMapping("/creationForm")
    public String showCreationForm(@ModelAttribute TaskForm form, Model model) {
        model.addAttribute("mode", "CREATE");
        return "tasks/form";
    }

    /**
     * タスク作成
     * @param form
     * @return
     */
    // POST /tasks/
    @PostMapping
    public String create(@Validated TaskForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return showCreationForm(form, model);
        }
        taskService.create(form.toEntity());
        return "redirect:/tasks";
    }

    /**
     * タスク修正画面表示
     * @param id
     * @param model
     * @return
     */
    // GET /tasks/{taskId}/editForm
    @GetMapping("{id}/editForm")
    public String showEditForm(@PathVariable("id") long id, Model model) {
        var form = taskService.findById(id)
                .map(TaskForm::formEntity)
                        .orElseThrow(TaskNotFoundException::new);
        model.addAttribute("taskForm", form);
        model.addAttribute("mode", "EDIT");
        return "tasks/form";
    }

}
