package com.taskmanager.server.services

import com.taskmanager.server.entities.Task
import com.taskmanager.server.repositories.TaskRepository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Service
class TaskServices(@Autowired private val repository: TaskRepository) {
    private val logger: Logger = LoggerFactory.getLogger("TaskServices")

    @Scheduled(fixedRate = 5000)
    fun checkDBCon(): String {
        try {
            val count: Long = repository.count()
            return "ACTIVE: $count Tasks"
        } catch (ex: Exception) {
            logger.error("ERROR :: FAILED TO CONNECT TO DATABASE: ${ex.message}"
                + "\n${ex.printStackTrace()}")
            return "FAILED: ${ex.message}"
        }
    }

    fun getTimestamp(): String {
        try {
            val currentTimestamp: Long = java.time.Instant.now().toEpochMilli()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            val instant = java.time.Instant.ofEpochMilli(currentTimestamp)
            val formattedDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter)
            return formattedDateTime
        } catch (ex: Exception) {
            logger.error("ERROR :: FAILED TO GET DATE/TIME: ${ex.message}"
                + "\n${ex.printStackTrace()}")
            return "Failed To Retrieve And Convert Timestamp: ${ex.message}"
        }
    }

    fun createTask(task: Task): Task? {
        try {
            if (task.timeStamp.isEmpty()) task.timeStamp = getTimestamp()
            return repository.save(task)
        } catch (ex: Exception) {
            logger.error("ERROR :: COULD NOT CREATE TASK: ${ex.message}"
                + "\n${ex.printStackTrace()}")
            return null
        }
    }

    fun getAllTasks(): List<Task>? {
        try {
            return repository.findAll()
        } catch (ex: Exception) {
            logger.error("ERROR :: COULD NOT FIND ANY TASKS: ${ex.message}"
                + "\n${ex.printStackTrace()}")
            return null
        }
    }

    fun getTask(id: String): Task? {
        return repository.findById(id)
            .orElseThrow { TaskNotFoundException(id) }
    }

    fun updateTask(id: String, newTask: Task): Task? {
            return repository.findById(id).map { existingTask ->
                existingTask.timeStamp = "UPDATED: ${getTimestamp()}"
                existingTask.title = newTask.title
                existingTask.contents = newTask.contents

                repository.save(existingTask)
            }.orElseThrow { TaskNotFoundException(id) }
    }

    fun deleteTask(id: String): String {
        try {
            repository.deleteById(id)
            return "Deletion Successful"
        } catch (ex: Exception) {
            return "Deletion Failed: ${ex.message}"
        }
    }
}

class TaskNotFoundException(id: String): RuntimeException("Could Not Find Task With ID: $id")

@RestControllerAdvice
class TaskNotFoundAdvice {
    @ExceptionHandler(TaskNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun taskNotFoundHandler(ex: TaskNotFoundException): String {
        return ex.message ?: "Task Not Found!"
    }
}