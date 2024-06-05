package com.tripod.durust.presentation.datacollection

import ChatRow
import NameInputTextField
import NextImageButton
import TopChatBar
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tripod.durust.data.DateEntity


@Preview
@Composable
fun ChatScreen() {
    val viewModel = ChatComponentViewModelFactory().create(ChatComponentViewModel::class.java)
    val chatComponents by viewModel.history
    var onNext = {}
    val context = LocalContext.current
    var isDone by remember {
        mutableStateOf(false)
    }
    val activeComponent by viewModel.activeComponent
    var columnState = rememberLazyListState()
    val needScroll = remember {
        mutableStateOf(false)
    }
    LaunchedEffect(needScroll.value) {
        if(needScroll.value){
            columnState.animateScrollToItem(viewModel.history.value.size)
            needScroll.value = false
        }
    }
    Scaffold(
        modifier = Modifier
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
                    .fillMaxHeight(0.86f)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                TopChatBar()
                LazyColumn(
                    state = columnState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                ) {
                    Log.i("ChatScreen", "ChatComponents: $chatComponents")
                    items(chatComponents.size) { index ->
                        AnimatedVisibility(visible = true) {
                            when (val chatComponent = chatComponents[index]) {
                                is ChatComponent.NameInputText -> NameInputText(
                                    chatComponent,
                                    isActive = false,
                                    viewModel = viewModel
                                ) { _, _ -> }

                                is ChatComponent.GenderInputUI -> GenderInputMain(
                                    chatComponent,
                                    isActive = false,
                                    viewModel = viewModel
                                ) { _, _ -> }

                                is ChatComponent.WeightInput -> WeightInputMain(
                                    chatComponent = chatComponent,
                                    viewModel = viewModel,
                                    isActive = false
                                ) { _, _ -> }

                                is ChatComponent.BirthdayInput -> DateInputMain(
                                    chatComponent = chatComponent,
                                    viewModel = viewModel,
                                    isActive = false
                                ) {
                                    _,_ ->
                                }

                                is ChatComponent.HeightInput -> HeightInputMain(
                                    chatComponent = chatComponent,
                                    viewModel = viewModel,
                                    isActive = false
                                ) {
                                     _,_->
                                }

                                is ChatComponent.ExerciseFrequencyInput -> ExerciseFrequencyInputMain(
                                    chatComponent = chatComponent,
                                    viewModel = viewModel,
                                    isActive = false
                                ) {
                                    _,_->
                                }

                                is ChatComponent.RoutineCheckUpFrequency -> CheckUpFrequencyInputMain(
                                    chatComponent = chatComponent,
                                    viewModel = viewModel,
                                    isActive = false
                                ) {
                                    _,_->
                                }

                                else -> {}
                            }
                        }
                        Spacer(modifier = Modifier.height(18.dp))
                    }
                    item {
                        AnimatedVisibility(visible = true) {
                            when (activeComponent) {
                                ChatComponentType.Weight ->
                                    WeightInputMain(
                                        chatComponent = ChatComponent.WeightInput(
                                            id = "5",
                                            weight = 95
                                        ),
                                        viewModel = viewModel,
                                        isActive = true
                                    ) { isDone2, it ->
                                        onNext = it
                                        isDone = isDone2
                                    }

                                ChatComponentType.Birthday ->
                                    DateInputMain(
                                        chatComponent = ChatComponent.BirthdayInput(
                                            id = "5",
                                            date = DateEntity(5,5,2000)
                                        ), viewModel = viewModel, isActive = true
                                    ) { isDone2, it ->
                                        onNext = it
                                        isDone = isDone2
                                    }

                                ChatComponentType.Height ->
                                    HeightInputMain(
                                        chatComponent = ChatComponent.HeightInput(
                                            id = "5",
                                            height = "5'2"
                                        ), viewModel = viewModel, isActive = true
                                    ) { isDone2, it ->
                                        onNext = it
                                        isDone = isDone2
                                    }

                                ChatComponentType.ExerciseFrequency->
                                    ExerciseFrequencyInputMain(
                                        chatComponent = ChatComponent.ExerciseFrequencyInput(
                                            id = "5",
                                            frequency = 5
                                        ), viewModel = viewModel, isActive = true
                                    ) { isDone2, it ->
                                        onNext = it
                                        isDone = isDone2
                                    }

                                ChatComponentType.RoutineCheckUpFrequency->
                                    CheckUpFrequencyInputMain(
                                        chatComponent = ChatComponent.RoutineCheckUpFrequency(
                                            id = "5",
                                            frequency = CheckUpFrequency.NEVER
                                        ), viewModel = viewModel, isActive = true
                                    ) { isDone2, it ->
                                        onNext = it
                                        isDone = isDone2
                                    }
                                else -> {}
                            }
                        }
                    }
                }
            }
            NextImageButton(
                isEnabledT = true, modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 36.dp)
            ) {
                if (isDone) {
                    onNext()
//                    needScroll.value = true
                    viewModel.nextComponent()
                    isDone = false
                } else
                    Toast.makeText(context, "Select one option please", Toast.LENGTH_SHORT).show()
            }

        }
    }
}

