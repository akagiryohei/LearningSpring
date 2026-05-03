package com.example.todo.service.task;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TaskService {

    public List<TaskEntity> find() {
        // タスクのインスタンス生成
        // タスクのDTOを初期化してそのタスクをviewに渡してる
        // 1Lの理由：可読性とバグ防止のため
        var task1 = new TaskEntity(1L,
                "Spring Bootを学ぶ",
                "TODO アプリケーションを作ってみる",
                TaskStatus.TODO
        );
        var task2 = new TaskEntity(2L,
                "Spring Securityを学ぶ",
                "TODO アプリケーションを作ってみる",
                TaskStatus.DOING
        );
        return List.of(task1, task2);
    }
}
