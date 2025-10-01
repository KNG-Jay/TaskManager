package com.taskmanager.server.repositories

import com.taskmanager.server.entities.Task
import org.springframework.data.mongodb.repository.MongoRepository

// TODO: ( IMPLEMENT COROUTINE DRIVER )
interface TaskRepository: MongoRepository<Task, String> {

    fun findByTitle(title: String): Task?

}
