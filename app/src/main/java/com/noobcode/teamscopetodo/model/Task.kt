package com.noobcode.teamscopetodo.model

import java.io.Serializable

class Task(name: String = "", priority: Int = 0, isCompleted: Boolean = false) : Serializable {

    var name: String = name
    var priority: Int = priority
    var isCompleted: Boolean = isCompleted

    constructor() : this("", 0, false)
}