package com.taskmanager.server.repositories

import com.taskmanager.server.entities.Task
import org.springframework.data.mongodb.repository.*

interface TaskRepository: MongoRepository<Task, String> {

    @Query("{name: '?0'}")
    fun Task.findByName(name: String)

}