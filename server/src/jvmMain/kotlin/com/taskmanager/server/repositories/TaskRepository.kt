package com.taskmanager.server.repositories

import com.taskmanager.server.entities.Task
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

// TODO: ( IMPLEMENT COROUTINE DRIVER )
@Repository
interface TaskRepository: MongoRepository<Task, String> {

    fun findByTitle(title: String): Task?

}
