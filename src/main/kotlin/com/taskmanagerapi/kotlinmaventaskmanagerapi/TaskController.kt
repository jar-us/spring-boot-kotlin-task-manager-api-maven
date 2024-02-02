package com.taskmanagerapi.kotlinmaventaskmanagerapi

import org.springframework.web.bind.annotation.*
import java.util.concurrent.atomic.AtomicLong

@RestController
@RequestMapping("/tasks")
class TaskController {

    private val tasks = mutableListOf<Task>()
    private val taskIdCounter = AtomicLong()

    @PostMapping
    fun createTask(@RequestBody taskRequest: TaskRequest): Task {
        val taskId = taskIdCounter.incrementAndGet()
        val newTask = Task(taskId, taskRequest.title, taskRequest.description, taskRequest.dueDate)
        tasks.add(newTask)
        return newTask
    }

    @GetMapping
    fun getAllTasks(): List<Task> {
        return tasks
    }

    @GetMapping("/{taskId}")
    fun getTaskById(@PathVariable taskId: Long): Task? {
        return tasks.find { it.id == taskId }
    }

    @PutMapping("/{taskId}")
    fun updateTask(@PathVariable taskId: Long, @RequestBody updatedTask: TaskRequest): Task? {
        val existingTask = tasks.find { it.id == taskId }
        if (existingTask != null) {
            existingTask.title = updatedTask.title
            existingTask.description = updatedTask.description
            existingTask.dueDate = updatedTask.dueDate
        }
        return existingTask
    }

    @DeleteMapping("/{taskId}")
    fun deleteTask(@PathVariable taskId: Long): Boolean {
        return tasks.removeIf { it.id == taskId }
    }
}

data class Task(val id: Long, var title: String, var description: String, var dueDate: String)

data class TaskRequest(val title: String, val description: String, val dueDate: String)
