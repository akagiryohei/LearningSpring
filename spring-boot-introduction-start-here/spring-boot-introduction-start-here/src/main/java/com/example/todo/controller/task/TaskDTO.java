package com.example.todo.controller.task;

import com.example.todo.service.task.TaskEntity;

// レコード型で作る
// プレゼンテーション層用の物(HTTPリクエストを受け取って返す層)
public record TaskDTO(
        long id,
        String summary,
        String description,
        String status
) {
    public static TaskDTO toDTO(TaskEntity entity) {
        return new TaskDTO(
                entity.id(),
                entity.summary(),
                entity.description(),
                entity.status().name());
    }
}
