package com.example.todo.service.task;

// ドメイン層/ビジネスロジック層用の物
public record TaskEntity(
        long id,
        String summary,
        String description,
        TaskStatus status
) {
}
