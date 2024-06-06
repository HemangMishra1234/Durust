package com.tripod.durust.presentation.chats

import ChatRow
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tripod.durust.presentation.chats.data.BotComponent
import com.tripod.durust.presentation.chats.individual.MenuBase
import com.tripod.durust.presentation.chats.individual.MenuOptions

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
fun MenuMainUI(botComponent: BotComponent.MenuState?,viewModel: GeminiViewModel, isActive: Boolean){
    var isSelected by remember {
        mutableStateOf(false)
    }
    var selectedOption by remember {
        mutableStateOf(botComponent?.selectedOption)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        ChatRow(chatText ="Hello! welcome back!\nHow may I help you " )
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
