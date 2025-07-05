package com.amariwyking.tasks.controllers;

import com.amariwyking.tasks.domain.dto.TaskListDto;
import com.amariwyking.tasks.domain.entities.TaskList;
import com.amariwyking.tasks.mappers.TaskListMapper;
import com.amariwyking.tasks.services.TaskListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/task-lists")
public class TaskListController {

    private final TaskListService taskListService;
    private final TaskListMapper taskListMapper;

    public TaskListController(TaskListService taskListService, TaskListMapper taskListMapper) {
        this.taskListService = taskListService;
        this.taskListMapper = taskListMapper;
    }

    @GetMapping
    public List<TaskListDto> listTaskLists() {
        return taskListService.listTaskLists()
                .stream()
                .map(taskListMapper::toDto)
                .toList();
    }

    @PostMapping
    public TaskListDto createTaskList(@RequestBody TaskListDto taskListDto) {
        // Convert the incoming Task List DTO into an entity using the mapper and store it in the database via the repo
        TaskList createdTaskList = taskListService.createTaskList(
                taskListMapper.fromDto(taskListDto)
        );

        // Return the newly created task list as a DTO
        return taskListMapper.toDto(createdTaskList);
    }
}
