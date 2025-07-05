package com.amariwyking.tasks.services.impl;

import com.amariwyking.tasks.domain.entities.TaskList;
import com.amariwyking.tasks.repositories.TaskListRepository;
import com.amariwyking.tasks.services.TaskListService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskListServiceImpl implements TaskListService {
    private final TaskListRepository taskListRepository;

    public TaskListServiceImpl(TaskListRepository taskListRepository){
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<TaskList> listTaskLists() {
        return taskListRepository.findAll();
    }
}
