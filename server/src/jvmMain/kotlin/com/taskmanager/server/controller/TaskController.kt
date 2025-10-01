package com.taskmanager.server.controller

import com.taskmanager.server.entities.Task
import com.taskmanager.server.services.TaskServices

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class TaskController(@Autowired val taskServices: TaskServices) {

    @GetMapping("/con")
    fun checkConnection(): String {
        try {
            val con: String = taskServices.checkDBCon()
            return "API : Connection Is Active\nDATABASE : CONNECTION[${con}]"
        } catch (ex: Exception) {
            return "Failed To Get Connection Status: ${ex.message}"
        }
    }

    @PostMapping("/create-task")
    fun createTask(@RequestBody task: Task): Task? {
        return taskServices.createTask(task)

    }

    @GetMapping("/tasks")
    fun getAllTasks(): List<Task>? {
        return taskServices.getAllTasks()

    }

    @GetMapping("/tasks/{id}")
    fun getTask(@PathVariable id: String): Task? {
        return taskServices.getTask(id)

    }

    @PutMapping("/tasks/{id}")
    fun updateTask(@PathVariable id: String, @RequestBody newTask: Task) {
        return taskServices.updateTask(id, newTask)

    }

    @DeleteMapping("/tasks/{id}")
    fun deleteTask(@PathVariable id: String): String {
        return taskServices.deleteTask(id)

    }

}