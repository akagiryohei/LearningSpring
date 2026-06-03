package com.example.todo.repository.tasks;

import com.example.todo.service.task.TaskEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface TaskRepository {
    @Select("SELECT id, summary, description, status FROM tasks;")
    List<TaskEntity> select();

    @Select("SELECT id, summary, description, status FROM tasks WHERE id = #{taskId}")
    Optional<TaskEntity> selectById(@Param("taskId") long taskId);

    @Insert("""
    INSERT INTO TASKS (summary, description, status)
    VALUES (#{task.summary}, #{task.description}, #{task.status})
    """)
    void insert(@Param("task") TaskEntity newEntity);

    @Update(
            """
            UPDATE tasks
            SET
                summary = #{task.summary},
                description = #{task.description},
                status = #{task.status}
            WHERE
                ID = #{task.id}
            """
    )
    void update(@Param("task") TaskEntity entity);

    @Delete("DELETE tasks WHERE id = #{id}")
    void delete(@Param("id") long id);
}
