package com.example.todo.controller.task;

import java.util.List;

public record TaskSearchDTO(
        String summary,
        List<String> statusList
) {

    public boolean isChecked(String status) {
        return statusList.contains(status);
    }
}
