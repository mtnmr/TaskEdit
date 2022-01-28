package com.example.taskedit

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class TodoList: RealmObject() {
    @PrimaryKey
    var id:Long = 0
    var num:Int = 0
    var todoText :String = ""
    var status: String = ""

}
