package com.tripod.durust.presentation.home

import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.tripod.durust.domain.repositories.TaskRepository
import com.tripod.durust.presentation.MainActivity
import com.tripod.durust.presentation.home.individuals.TaskEntity
import com.tripod.durust.presentation.home.individuals.TaskListEntity
import kotlinx.coroutines.launch

class DashboardViewModel(val auth: FirebaseAuth): ViewModel() {
    private val taskRepository = TaskRepository(auth)
    var tasks by MainActivity.tasks

    fun addTask(task: TaskEntity) {
        val currentTasks = tasks.toMutableList()
        currentTasks.add(task)
        tasks = currentTasks
        uploadTasks()
    }

    fun markTaskCompleted(task: TaskEntity) {
        val currentTasks = tasks.toMutableList()
        val taskIndex = currentTasks.indexOfFirst { it.id == task.id }
        currentTasks[taskIndex] = task.copy(isTaskCompleted = true)
        tasks = currentTasks
    }

    fun uploadTasks() = viewModelScope.launch {
        val result = taskRepository.uploadTasks(TaskListEntity(MainActivity.tasks.value))
        if (!result) {
            // Handle the error
            Log.e("DashboardViewModel", "Error uploading tasks")
        }
    }

    fun showSnackbar(snackbarHostState: SnackbarHostState,message: String) {
        viewModelScope.launch {
            snackbarHostState.showSnackbar(message)
        }
    }
}

class DashboardViewModelFactory(val auth: FirebaseAuth): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(auth) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}