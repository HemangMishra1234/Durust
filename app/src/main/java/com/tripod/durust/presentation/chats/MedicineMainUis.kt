package com.tripod.durust.presentation.chats

import ChatInputTextField
import ChatRow
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.tripod.durust.presentation.chats.data.BotComponent
import com.tripod.durust.presentation.chats.individual.MedicineFrequencyList
import com.tripod.durust.presentation.chats.individual.MedicineTimeList
import com.tripod.durust.presentation.datacollection.TimeEntity
import com.tripod.durust.presentation.datacollection.TimePickerBase

@Composable
fun DEMedicineSearchMainUI(
    botComponent: BotComponent.DEMedicineSearchState?,
    viewModel: GeminiViewModel,
    isActive: Boolean
) {
    // Step 1: Create a mutable state for the selected medicine
    var selectedMedicine by remember {
        mutableStateOf(botComponent?.medicine)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Step 2: Display a message to the user
        ChatRow(chatText = "Please enter the name of the medicine")

        // Step 3: Create a UI component to get the user's input
        // This could be a TextField or any other input field
        ChatInputTextField(value = selectedMedicine?:"",
            modifier = Modifier.align(Alignment.End),
            placeholder = "Enter medicine here",isActive = isActive) {
            newValue ->
                // Step 4: Update the selected medicine when the user enters a new value
                selectedMedicine = newValue
                viewModel.data.value = viewModel.data.value.copy(medicineName = newValue)

        }
    }
}

@Composable
fun DEMedicineTimeMainUI(
    botComponent: BotComponent.DEMedicineTimeState?,
    viewModel: GeminiViewModel,
    isActive: Boolean
) {
    // Step 1: Create a mutable state for the selected time
    var selectedTime by remember {
        mutableStateOf(botComponent?.time)
    }

    // Step 2: Display a message to the user
    ChatRow(chatText = "When do you take your medicine?")

    // Step 3: Pass the selected time to the MedicineTimeList composable
    MedicineTimeList(
        isEnabled = isActive,
        selectedTime = selectedTime
    ) {
        // Step 4: Update the selected time when the user selects a new time
        selectedTime = it
        viewModel.data.value = viewModel.data.value.copy(medicineTime = it)
    }
}

@Composable
fun DEMedicineFrequencyIsDailyMainUI(
    botComponent: BotComponent.DEMedicineFrequencyIsDaily?,
    viewModel: GeminiViewModel,
    isActive: Boolean
) {
    // Step 1: Create a mutable state for the selected option
    var selectedOption by remember {
        mutableStateOf(botComponent?.isDaily)
    }

    // Step 2: Display a message to the user
    ChatRow(chatText = "Is the medicine frequency daily?")

    // Step 3: Create a UI component to get the user's response
    // This could be a custom composable that displays options for "Yes" and "No"
    OptionBase(
        isEnabled = isActive,
        initialSelectedOption = selectedOption,
        trueLabel = "Yes",
        falseLabel = "No",
        onOptionSelected = {
            // Step 4: Update the selected option when the user selects a new option
            selectedOption = it
            viewModel.data.value = viewModel.data.value.copy(isMedicineDaily = it)
            viewModel.onDEMedicineFrequencyIsDailySelected(it)
        }
    )
}

@Composable
fun DEMedicineWeekdaysMainUI(
    botComponent: BotComponent.DEMedicineWeekdaysState?,
    viewModel: GeminiViewModel,
    isActive: Boolean
) {
    // Step 1: Create a mutable state for the selected option
    var selectedDays by remember {
        mutableStateOf(botComponent?.frequency)
    }

    // Step 2: Display a message to the user
    ChatRow(chatText = "On which days do you take the medicine?")

    // Step 3: Create a UI component to get the user's response
    MedicineFrequencyList(
        isEnabled = isActive,
        selectedDays = selectedDays ?: emptyList(),
        onDaySelected = {
            // Step 4: Update the selected option when the user selects a new option
            selectedDays = it
            viewModel.data.value = viewModel.data.value.copy(medicineFrequency = it)
        }
    )
}

@Composable
fun DEMedicineNotificationMainUI(
    botComponent: BotComponent.DEMedicineNotificationState?,
    viewModel: GeminiViewModel,
    isActive: Boolean
) {
    // Step 1: Create a mutable state for the selected option
    var selectedOption by remember {
        mutableStateOf(botComponent?.shouldNotify)
    }

    // Step 2: Display a message to the user
    ChatRow(chatText = "Would you like to be notified for the medicine?")

    // Step 3: Create a UI component to get the user's response
    // This could be a custom composable that displays options for "Yes" and "No"
    // For this example, let's assume you have a composable named `OptionBase`
    OptionBase(
        isEnabled = isActive,
        initialSelectedOption = selectedOption,
        trueLabel = "Yes",
        falseLabel = "No",
        onOptionSelected = {
            // Step 4: Update the selected option when the user selects a new option
            selectedOption = it
            viewModel.data.value = viewModel.data.value.copy(shouldNotifyForMedicine = it)
            viewModel.onDEMedicineNotificationStateSelected(it)
        }
    )
}

@Composable
fun DEMedicineNotificationTimeMainUI(
    botComponent: BotComponent.DEMedicineNotificationTimeState?,
    viewModel: GeminiViewModel,
    isActive: Boolean
) {
    // Step 1: Create a mutable state for the selected time
    var selectedTime by remember {
        mutableStateOf(botComponent?.time)
    }

    // Step 2: Display a message to the user
    ChatRow(chatText = "Please select a notification time for the medicine")

    // Step 3: Create a UI component to get the user's response
    // This could be a custom composable that displays a time picker
    // For this example, let's assume you have a composable named `TimePickerBase`
    TimePickerBase(
        initialTime = selectedTime ?: TimeEntity(0, 0),
        isEnabled = isActive,
        onTimeChange = {
            // Step 4: Update the selected time when the user selects a new time
            selectedTime = it
            viewModel.data.value = viewModel.data.value.copy(medicineNotifcationTime = it)
        }
    )
}

@Composable
fun DEMedicineAddOneMoreMainUI(
    botComponent: BotComponent.DEMedicineAddOneMoreState?,
    viewModel: GeminiViewModel,
    isActive: Boolean
) {
    // Step 1: Create a mutable state for the selected option
    var selectedOption by remember {
        mutableStateOf(botComponent?.shouldAdd)
    }

    // Step 2: Display a message to the user
    ChatRow(chatText = "Would you like to add another medicine?")

    // Step 3: Create a UI component to get the user's response
    // This could be a custom composable that displays options for "Yes" and "No"
    // For this example, let's assume you have a composable named `YesNoSelectorBase`
    OptionBase(
        isEnabled = isActive,
        initialSelectedOption = selectedOption,
        trueLabel = "+ Add More",
        falseLabel = "Skip",
        onOptionSelected = {
            // Step 4: Update the selected option when the user selects a new option
            selectedOption = it
            viewModel.data.value = viewModel.data.value.copy(shouldAddMedicine = it)
            viewModel.onDEMedicineAddOneMoreStateSelected(it)
        }
    )
}