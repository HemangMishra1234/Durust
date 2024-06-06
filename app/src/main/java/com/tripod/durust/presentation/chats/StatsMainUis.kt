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
import com.tripod.durust.presentation.chats.individual.DEStatsListBase
import com.tripod.durust.presentation.chats.individual.GlucoseTestTypeList
import com.tripod.durust.presentation.chats.individual.StatsEntity
import com.tripod.durust.presentation.datacollection.WeightPickerCard

@Composable
fun DEStatsCarouselMainUI(
    botComponent: BotComponent.DEStatsCarouselState?,
    viewModel: GeminiViewModel,
    isActive: Boolean
) {
    // Step 1: Create a mutable state for the selected option
    var selectedOption by remember {
        mutableStateOf(botComponent?.selectedOption)
    }

    // Step 2: Display a message to the user
    ChatRow(chatText = "What type of stats would you like to enter?")

    // Step 3: Pass the selected option to the Specific Ui component
    DEStatsListBase(
        selectedStatIn = selectedOption,
        isClickable = isActive,
    ) {
        // Step 4: Update the selected option when the user selects a new option
        selectedOption = it
        viewModel.data.value = viewModel.data.value.copy(selectedStatsEntity = it)
    }
}

@Composable
fun DEStatsWeightEntryMainUI(
    botComponent: BotComponent.DEStatsWeightEntryState?,
    viewModel: GeminiViewModel,
    isActive: Boolean
) {
    // Step 1: Create a mutable state for the selected weight
    var selectedWeight by remember {
        mutableStateOf(botComponent?.weight)
    }

    // Step 2: Display a message to the user
    ChatRow(chatText = "Please enter your weight")

    // Step 3: Create a UI component to get the user's input
    // This could be a custom composable that displays a weight picker
    // For this example, let's assume you have a composable named `WeightPickerCard`
    WeightPickerCard(
        initialWeight = selectedWeight?.toInt() ?: 0,
        isEnabled = isActive
    ) {
        // Step 4: Update the selected weight when the user selects a new weight
        selectedWeight = it.toFloat()
        viewModel.data.value = viewModel.data.value.copy(weight = it.toFloat())
    }
}

@Composable
fun DEBloodPressureSystolicMainUI(
    botComponent: BotComponent.DEStatsBloodPressureSystolicEntryState?,
    viewModel: GeminiViewModel,
    isActive: Boolean
) {
    // Step 1: Create a mutable state for the systolic pressure
    var systolicPressure by remember {
        mutableStateOf(botComponent?.systolic)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Step 2: Display a message to the user
        ChatRow(chatText = "Please enter your systolic blood pressure")

        // Step 3: Create a UI component to get the user's input
        // This could be a TextField or any other input field
        ChatInputTextField(value = systolicPressure?.toString()?:"",
            modifier = Modifier.align(Alignment.End),
            placeholder = "Enter systolic pressure here", isActive = isActive) {
                newValue ->
            // Step 4: Update the systolic pressure when the user enters a new value
            systolicPressure = newValue.toIntOrNull()
            viewModel.data.value = viewModel.data.value.copy(systolicPressure = systolicPressure)
        }
    }
}

@Composable
fun DEBloodPressureDiastolicMainUI(
    botComponent: BotComponent.DEStatsBloodPressureDiastolicEntryState?,
    viewModel: GeminiViewModel,
    isActive: Boolean
) {
    // Step 1: Create a mutable state for the diastolic pressure
    var diastolicPressure by remember {
        mutableStateOf(botComponent?.diastolic)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Step 2: Display a message to the user
        ChatRow(chatText = "Please enter your diastolic blood pressure")

        // Step 3: Create a UI component to get the user's input
        // This could be a TextField or any other input field
        ChatInputTextField(value = diastolicPressure?.toString()?:"",
            modifier = Modifier.align(Alignment.End),
            placeholder = "Enter diastolic pressure here", isActive = isActive) {
                newValue ->
            // Step 4: Update the diastolic pressure when the user enters a new value
            diastolicPressure = newValue.toIntOrNull()
            viewModel.data.value = viewModel.data.value.copy(diastolicPressure = diastolicPressure)
        }
    }
}

@Composable
fun DEGlucoseLevelMainUI(
    botComponent: BotComponent.DEStatsGlucoseEntryState?,
    viewModel: GeminiViewModel,
    isActive: Boolean
) {
    // Step 1: Create a mutable state for the glucose level
    var glucoseLevel by remember {
        mutableStateOf(botComponent?.glucose)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Step 2: Display a message to the user
        ChatRow(chatText = "Please enter your glucose level")

        // Step 3: Create a UI component to get the user's input
        // This could be a TextField or any other input field
        ChatInputTextField(value = glucoseLevel?.toString()?:"",
            modifier = Modifier.align(Alignment.End),
            placeholder = "Enter glucose level here", isActive = isActive) {
                newValue ->
            // Step 4: Update the glucose level when the user enters a new value
            glucoseLevel = newValue.toIntOrNull()
            viewModel.data.value = viewModel.data.value.copy(glucoseLevel = glucoseLevel)
        }
    }
}

@Composable
fun DEGlucoseTestTypeMainUI(
    botComponent: BotComponent.DEStatsGlucoseTestTypeState?,
    viewModel: GeminiViewModel,
    isActive: Boolean
) {
    // Step 1: Create a mutable state for the selected test type
    var selectedTestType by remember {
        mutableStateOf(botComponent?.glucoseTestType)
    }

    // Step 2: Display a message to the user
    ChatRow(chatText = "What type of glucose test did you take?")

    // Step 3: Pass the selected test type to the GlucoseTestTypeList composable
    GlucoseTestTypeList(
        isEnabled = isActive,
        selectedTestType = selectedTestType
    ) {
        // Step 4: Update the selected test type when the user selects a new type
        selectedTestType = it
        viewModel.data.value = viewModel.data.value.copy(glucoseTestType = it)
    }
}