@Composable
fun CheckUpFrequencyInputMain(
    chatComponent: ChatComponent.RoutineCheckUpFrequency, viewModel: ChatComponentViewModel,
    isActive: Boolean, onNext: (Boolean, () -> Unit) -> Unit
) {
    val context = LocalContext.current
    var frequency by remember {
        mutableStateOf(chatComponent.frequency)
    }
    var isAnswered by remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        ChatRow(chatText = chatComponent.message)
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.align(Alignment.End)) {
            FrequencyList(
                selectedFrequency = if (!isActive) chatComponent.frequency else null,
                onFrequencySelected = { selectedFrequency ->
                    if (isActive) {
                        frequency = selectedFrequency ?: CheckUpFrequency.NEVER
                        isAnswered = true
                    }
                },
                isEnabled = isActive
            )
        }
    }
    onNext(isActive && isAnswered) {
        if (isActive) {
            viewModel.data = viewModel.data.copy(checkUpFrequency = frequency)
            Toast.makeText(context, "Exercise frequency is $frequency", Toast.LENGTH_SHORT).show()
            viewModel.addHistory(
                ChatComponent.RoutineCheckUpFrequency(
                    id = getTime().toString(),
                    frequency = frequency
                )
            )
        }
    }
}

@Composable
fun ExerciseFrequencyInputMain(
    chatComponent: ChatComponent.ExerciseFrequencyInput, viewModel: ChatComponentViewModel,
    isActive: Boolean, onNext: (Boolean, () -> Unit) -> Unit
) {
    val context = LocalContext.current
    var sliderPosition by remember {
        mutableStateOf(chatComponent.frequency)
    }
    var isAnswered by remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        ChatRow(chatText = chatComponent.message)
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.align(Alignment.End)) {
            DaysSlider(
                initalSliderPosition = if (!isActive) chatComponent.frequency else 0,
                isEnabled = isActive,
                onDaysChange = { newSliderPosition ->
                    if (isActive) {
                        sliderPosition = newSliderPosition
                        isAnswered = true
                    }
                }
            )
        }
    }
    onNext(isActive && isAnswered) {
        if (isActive) {
            viewModel.data = viewModel.data.copy(exerciseFrequency = sliderPosition)
            Toast.makeText(context, "Exercise frequency slider position is $sliderPosition", Toast.LENGTH_SHORT).show()
            viewModel.addHistory(
                ChatComponent.ExerciseFrequencyInput(
                    id = getTime().toString(),
                    frequency = sliderPosition
                )
            )
        }
    }
}

