package com.tripod.durust.presentation

import ChatInputTextField
import ChatRow
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tripod.durust.data.AppointmentEntity
import com.tripod.durust.data.DateEntity
import com.tripod.durust.presentation.chats.GeminiViewModel
import com.tripod.durust.presentation.chats.data.BotComponent
import com.tripod.durust.presentation.chats.data.BotUiState
import com.tripod.durust.presentation.chats.individual.AppointmentMenuBase
import com.tripod.durust.presentation.chats.individual.MedicineFrequencyList
import com.tripod.durust.presentation.chats.individual.MedicineTimeList
import com.tripod.durust.presentation.datacollection.DatePickerCard
import com.tripod.durust.presentation.datacollection.TimeEntity
import com.tripod.durust.presentation.datacollection.TimePickerBase
import com.tripod.durust.presentation.datacollection.getTime
import com.tripod.durust.presentation.datacollection.utcToDateEntity
import com.tripod.durust.presentation.home.individuals.BasicAlertDialog
import com.tripod.durust.presentation.home.individuals.DoctorProfession
import java.time.LocalDateTime

fun getUpcomingAppointments(appointments: List<AppointmentEntity>): List<String> {
    val upcomingAppointments = mutableListOf<String>()
    val now = LocalDateTime.now()

    appointments.forEach { appointment ->
        val appointmentDateTime = LocalDateTime.of(
            appointment.date.year,
            appointment.date.month,
            appointment.date.day,
            appointment.time.hour,
            appointment.time.minute
        )

        if (appointmentDateTime.isAfter(now)) {
            val date = "${appointment.date.day}/${appointment.date.month}/${appointment.date.year}"
            val time = "${appointment.time.hour}:${appointment.time.minute}"
            val details = "$date at $time:\n Appointment with ${appointment.doctorName} who is a ${appointment.doctorSpeciality.professionName}\n\n"
            upcomingAppointments.add(details)
        }
    }

    return upcomingAppointments
}

fun getAppointmentDetails(appointments: List<AppointmentEntity>): List<String> {
    val appointmentDetails = mutableListOf<String>()

    appointments.forEach { appointment ->
        val date = "${appointment.date.day}/${appointment.date.month}/${appointment.date.year}"
        val time = "${appointment.time.hour}:${appointment.time.minute}"
        val details = "$date at $time: \nAppointment with ${appointment.doctorName} who is a ${appointment.doctorSpeciality.professionName}\n\n"
        appointmentDetails.add(details)
    }

    return appointmentDetails
}

@Composable
fun AppointmentHistoryMainUI(
    botComponent: BotComponent.AppointmentHistoryState?,
    viewModel: GeminiViewModel,
    isActive: Boolean
) {
    val upcomingAppointments = getUpcomingAppointments(MainActivity.appointments.value ?: emptyList())
    val appointmentDetails = getAppointmentDetails(MainActivity.appointments.value?: emptyList())

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
//        ChatRow(chatText = "Here are your upcoming appointments:")
//        upcomingAppointments.forEach { appointment ->
//            ChatRow(chatText = appointment)
//        }

        ChatRow(chatText = "Here are your past appointments:")
        appointmentDetails.forEach { appointment ->
            ChatRow(chatText = appointment)
        }

        viewModel.addHistory(BotComponent.GeminiResponseState(getTime().toString(), "All Appointments: ${appointmentDetails.joinToString("")}"))
        if(isActive)
            viewModel.chatUiState.value = BotUiState.APPOINTMENT_LIST
    }
}

@Composable
fun AppointmentUpcomingMainUI(
    botComponent: BotComponent.UpcomingAppointmentsState?,
    viewModel: GeminiViewModel,
    isActive: Boolean
) {
    val upcomingAppointments = getUpcomingAppointments(MainActivity.appointments.value ?: emptyList())
    val appointmentDetails = getAppointmentDetails(MainActivity.appointments.value?: emptyList())

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        ChatRow(chatText = "Here are your upcoming appointments:")
        upcomingAppointments.forEach { appointment ->
            ChatRow(chatText = appointment)
        }

        viewModel.addHistory(BotComponent.GeminiResponseState(getTime().toString(), "Upcoming Appointments: ${upcomingAppointments.joinToString("")}"))
        if(isActive)
            viewModel.chatUiState.value = BotUiState.APPOINTMENT_LIST
//        ChatRow(chatText = "Here are your past appointments:")
//        appointmentDetails.forEach { appointment ->
//            ChatRow(chatText = appointment)
//        }
    }
}



@Composable
fun AppointmentMenuMainUI(
    botComponent: BotComponent.AppmtList?,
    viewModel: GeminiViewModel,
    isActive: Boolean
) {
    var isSelected by remember {
        mutableStateOf(false)
    }
    var selectedOption by remember {
        mutableStateOf(botComponent?.appList)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        ChatRow(
            chatText = if (viewModel.history.value
                    .isEmpty() && isActive
            ) "Hello! welcome back!\nHow may I help you " else "I can help you with"
        )
        AppointmentMenuBase(
            isEnabled = isActive,
            initialSelectedOption = selectedOption,
            onOptionSelected = {
                selectedOption = it
                isSelected = true
                viewModel.onAppointmentListDisplay(it)
            }
        )
    }
}

