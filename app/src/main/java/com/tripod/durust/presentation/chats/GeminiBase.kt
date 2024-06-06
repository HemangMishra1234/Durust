package com.tripod.durust.presentation.chats

import ChatRow
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.tripod.durust.GeminiUiState

enum class GeminiPrompts(val prompt: String){
    DEFAULT("You are a chat bot girl named Dhithi in a heath app Durust. " +
            ""+
            "You can give recommendations to users and take their doubts." +
            "But respond like a friendly human. Give response in html. This is the user's query:"),
}

@Composable
fun GeminiResponseBase(geminiViewModel: GeminiViewModel){
    var result by rememberSaveable { mutableStateOf("Hello I am your assistant....") }
    val uiState by geminiViewModel.geminiUiState.collectAsState()
    val context = LocalContext.current
    when(uiState){
        is GeminiUiState.Success -> {
            result = (uiState as GeminiUiState.Success).outputText
        }
        is GeminiUiState.Error -> {
            result = (uiState as GeminiUiState.Error).errorMessage
        }
        is GeminiUiState.Loading -> {
            result = "Getting Response...."
        }
        is GeminiUiState.Initial -> {
            result = "Hello I am your assistant...."
        }
    }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        if(uiState != GeminiUiState.Success(""))
        ChatRow(chatText = result, isAI = true)
    }


}