package com.tripod.durust.presentation.chats

import ChatRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tripod.durust.presentation.chats.data.BotComponent
import com.tripod.durust.presentation.datacollection.ExercisePreferenceUI
import com.tripod.durust.presentation.datacollection.ExerciseType
import com.tripod.durust.presentation.datacollection.TimeEntity
import com.tripod.durust.presentation.datacollection.TimePickerBase

@Composable
fun DEExerciseTypeMainUI(
    botComponent: BotComponent.DEExerciseTypeCarouselState?,
    viewModel: GeminiViewModel,
    isActive: Boolean
) {
    //Step 1: Create a mutable state for the selected option
    var selectedOption by remember {
        mutableStateOf(botComponent?.selectedOption)
    }
    ChatRow(chatText = "What type of exercise would you like to do?")
    //Step 2: Pass the selected option to the Specific Ui component
    ExercisePreferenceUI(
        inactiveValue = selectedOption ?: ExerciseType.GYM,
        isActive = isActive,
        modifier = Modifier
            .width(280.dp)
            .height(250.dp)
    ) {
        //Step 3: Update the selected option when the user selects a new option
        selectedOption = it
        viewModel.data.value = viewModel.data.value.copy(selectedExerciseType = it)
    }
}

@Composable
fun DEExerciseTimeDurationMainUI(
    botComponent: BotComponent.DEExerciseDurationState?,
    viewModel: GeminiViewModel,
    isActive: Boolean
) {
    //Step 1: Create a mutable state for the selected duration
    var selectedDuration by remember {
        mutableStateOf(botComponent?.duration)
    }

    //Step 2: Pass the selected duration to the TimeDurationCard composable
    ChatRow(chatText = "How long did you exercise for?")
    Spacer(modifier = Modifier.height(20.dp))
    TimePickerBase(
        initialTime = selectedDuration ?: TimeEntity(0,0),
        isEnabled = isActive,
        is24hour = true,
        onTimeChange = {
            //Step 3: Update the selected duration when the user selects a new duration
            selectedDuration = it
            viewModel.data.value = viewModel.data.value.copy(exerciseDuration = it)
        }
    )
}

@Composable
fun DEExerciseAddOneMoreMainUI(
    botComponent: BotComponent.DEExerciseAddOneMoreState?,
    viewModel: GeminiViewModel,
    isActive: Boolean
) {
    // Step 1: Create a mutable state for the selected option
    var selectedOption by remember {
        mutableStateOf(botComponent?.shouldAdd)
    }

    // Step 2: Display a message to the user
    ChatRow(chatText = "Would you like to add another exercise?")

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
            viewModel.data.value = viewModel.data.value.copy(shouldAddExercise = it)
            viewModel.onDEExerciseAddOneMoreStateSelected(it)
        }
    )
}