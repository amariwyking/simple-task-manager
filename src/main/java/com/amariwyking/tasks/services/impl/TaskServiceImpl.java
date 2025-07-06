package com.amariwyking.tasks.services.impl;

import com.amariwyking.tasks.domain.entities.Task;
import com.amariwyking.tasks.repositories.TaskListRepository;
import com.amariwyking.tasks.repositories.TaskRepository;
import com.amariwyking.tasks.services.TaskListService;
import com.amariwyking.tasks.services.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
