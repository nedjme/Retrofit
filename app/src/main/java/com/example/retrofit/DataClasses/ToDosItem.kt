package com.example.taskmanager

data class ToDosItem(
    var completed: Boolean = false,
    var id: Int = -1,
    var title: String = "",
    val userId: Int = 5
)