package com.taskmanager.server.repositories

import com.taskmanager.server.entities.Task
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.repository.MongoRepository

interface TaskRepository: MongoRepository<Task, String> {

    fun findByTitle(title: String): Task?

}