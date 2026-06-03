package com.example.todo.service.task;

import com.example.todo.repository.tasks.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<TaskEntity> find(TaskSearchEntity taskSearchEntity) {
        return taskRepository.select();
    }

    public Optional<TaskEntity> findById(long taskId) {
        return taskRepository.selectById(taskId);
    }

    /**
     * タスク作成
     * @param newEntity
     */
    @Transactional
    public void create(TaskEntity newEntity) {
        taskRepository.insert(newEntity);
    }

    /**
     * タスク更新
     * @param entity
     */
    @Transactional
    public void update(TaskEntity entity) {
        taskRepository.update(entity);
    }

    /**
     * タスク削除
     * @param id
     */
    @Transactional
    public void delete(long id) {
        taskRepository.delete(id);
    }
}
