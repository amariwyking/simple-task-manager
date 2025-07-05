package com.amariwyking.tasks.services;

import com.amariwyking.tasks.domain.entities.TaskList;

import java.util.List;

public interface TaskListService {
    List<TaskList> listTaskLists();
}
