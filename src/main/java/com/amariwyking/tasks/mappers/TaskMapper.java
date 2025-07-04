package com.amariwyking.tasks.mappers;

import com.amariwyking.tasks.domain.dto.TaskDto;
import com.amariwyking.tasks.domain.entities.Task;

public interface TaskMapper {

    Task fromDto(TaskDto taskDto);

    TaskDto toDto(Task task);
}
