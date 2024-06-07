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
import com.tripod.durust.presentation.MainActivity

enum class GeminiPrompts(val prompt: String){
    DEFAULT("You are a chat bot girl named Dhithi in a heath app Durust. " +
            "Don't ask counter questions"+
            "You can give recommendations to users and take their doubts." +
            "But respond like a friendly human. This is the user's query:"),
    MEAL("You are a chat bot girl named Dhithi in a health app Durust."+
            "Don't ask counter questions"+
        "User is giving you his todays meal. You have to tell the estimated amount of calories in the meal." +
            "Here is users response:"),
    MOOD("You are a chat bot girl named Dhithi in a health app Durust."+
            "Don't ask counter questions"+
            "User is telling you his mood. You have to respond to his mood." +
            "If he is in a happy mood congratulate him else tell him to cope up. Give tips" +
            "Here is users response:"),
    RECOMMENDATION("You are a chat bot girl named Dhithi in a health app Durust."+
            "Don't ask counter questions"+
        "User is asking you for a recommendation. You have to give him a health recommendation." +
            "Here is users primary data ${MainActivity.primaryUserData.value} use this for responding"
    )
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
            result = "An error occurred. Check your prompt and try again."
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