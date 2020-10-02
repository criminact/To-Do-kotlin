package com.noobcode.teamscopetodo.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.noobcode.teamscopetodo.model.User

class UserRepo {

    private var userData: MutableLiveData<User> = MutableLiveData()
    private var isSetSuccess: MutableLiveData<Boolean> = MutableLiveData()
    private var databaseRef: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun updateTaskData(
        id: String,
        oldTask: com.noobcode.teamscopetodo.model.Task,
        newTask: com.noobcode.teamscopetodo.model.Task
    ): LiveData<Boolean> {
        databaseRef.collection("users").document(id).update("tasks", FieldValue.arrayRemove(oldTask))
            .addOnCompleteListener(OnCompleteListener {
                if (it.isSuccessful) {
                    databaseRef.collection("users").document(id)
                        .update("tasks", FieldValue.arrayUnion(newTask))
                        .addOnCompleteListener(OnCompleteListener {
                            if (it.isSuccessful) {
                                isSetSuccess.postValue(true)
                            } else {
                                isSetSuccess.postValue(false)
                            }
                        })
                        .addOnFailureListener(OnFailureListener {
                            isSetSuccess.postValue(false)
                        })
                } else {
                    isSetSuccess.postValue(false)
                }
            })
            .addOnFailureListener(OnFailureListener {
                isSetSuccess.postValue(false)
            })
        return isSetSuccess
    }

    fun deleteTaskData(id: String, task: com.noobcode.teamscopetodo.model.Task): LiveData<Boolean> {
        databaseRef.collection("users").document(id).update("tasks", FieldValue.arrayRemove(task))
            .addOnCompleteListener(OnCompleteListener {
                if (it.isSuccessful)
                    isSetSuccess.postValue(true)
                else
                    isSetSuccess.postValue(false)
            })
            .addOnFailureListener(OnFailureListener {
                isSetSuccess.postValue(false)
            })
        return isSetSuccess
    }

    fun setTaskData(id: String, task: com.noobcode.teamscopetodo.model.Task): LiveData<Boolean> {
        databaseRef.collection("users").document(id).update("tasks", FieldValue.arrayUnion(task))
            .addOnCompleteListener(OnCompleteListener {
                if (it.isSuccessful)
                    isSetSuccess.postValue(true)
                else
                    isSetSuccess.postValue(false)
            })
            .addOnFailureListener(OnFailureListener {
                isSetSuccess.postValue(false)
            })
        return isSetSuccess
    }

    fun setUserData(user: User): LiveData<Boolean> {
        databaseRef.collection("users").document(user.firebaseUid).set(user)
            .addOnCompleteListener(OnCompleteListener {
                if (it.isSuccessful)
                    isSetSuccess.postValue(true)
                else
                    isSetSuccess.postValue(false)
            })
            .addOnFailureListener(OnFailureListener {
                isSetSuccess.postValue(false)
            })
        return isSetSuccess
    }

    fun getUserData(id: String): LiveData<User> {
        databaseRef.collection("users").document(id).get()
            .addOnCompleteListener(OnCompleteListener(fun(task: Task<DocumentSnapshot>) {
                if (task.isSuccessful) {
                    userData.postValue(task.result?.toObject(User::class.java))
                } else {
                    userData.postValue(null)
                }
            }))
            .addOnFailureListener(OnFailureListener {
                userData.postValue(null)
            })

        return userData
    }

}