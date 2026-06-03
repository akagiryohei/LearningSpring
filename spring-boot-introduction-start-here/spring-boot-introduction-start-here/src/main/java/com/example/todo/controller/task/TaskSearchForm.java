package com.example.todo.controller.task;

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
}
