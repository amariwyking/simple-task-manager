package com.amariwyking.tasks.services.impl;

import com.amariwyking.tasks.domain.entities.Task;
import com.amariwyking.tasks.domain.entities.TaskList;
import com.amariwyking.tasks.domain.entities.TaskPriority;
import com.amariwyking.tasks.domain.entities.TaskStatus;
import com.amariwyking.tasks.repositories.TaskListRepository;
import com.amariwyking.tasks.repositories.TaskRepository;
import com.amariwyking.tasks.services.TaskListService;
import com.amariwyking.tasks.services.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;

    public TaskServiceImpl(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<Task> listTasks(UUID taskListId) {
        return taskRepository.findByTaskListId(taskListId);
    }

    @Override
    public Task createTask(UUID taskListId, Task task) {
        if (null != task.getId()) {
            throw new IllegalArgumentException(String.format("Task already has ID: %s", task.getId()));
        }

        if (null == task.getTitle() || task.getTitle().isBlank()) {
            throw new IllegalArgumentException("Task must have a title");
        }

        TaskPriority priority = Optional.ofNullable(task.getPriority()).orElse(TaskPriority.MEDIUM);

        TaskList taskList = taskListRepository
                .findById(taskListId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid Task List ID provided")
                );

        LocalDateTime now = LocalDateTime.now();
        return taskRepository.save(new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                TaskStatus.OPEN,
                priority,
                taskList,
                now,
                now
        ));
    }

    @Override
    public Optional<Task> getTask(UUID taskListId, UUID taskId) {
        return taskRepository.findByTaskListIdAndId(taskListId, taskId);
    }

    @Transactional
    @Override
    public void deleteTask(UUID taskListId, UUID taskId) {
        taskRepository.deleteByTaskListIdAndId(taskListId, taskId);
    }
}
