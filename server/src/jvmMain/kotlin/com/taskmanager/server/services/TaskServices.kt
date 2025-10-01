package com.taskmanager.server.services

import com.taskmanager.server.entities.Task
import com.taskmanager.server.repositories.TaskRepository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@Service
class TaskServices(@Autowired private val repository: TaskRepository) {

    fun createTask(task: Task): String {
        try {
            repository.save(task)
            return "Successfully Saved Task"
        } catch (ex: Exception) {
            return "Failed To Delete Task: ${ex.message}"
        }
    }

    fun getTask(id: String): Task {
        return repository.findById(id)
            .orElseThrow { TaskNotFoundException(id) }
    }

    fun getAllTasks(): List<Task> {
        return repository.findAll()
    }

    fun updateTask(id: String, newTask: Task): String {
        try {
            repository.findById(id).map { existingTask ->
                existingTask.timeStamp = "UPDATED: ${newTask.timeStamp}"
                existingTask.title = newTask.title
                existingTask.contents = newTask.contents

                repository.save(existingTask)
            }.orElseThrow { TaskNotFoundException(id) }
            return "Successfully Updated Task!"
        } catch (ex: Exception) {
            return "Failed To Update Task: ${ex.message}"
        }
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