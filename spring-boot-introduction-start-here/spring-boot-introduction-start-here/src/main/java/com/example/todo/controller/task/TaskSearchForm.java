package com.example.todo.controller.task;

import com.example.todo.service.task.TaskSearchEntity;
import com.example.todo.service.task.TaskStatus;

import java.util.List;

public record TaskSearchForm(
        String summary,
        List<String> status
) {
    public TaskSearchForm {
        if (status == null) {
            status = List.of();
        }
    }

    public TaskSearchEntity toEntity() {
        List<TaskStatus> statusList = status()
                .stream()
                .map(TaskStatus::valueOf)
                .toList();

//        Udemyの記載方法（赤木は型の指定を変数にしたかったので修正した）
//        var statusEntityList = Optional.ofNullable(searchForm.status())
//                .map(statusList -> statusList.stream().map(TaskStatus::valueof).toList())
//                .orElse(List.Of());
        return new TaskSearchEntity(summary(), statusList);
    }

    public TaskSearchDTO toDTO() {
        return new TaskSearchDTO(summary(), status());
    }
}
