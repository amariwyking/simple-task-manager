package com.amariwyking.tasks.mappers;

import com.amariwyking.tasks.domain.dto.TaskListDto;
import com.amariwyking.tasks.domain.entities.TaskList;

public interface TaskListMapper {
    TaskList fromDto(TaskListDto taskListDto);

    TaskListDto toDto(TaskList taskList);
}
