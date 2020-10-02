package com.noobcode.teamscopetodo.interfaces

import com.noobcode.teamscopetodo.model.Task

interface TaskGestureControl {
    fun taskSwitch(task: Task, newTask: Task)
    fun longPressed(task: Task)
    fun clicked(task: Task)
    fun loadedData()
}