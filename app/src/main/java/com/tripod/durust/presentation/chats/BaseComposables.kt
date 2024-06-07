package com.tripod.durust.presentation.chats

import ChatInputTextField
import ChatRow
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tripod.durust.presentation.chats.data.BotComponent
import com.tripod.durust.presentation.chats.individual.DataEntryCarouselBase
import com.tripod.durust.presentation.chats.individual.DataEntryCarouselEntity
import com.tripod.durust.presentation.chats.individual.MenuBase
import com.tripod.durust.presentation.chats.individual.MenuOptions
import com.tripod.durust.presentation.datacollection.TimeEntity
import com.tripod.durust.presentation.datacollection.WakeSleepEntity
import com.tripod.durust.presentation.datacollection.WakeUpTimeAndBedTime

@Preview(showBackground = true)
@Composable
fun PreviewMenuMainUI() {
    MenuBase(
        isEnabled = false,
        initialSelectedOption = MenuOptions.MOOD_TODAY, // Replace with one of your actual options
        onOptionSelected = { /*TODO: Handle option selection*/ }
    )
}

@Composable
fun MenuMainUI(
    botComponent: BotComponent.MenuState?,
    viewModel: GeminiViewModel,
    isActive: Boolean
) {
    var isSelected by remember {
        mutableStateOf(false)
    }
    var selectedOption by remember {
        mutableStateOf(botComponent?.selectedOption)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        ChatRow(
            chatText = if (viewModel.history.value
                    .isEmpty() && isActive
            ) "Hello! welcome back!\nHow may I help you " else "I can help you with"
        )
        MenuBase(
            isEnabled = isActive,
            initialSelectedOption = selectedOption,
            onOptionSelected = {
                selectedOption = it
                isSelected = true
                viewModel.onMenuOptionSelected(it)
            }
        )
    }
}

@Composable
fun DataEntryMainUI(
    botComponent: BotComponent.DECarouselState?,
    viewModel: GeminiViewModel,
    isActive: Boolean
) {
    //Step 1: Create a mutable state for the selected option
    var selectedOption by remember {
        mutableStateOf(botComponent?.selectedOption)
    }
    ChatRow(chatText = "What type of data would you like to enter?")
    //Step 2: Pass the selected option to the Specific Ui component
    DataEntryCarouselBase(
        inactiveValue = selectedOption ?: DataEntryCarouselEntity.EXERCISE,
        isActive = isActive,
        modifier = Modifier.width(280.dp)
            .height(250.dp)
    ) {
        //Step 3: Update the selected option when the user selects a new option
        selectedOption = it
        viewModel.data.value = viewModel.data.value.copy(selectedCarouselEntity = it)
    }

}

@Composable
fun DESleepMainUI(
    botComponent: BotComponent.DESleepDurationState?,
    viewModel: GeminiViewModel,
    isActive: Boolean
) {
    //Step 1: Create a mutable state for the selected option
    var selectedOption by remember {
        mutableStateOf(botComponent?.duration)
    }
    ChatRow(chatText = "Please enter your sleep duration")
    //Step 2: Pass the selected option to the Specific Ui component
    WakeUpTimeAndBedTime(
        initialSchedule = selectedOption ?: WakeSleepEntity(TimeEntity(6, 15), TimeEntity(11, 15)),
        isEnabled = isActive
    ) {
        //Step 3: Update the selected option when the user selects a new option
        selectedOption = it
        viewModel.data.value = viewModel.data.value.copy(sleepDuration = it)
    }
}

@Composable
fun DEMealTrackMainUI(
    botComponent: BotComponent.DEMealTrackState?,
    viewModel: GeminiViewModel,
    isActive: Boolean
) {
    // Step 1: Create a mutable state for the selected meal
    var selectedMeal2 by remember {
        mutableStateOf(botComponent?.meal)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Step 2: Display a message to the user
        ChatRow(chatText = "Please enter meal you had. Also mention the quantity.")

        // Step 3: Create a UI component to get the user's input
        // This could be a TextField or any other input field
        ChatInputTextField(value = selectedMeal2 ?: "",
            modifier = Modifier.align(Alignment.End),
            placeholder = "Enter meal here", isActive = isActive) { newValue ->
            // Step 4: Update the selected meal when the user enters a new value
            selectedMeal2 = newValue
            viewModel.data.value = viewModel.data.value.copy(meal = newValue)
        }
    }
}

@Composable
fun DEWaterTrackMainUI(
    botComponent: BotComponent.DEWaterTrackState?,
    viewModel: GeminiViewModel,
    isActive: Boolean
) {
    // Step 1: Create a mutable state for the selected water intake
    var selectedWater by remember {
        mutableStateOf(botComponent?.water)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Step 2: Display a message to the user
        ChatRow(chatText = "Please enter the amount of water you had in litres.")

        // Step 3: Create a UI component to get the user's input
        // This could be a TextField or any other input field
        ChatInputTextField(value = selectedWater?.toString() ?: "",
            modifier = Modifier.align(Alignment.End),
            placeholder = "Enter water intake here", isActive = isActive) { newValue ->
            // Step 4: Update the selected water intake when the user enters a new value
            selectedWater = newValue.toIntOrNull()
            if (selectedWater != null) {
                viewModel.data.value = viewModel.data.value.copy(water = selectedWater)
            }
        }
    }
}

@Composable
fun DEMoodTrackMainUI(
    botComponent: BotComponent.MoodState?,
    viewModel: GeminiViewModel,
    isActive: Boolean
) {
    // Step 1: Create a mutable state for the selected mood
    var selectedMood by remember {
        mutableStateOf(botComponent?.mood)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Step 2: Display a message to the user
        ChatRow(chatText = "Please enter your mood.")

        // Step 3: Create a UI component to get the user's input
        // This could be a TextField or any other input field
        ChatInputTextField(value = selectedMood ?: "",
            modifier = Modifier.align(Alignment.End),
            placeholder = "Enter mood here", isActive = isActive) { newValue ->
            // Step 4: Update the selected mood when the user enters a new value
            selectedMood = newValue
            viewModel.data.value = viewModel.data.value.copy(mood = selectedMood)
        }
    }
}