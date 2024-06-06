package com.tripod.durust.presentation.chats

import ChatRow
import NextImageButton
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tripod.durust.presentation.chats.data.BotComponent
import com.tripod.durust.presentation.chats.data.BotUiState


@Composable
fun BotScreen(
    geminiViewModel: GeminiViewModel
) {
    var prompt by rememberSaveable { mutableStateOf("") }
    var columnState = rememberLazyListState()

    LaunchedEffect(geminiViewModel.needScroll.value) {
        if (geminiViewModel.needScroll.value) {
            columnState.animateScrollToItem(geminiViewModel.history.value.size)
            geminiViewModel.needScroll.value = false
        }
    }
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        modifier = Modifier
            .background(color = Color(0xFF7788F4))
            .navigationBarsPadding()
            .statusBarsPadding()
    ) { paddingValues ->
        val pd = paddingValues
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFF7788F4))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(top = 16.dp, bottom = 90.dp)
            ) {
                LazyColumn(
                    state = columnState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Log.i("Gemini", "History size: ${geminiViewModel.history.value.size}")
                    items(geminiViewModel.history.value.size) { index ->
                        val item = geminiViewModel.history.value[index]
                        when (item) {
                            is BotComponent.UserResponseState -> {
                                Response(response = item.response)
                            }

                            is BotComponent.GeminiState -> {
                                ChatRow(chatText = item.prompt)
                            }

                            is BotComponent.GeminiResponseState ->{
                                ChatRow(chatText = item.response)
                            }

                            is BotComponent.MenuState -> {
                                MenuMainUI(botComponent = item, viewModel = geminiViewModel, isActive = false)
                            }
                            else -> {}
                        }
                    }
                    item {
                        when (geminiViewModel.chatUiState.value) {
                            BotUiState.GEMINI -> {
                                GeminiResponseBase(geminiViewModel = geminiViewModel)
                            }

                            BotUiState.MENU ->{
                                MenuMainUI(botComponent = null, viewModel = geminiViewModel, isActive = true)
                            }
                            else -> {}
                        }
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(8.dp)
                    .height(60.dp)
            ) {
                BotTextField(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    value = prompt,
                    onValueChange = {
                        prompt = it
                    }) {
                    geminiViewModel.onClick(snackbarHostState,prompt)
                    prompt = ""
                }
                Spacer(modifier = Modifier.width(10.dp))
                NextImageButton(
                    modifier = Modifier,
                    smallSize = 30, largeSize = 60
                ) {
                    geminiViewModel.onClick(snackbarHostState,prompt)
                    prompt = ""
                }
            }
        }
    }

//    Column(
//        modifier = Modifier.fillMaxSize()
//    ) {
//        Text(
//            text = stringResource(R.string.baking_title),
//            style = MaterialTheme.typography.titleLarge,
//            modifier = Modifier.padding(16.dp)
//        )
//
//        Row(
//            modifier = Modifier.padding(all = 16.dp)
//        ) {
//            TextField(
//                value = prompt,
//                label = { Text(stringResource(R.string.label_prompt)) },
//                onValueChange = { prompt = it },
//                modifier = Modifier
//                    .weight(0.8f)
//                    .padding(end = 16.dp)
//                    .align(Alignment.CenterVertically)
//            )
//
//            Button(
//                onClick = {
//                    geminiViewModel.sendPrompt(prompt)
//                },
//                enabled = prompt.isNotEmpty(),
//                modifier = Modifier
//                    .align(Alignment.CenterVertically)
//            ) {
//                Text(text = stringResource(R.string.action_go))
//            }
//        }
//
////        if (uiState is GeminiUiState.Loading) {
////            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
////        } else {
////            var textColor = MaterialTheme.colorScheme.onSurface
////            if (uiState is GeminiUiState.Error) {
////                textColor = MaterialTheme.colorScheme.error
////                result = (uiState as GeminiUiState.Error).errorMessage
////            } else if (uiState is GeminiUiState.Success) {
////                textColor = MaterialTheme.colorScheme.onSurface
////                result = (uiState as GeminiUiState.Success).outputText
////            }
////            val scrollState = rememberScrollState()
////            Text(
////                text = result,
////                textAlign = TextAlign.Start,
////                color = textColor,
////                modifier = Modifier
////                    .align(Alignment.CenterHorizontally)
////                    .padding(16.dp)
////                    .fillMaxSize()
////                    .verticalScroll(scrollState)
////            )
////        }
//    }
}