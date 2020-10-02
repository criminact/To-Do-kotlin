package com.noobcode.teamscopetodo.model

import java.io.Serializable

class User(
    tasks: ArrayList<Task> = ArrayList(),
    firebaseUid: String = ""
) : Serializable {

    var tasks: ArrayList<Task> = tasks
    var firebaseUid: String = firebaseUid

    constructor() : this(ArrayList(), "")
}