@Composable
fun HeightInputMain(
    chatComponent: ChatComponent.HeightInput, viewModel: ChatComponentViewModel,
    isActive: Boolean, onNext: (Boolean, () -> Unit) -> Unit
) {
    val context = LocalContext.current
    var height by remember {
        mutableStateOf(chatComponent.height)
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        ChatRow(chatText = chatComponent.message)
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.align(Alignment.End)) {
            HeightPickerCard(
                initialHeight = if (!isActive) chatComponent.height else "5'0",
                isEnabled = isActive
            ) { selectedHeight ->
                if (isActive) {
                    height = selectedHeight
                    onNext(isActive) {
                        if (isActive) {
                            viewModel.data = viewModel.data.copy(height = height)
                            Toast.makeText(context, "Height is $height", Toast.LENGTH_SHORT).show()
                            viewModel.addHistory(
                                ChatComponent.HeightInput(
                                    id = getTime().toString(),
                                    height = height
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DateInputMain(
    chatComponent: ChatComponent.BirthdayInput, viewModel: ChatComponentViewModel,
    isActive: Boolean, onNext: (Boolean, () -> Unit) -> Unit
) {
    val context = LocalContext.current
    var date by remember {
        mutableStateOf(chatComponent.date)
    }
    var isAnswered by remember {
        mutableStateOf(true)
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        ChatRow(chatText = chatComponent.message)
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.align(Alignment.End)) {
            DatePickerCard(
                initialDate = if (!isActive) chatComponent.date else DateEntity(
                    day = 1,
                    month = 1,
                    year = 2000
                ),
                isEnabled = isActive
            ) {
                if (isActive) {
                    date = it
                    isAnswered = true
                }
            }
        }
    }
    onNext(isActive && isAnswered) {
        if (isActive) {
            viewModel.data = viewModel.data.copy(birthday = date)
            Toast.makeText(context, "Date is $date", Toast.LENGTH_SHORT).show()
            viewModel.addHistory(
                ChatComponent.BirthdayInput(
                    id = getTime().toString(),
                    date = date
                )
            )
        }
    }
}

@Composable
fun WeightInputMain(
    chatComponent: ChatComponent.WeightInput, viewModel: ChatComponentViewModel,
    isActive: Boolean, onNext: (Boolean, () -> Unit) -> Unit
) {
    Log.i("WeightInputMain", "$chatComponent")
    val context = LocalContext.current
    var weight by remember {
        mutableStateOf(chatComponent.weight)
    }
    var isAnswered by remember {
        mutableStateOf(true)
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        ChatRow(chatText = chatComponent.message)
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.align(Alignment.End)) {
            WeightPickerCard(
                initialWeight = if (!isActive) chatComponent.weight else 65,
                isEnabled = isActive
            ) {
                if (isActive) {
                    weight = it
                    isAnswered = true
                }
            }
        }
    }
    onNext(isActive && isAnswered) {
        if (isActive) {
            viewModel.data = viewModel.data.copy(weight = weight)
            Toast.makeText(context, "Weight is $weight", Toast.LENGTH_SHORT).show()
            viewModel.addHistory(
                ChatComponent.WeightInput(
                    id = getTime().toString(),
                    weight = weight
                )
            )
        }
    }
}

@Composable
fun GenderInputMain(
    chatComponent: ChatComponent.GenderInputUI, viewModel: ChatComponentViewModel,
    isActive: Boolean, onNext: (Boolean, () -> Unit) -> Unit
) {
    Log.i(("GenderInputMain"), "$chatComponent")
    val context = LocalContext.current
    var genderEntity by remember {
        mutableStateOf(chatComponent.gender)
    }
    var isAnswered by remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        ChatRow(chatText = chatComponent.message)
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.align(Alignment.End)) {
            GenderSelection(
                selectedGenderIn = if (isActive) null else chatComponent.gender,
                isClickable = isActive
            ) {
                if (isActive) {
                    if (it != null) {
                        genderEntity = it
                        isAnswered = true
                    }
                }
            }
        }
        onNext(isActive && isAnswered) {
            if (isActive) {
                viewModel.data = viewModel.data.copy(gender = genderEntity)
                Toast.makeText(context, "Gender is $genderEntity", Toast.LENGTH_SHORT).show()
                viewModel.addHistory(
                    ChatComponent.GenderInputUI(
                        id = getTime().toString(),
                        gender = genderEntity
                    )
                )
            }
        }
    }


}

@Composable
fun NameInputText(
    chatComponent: ChatComponent.NameInputText, viewModel: ChatComponentViewModel,
    isActive: Boolean, onNext: (Boolean, () -> Unit) -> Unit
) {
    val context = LocalContext.current
    var name by remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        ChatRow(chatText = chatComponent.message)
        Box(modifier = Modifier.align(Alignment.End)) {
            NameInputTextField(
                name = if (!isActive) chatComponent.response else "",
                isActive = isActive
            ) {
                name = it
            }
        }
    }
    onNext(isActive && name.isNotEmpty()) {
        if (isActive) {
            viewModel.data = viewModel.data.copy(name = name)
            Toast.makeText(context, "Name is $name", Toast.LENGTH_SHORT).show()
            viewModel.addHistory(
                ChatComponent.NameInputText(
                    id = getTime().toString(),
                    response = name
                )
            )
        }
    }
}

fun getTime() = System.currentTimeMillis()