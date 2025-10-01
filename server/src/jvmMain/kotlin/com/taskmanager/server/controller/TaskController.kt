package com.taskmanager.server.controller

import com.taskmanager.server.entities.Task
import com.taskmanager.server.services.TaskNotFoundException
import com.taskmanager.server.services.TaskServices

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
    fun checkConnection(): ResponseEntity<String> {
        return try {
            val con: String = taskServices.checkDBCon()
            ResponseEntity.ok("API : Connection Is Active\nDATABASE : CONNECTION[${con}]")
        } catch (ex: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed To Get Connection Status: ${ex.message}")
        }
    }

    @PostMapping("/create-task")
    fun createTask(@RequestBody task: Task): ResponseEntity<Task> {
        return try {
            val task = taskServices.createTask(task)
            ResponseEntity.ok(task)
        } catch (ex: TaskNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        }

    }

    @GetMapping("/tasks")
    fun getAllTasks(): ResponseEntity<List<Task>> {
        return try {
            val tasks = taskServices.getAllTasks()
            ResponseEntity.ok(tasks)
        } catch (ex: Exception) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        }

    }

    @GetMapping("/tasks/{id}")
    fun getTask(@PathVariable id: String): ResponseEntity<Task> {
        return try {
            val task = taskServices.getTask(id)
            ResponseEntity.ok(task)
        } catch (ex: TaskNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        }
    }

    @PutMapping("/tasks/{id}")
    fun updateTask(@PathVariable id: String, @RequestBody newTask: Task): ResponseEntity<Task> {
        return try {
            val task = taskServices.updateTask(id, newTask)
            ResponseEntity.ok(task)
        } catch (ex: TaskNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        }

    }

    @DeleteMapping("/tasks/{id}")
    fun deleteTask(@PathVariable id: String): ResponseEntity<out String?> {
        return try {
            val task = taskServices.deleteTask(id)
            ResponseEntity.ok(task)
        } catch (ex: TaskNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        }
    }
}