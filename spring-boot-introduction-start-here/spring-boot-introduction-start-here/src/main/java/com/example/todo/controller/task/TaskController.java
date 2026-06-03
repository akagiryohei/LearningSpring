package com.example.todo.controller.task;

import com.example.todo.service.task.TaskSearchEntity;
import com.example.todo.service.task.TaskService;
import com.example.todo.service.task.TaskStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String list(TaskSearchForm searchForm, Model model) {
        List<TaskStatus> statusList =  searchForm
                .status()
                .stream()
                .map(TaskStatus::valueOf)
                .toList();

//        Udemyの記載方法（赤木は型の指定を変数にしたかったので修正した）
//        var statusEntityList = Optional.ofNullable(searchForm.status())
//                .map(statusList -> statusList.stream().map(TaskStatus::valueof).toList())
//                .orElse(List.Of());

        TaskSearchEntity searchEntity = new TaskSearchEntity(searchForm.summary(), statusList);
        // リスト型での初期化をしている
        var taskList = taskService.find(searchEntity) // List<TaskEntity> -> List<TaskDTO>
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

    /**
     * タスク更新
     * @param id
     * @param form
     * @param bindingResult
     * @param model
     * @return
     */
    // PUT /tasks/{id}
    @PutMapping("{id}")
    public String update(
            @PathVariable("id") long id,
            @Validated @ModelAttribute TaskForm form,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("mode", "EDIT");
            return "tasks/form";
        }
        var entity = form.toEntity(id);
        taskService.update(entity);
        return "redirect:/tasks/{id}";
    }

    /**
     * タスク削除
     * @param id
     * @return
     */
    // DELETE /tasks/1
    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") long id) {
        taskService.delete(id);
        return "redirect:/tasks";
    }


}
