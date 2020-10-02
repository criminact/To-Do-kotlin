package com.noobcode.teamscopetodo.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.noobcode.teamscopetodo.model.Task
import com.noobcode.teamscopetodo.model.User
import com.noobcode.teamscopetodo.repo.UserRepo

class AllTaskViewModel : ViewModel() {

    private var userRepo: UserRepo = UserRepo()

    fun getUserData(id: String): LiveData<User> {
        return userRepo.getUserData(id)
    }

    fun changeTaskData(id: String, oldTask: Task, newTask: Task): LiveData<Boolean> {
        return userRepo.updateTaskData(id, oldTask, newTask)
    }

    fun deleteTask(id: String, task: Task): LiveData<Boolean> {
        return userRepo.deleteTaskData(id, task)
    }

}