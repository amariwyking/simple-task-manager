package com.amariwyking.tasks.mappers.impl;

import com.amariwyking.tasks.domain.dto.TaskListDto;
import com.amariwyking.tasks.domain.entities.Task;
import com.amariwyking.tasks.domain.entities.TaskList;
import com.amariwyking.tasks.domain.entities.TaskStatus;
import com.amariwyking.tasks.mappers.TaskMapper;

import java.util.List;
import java.util.Optional;

public class TaskListMapperImpl {

    private final TaskMapper taskMapper;

    public TaskListMapperImpl(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    public TaskList fromDto(TaskListDto taskListDto) {
        return new TaskList(
                taskListDto.id(),
                taskListDto.title(),
                taskListDto.description(),
                null,
                null,
                Optional.ofNullable(taskListDto.tasks())
                        .map(tasks -> tasks.stream()
                                .map(taskMapper::fromDto)
                                .toList())
                        .orElse(null));
    }

    public TaskListDto toDto(TaskList taskList) {
        final List<Task> tasks = taskList.getTasks();
        return new TaskListDto(
                taskList.getId(),
                taskList.getTitle(),
                taskList.getDescription(),
                Optional.ofNullable(tasks).map(
                        List::size
                ).orElse(0),
                calculateTaskListProgress(tasks),
                Optional.ofNullable(tasks).map(t -> t.stream().map(
                                taskMapper::toDto
                        ).toList())
                        .orElse(null)
        );
    }

    private Double calculateTaskListProgress(List<Task> tasks) {
        if (null == tasks) {
            return null;
        }

        long closedTaskCount = tasks.stream().filter(task -> TaskStatus.CLOSED == task.getStatus()).count();
        return (double) closedTaskCount / tasks.size();
    }
}
