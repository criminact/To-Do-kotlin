package com.noobcode.teamscopetodo.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.noobcode.teamscopetodo.model.Task
import com.noobcode.teamscopetodo.repo.UserRepo

class AddTaskViewModel: ViewModel() {

    private var userRepo: UserRepo = UserRepo()

    fun setTask(id: String, task: Task): LiveData<Boolean> {
        return userRepo.setTaskData(id, task)
    }

    fun updateTask(id: String, oldTask: Task, newTask: Task): LiveData<Boolean> {
        return userRepo.updateTaskData(id, oldTask, newTask)
    }

}