@Composable
fun DEAppointmentDateMainUI(
    botComponent: BotComponent.AppmtDate?,
    viewModel: GeminiViewModel,
    isActive: Boolean
) {
    // Step 1: Create a mutable state for the selected date
    var selectedDate by remember {
        mutableStateOf(botComponent?.date)
    }

    // Step 2: Display a message to the user
    ChatRow(chatText = "Please select the appointment date")

    // Step 3: Create a UI component to get the user's input
    // This could be a DatePicker or any other input field
    DatePickerCard(
        initialDate = selectedDate ?: utcToDateEntity(getTime()),
        isEnabled = isActive,
        onDateChanged = { newDate ->
            // Step 4: Update the selected date when the user selects a new date
            selectedDate = newDate
            viewModel.data.value = viewModel.data.value.copy(appDate = newDate)
        }
    )
}


@Composable
fun DEAppointmentTimeMainUI(
    botComponent: BotComponent.AppmtTime?, // Replace with your actual BotComponent state
    viewModel: GeminiViewModel,
    isActive: Boolean
) {
    // Step 1: Create a mutable state for the selected time
    var selectedTime by remember {
        mutableStateOf(botComponent?.time)
    }

    // Step 2: Display a message to the user
    ChatRow(chatText = "Please select a time for the appointment")

    // Step 3: Create a UI component to get the user's response
    // This could be a custom composable that displays a time picker
    // For this example, let's assume you have a composable named `TimePickerBase`
    TimePickerBase(
        initialTime = selectedTime ?: TimeEntity(0, 0),
        isEnabled = isActive,
        onTimeChange = {
            // Step 4: Update the selected time when the user selects a new time
            selectedTime = it
            viewModel.data.value = viewModel.data.value.copy(appTime = it) // Replace with your actual data model
        }
    )
}

@Composable
fun DEAppointmentDoctorMainUI(
    botComponent: BotComponent.AppmtDoctor?,
    viewModel: GeminiViewModel,
    isActive: Boolean
) {
    // Step 1: Create a mutable state for the selected doctor
    var selectedDoctor by remember {
        mutableStateOf(botComponent?.doctor)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Step 2: Display a message to the user
        ChatRow(chatText = "Please enter the name of the doctor")

        // Step 3: Create a UI component to get the user's input
        // This could be a TextField or any other input field
        ChatInputTextField(value = selectedDoctor ?: "",
            modifier = Modifier.align(Alignment.End),
            placeholder = "Enter doctor's name here", isActive = isActive) { newValue ->
            // Step 4: Update the selected doctor when the user enters a new value
            selectedDoctor = newValue
            viewModel.data.value = viewModel.data.value.copy(appDoctor = newValue)
        }
    }
}

@Composable
fun DEAppointmentSpecialityMainUI(
    botComponent: BotComponent.AppmtSpeciality?,
    viewModel: GeminiViewModel,
    isActive: Boolean
) {
    // Step 1: Create a mutable state for the selected specialty
    var selectedSpeciality by remember {
        mutableStateOf(botComponent?.speciality)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Step 2: Display a message to the user
        ChatRow(chatText = "Please enter the specialty of the doctor")

        // Step 3: Create a UI component to get the user's input
        // This could be a TextField or any other input field
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            if(isActive)
            DoctorSearch { newValue ->
                selectedSpeciality = newValue
                viewModel.data.value = viewModel.data.value.copy(appSpeciality = newValue)
            }
        }


    }
}


@Composable
fun DoctorSearch(onSelect: (DoctorProfession) -> Unit) {
    // ...
    var selectedSpeciality by remember { mutableStateOf<DoctorProfession?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(start = 16.dp,end = 16.dp, top = 16.dp, bottom = 16.dp)
    ){

    }

    OutlinedTextField(
        value = selectedSpeciality?.professionName ?: "Doctor’s specialty",
        onValueChange = {},
        label = { Text("Choose:") },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown",
                modifier = Modifier.clickable { showDialog = true }
            )
        },
        readOnly = true,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showDialog = true }
    )
    Spacer(modifier = Modifier.height(8.dp))

    if (showDialog) {
        BasicAlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Choose Doctor's Specialty") },
            text = {
                val scrollState = rememberScrollState()
                Box(modifier = Modifier.height(400.dp)) {
                    Column(
                        modifier = Modifier.verticalScroll(scrollState)
                    ) {
                        DoctorProfession.values().forEach { profession ->
                            DropdownMenuItem(
                                text = {
                                    Text(profession.professionName)
                                },
                                onClick = {
                                    selectedSpeciality = profession
                                    onSelect(profession)
                                    showDialog = false
                                })
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {}
        )
    }
}

