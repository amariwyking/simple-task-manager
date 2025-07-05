package com.amariwyking.tasks.services.impl;

import com.amariwyking.tasks.domain.entities.TaskList;
import com.amariwyking.tasks.mappers.TaskListMapper;
import com.amariwyking.tasks.repositories.TaskListRepository;
import com.amariwyking.tasks.services.TaskListService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskListServiceImpl implements TaskListService {
    private final TaskListRepository taskListRepository;
    private final TaskListMapper taskListMapper;

    public TaskListServiceImpl(TaskListRepository taskListRepository, TaskListMapper taskListMapper){
        this.taskListRepository = taskListRepository;
        this.taskListMapper = taskListMapper;
    }

    @Override
    public List<TaskList> listTaskLists() {
        return taskListRepository.findAll();
    }

    @Override
    public TaskList createTaskList(TaskList taskList) {
        if (taskList.getId() != null) {
            throw new IllegalArgumentException(String.format("Task list already has ID of %s", taskList.getId()));
        }

        if (taskList.getTitle() == null || taskList.getTitle().isBlank()) {
            throw new IllegalArgumentException(String.format("Task list title must be provided"));
        }

        LocalDateTime now = LocalDateTime.now();
        return taskListRepository.save(new TaskList(
                null,
                taskList.getTitle(),
                taskList.getDescription(),
                null,
                now,
                now
        ));
    }

    @Override
    public TaskList updateTaskList(UUID taskListId, TaskList taskList) {
        if (taskList.getId() == null) {
            throw new IllegalArgumentException(String.format("Task list must have an ID", taskList.getId()));
        }

        if(!Objects.equals(taskList.getId().toString(), taskListId.toString())) {
            throw new IllegalArgumentException(
                    "Attempting to change task list ID, this is not permitted!"
            );
        }

        TaskList existingTaskList = taskListRepository.findById(taskListId)
                .orElseThrow(() ->
                        new IllegalStateException("Task list not found!")
        );

        existingTaskList.setTitle(taskList.getTitle());
        existingTaskList.setDescription((taskList.getDescription()));
        existingTaskList.setUpdated(LocalDateTime.now());

        return taskListRepository.save(existingTaskList);
    }

    @Override
    public Optional<TaskList> getTaskList(UUID id) {
        return taskListRepository.findById(id);
    }
}
