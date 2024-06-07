package com.tripod.durust.presentation.home.individuals

import java.time.LocalDate

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.tripod.durust.presentation.datacollection.getTime
import com.tripod.durust.presentation.home.DashboardViewModel

data class TaskListEntity(val tasks: List<TaskEntity> = emptyList())
data class TaskEntity(val id: Long = getTime(), val taskName: String = "", val date: String = LocalDate.now().toString(), var isTaskCompleted: Boolean = false)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TaskList(dashboardViewModel: DashboardViewModel) {
    var isAddingTask by remember { mutableStateOf(false) }
    var newTaskName by remember { mutableStateOf(TextFieldValue("")) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "For today",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )



        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            color = Color(0xFFBBDEFB),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                dashboardViewModel.tasks.forEach { task ->
                    TaskItem(task, dashboardViewModel)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                if (isAddingTask) {
                    OutlinedTextField(
                        value = newTaskName,
                        onValueChange = { newTaskName = it },
                        label = { Text("Task Name",
                                style = TextStyle(
                                color = Color.Gray,
                                fontSize = 14.sp
                                ))},
                        placeholder = { Text("Enter task name here",
                            style = TextStyle(
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                        ) },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Add Task",
                                modifier = Modifier.clickable {
                                    if (newTaskName.text.isNotEmpty()) {
                                        dashboardViewModel.addTask(TaskEntity(
                                            getTime(), newTaskName.text, LocalDate.now().toString(), false
                                        )
                                        )
                                        newTaskName = TextFieldValue("")
                                        isAddingTask = false
                                        dashboardViewModel.uploadTasks()
                                        keyboardController?.hide()
                                    }
                                }
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged { focusState ->
                                if (focusState.isFocused) {
                                    keyboardController?.show()
                                }
                            }
                    )
                } else {
                    TextButton(onClick = { isAddingTask = true }) {
                        Text(
                            text = "+ add more task",
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TaskItem(task: TaskEntity, viewModel: DashboardViewModel) {
    var isTaskCompleted by remember {
        mutableStateOf(task.isTaskCompleted)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isTaskCompleted,
            onCheckedChange = {
                isTaskCompleted = it
                if(task.isTaskCompleted)
                    return@Checkbox
                viewModel.markTaskCompleted(task)
                viewModel.uploadTasks()
            }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = task.taskName,
            fontSize = 14.sp,
            color = Color.Gray,
            textDecoration = if (task.isTaskCompleted) TextDecoration.LineThrough else TextDecoration.None
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TaskListPreview() {
//    TaskList(dashboardViewModel)
}
