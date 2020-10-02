package com.noobcode.teamscopetodo.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.noobcode.teamscopetodo.model.User
import com.noobcode.teamscopetodo.repo.UserRepo

class FeaturesViewModel : ViewModel() {

    private var userRepo: UserRepo = UserRepo()

    fun setUserData(user: User): LiveData<Boolean> {
        return userRepo.setUserData(user)
    }

}