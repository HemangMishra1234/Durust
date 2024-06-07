package com.tripod.durust.presentation.datacollection

import ChatRow
import ChatInputTextField
import NextImageButton
import TopChatBar
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import com.tripod.durust.data.DateEntity
import com.tripod.durust.getScreenHeightInDP
import kotlinx.serialization.Serializable

@Serializable
object NavChatScreen

@Composable
fun ChatScreen(viewModel: ChatComponentViewModel) {

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
            Box(modifier = Modifier.fillMaxSize()
                .align(Alignment.TopCenter)){
                TopChatBar(viewModel)
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(top = 76.dp, bottom = 90.dp)
            ) {

                LazyColumn(
                    state = columnState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 6.dp)
                ) {
                    Log.i("ChatScreen", "ChatComponentsHistory: $chatComponents")
                    items(chatComponents.size) { index ->
                        AnimatedVisibility(visible = true) {
                            when (val chatComponent = chatComponents[index]) {
                                is OnBoardingChatComponent.NameInputText -> NameInputText(
                                    chatComponent,
                                    isActive = false,
                                    viewModel = viewModel
                                ) { _, _ -> }

                                is OnBoardingChatComponent.GenderInputUI -> GenderInputMain(
                                    chatComponent,
                                    isActive = false,
                                    viewModel = viewModel
                                ) { _, _ -> }

                                is OnBoardingChatComponent.WeightInput -> WeightInputMain(
                                    onBoardingChatComponent = chatComponent,
                                    viewModel = viewModel,
                                    isActive = false
                                ) { _, _ -> }

                                is OnBoardingChatComponent.BirthdayInput -> DateInputMain(
                                    onBoardingChatComponent = chatComponent,
                                    viewModel = viewModel,
                                    isActive = false
                                ) {
                                    _,_ ->
                                }

                                is OnBoardingChatComponent.HeightInput -> HeightInputMain(
                                    onBoardingChatComponent = chatComponent,
                                    viewModel = viewModel,
                                    isActive = false
                                ) {
                                     _,_->
                                }

                                is OnBoardingChatComponent.ExerciseFrequencyInput -> ExerciseFrequencyInputMain(
                                    onBoardingChatComponent = chatComponent,
                                    viewModel = viewModel,
                                    isActive = false
                                ) {
                                    _,_->
                                }

                                is OnBoardingChatComponent.PreferredExerciseInput -> ExercisePreferenceInputMain(
                                    onBoardingChatComponent = chatComponent,
                                    viewModel = viewModel,
                                    isActive = false
                                ) {
                                    _,_->
                                }

                                is OnBoardingChatComponent.StepCountInput -> StepsInputMain(
                                    onBoardingChatComponent = chatComponent,
                                    viewModel = viewModel,
                                    isActive = false
                                ) {
                                    _,_->
                                }

                                is OnBoardingChatComponent.MealPreference -> MealPreferenceInputMain(
                                    onBoardingChatComponent = chatComponent,
                                    viewModel = viewModel,
                                    isActive = false
                                ) {
                                    _,_->
                                }

                                is OnBoardingChatComponent.SleepSchedule -> WakeSleepTimeInputMain(
                                    onBoardingChatComponent = chatComponent,
                                    viewModel = viewModel,
                                    isActive = false
                                ) {
                                    _,_->
                                }

                                is OnBoardingChatComponent.CalorieIntake -> CalorieIntakeInputMain(
                                    onBoardingChatComponent = chatComponent,
                                    viewModel = viewModel,
                                    isActive = false
                                ) {
                                    _,_->
                                }

                                is OnBoardingChatComponent.HealthIssues -> HealthConditionInputMain(
                                    onBoardingChatComponent = chatComponent,
                                    viewModel = viewModel,
                                    isActive = false
                                ) {
                                    _,_->
                                }

                                is OnBoardingChatComponent.RoutineCheckUpFrequency -> CheckUpFrequencyInputMain(
                                    onBoardingChatComponent = chatComponent,
                                    viewModel = viewModel,
                                    isActive = false
                                ) {
                                    _,_->
                                }
                                else ->{}
                            }
                        }
                        Spacer(modifier = Modifier.height(18.dp))
                    }
                    item {
                        AnimatedVisibility(visible = true) {
                            when (activeComponent) {

                                ChatComponentType.Name ->
                                    NameInputText(
                                        onBoardingChatComponent = OnBoardingChatComponent.NameInputText(
                                            id = "5",
                                            response = ""
                                        ),
                                        viewModel = viewModel,
                                        isActive = true
                                    ) { isDone2, it ->
                                        onNext = it
                                        isDone = isDone2
                                    }

                                ChatComponentType.Gender ->
                                    GenderInputMain(
                                        onBoardingChatComponent = OnBoardingChatComponent.GenderInputUI(
                                            id = "5",
                                            gender = GenderEntity.Female
                                        ),
                                        viewModel= viewModel,
                                        isActive = true
                                    ){
                                        isDone2, it ->
                                        onNext = it
                                        isDone = isDone2
                                    }


                                ChatComponentType.Weight ->
                                    WeightInputMain(
                                        onBoardingChatComponent = OnBoardingChatComponent.WeightInput(
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
                                        onBoardingChatComponent = OnBoardingChatComponent.BirthdayInput(
                                            id = "5",
                                            date = DateEntity(5,5,2000)
                                        ), viewModel = viewModel, isActive = true
                                    ) { isDone2, it ->
                                        onNext = it
                                        isDone = isDone2
                                    }

                                ChatComponentType.Height ->
                                    HeightInputMain(
                                        onBoardingChatComponent = OnBoardingChatComponent.HeightInput(
                                            id = "5",
                                            height = "5'2"
                                        ), viewModel = viewModel, isActive = true
                                    ) { isDone2, it ->
                                        onNext = it
                                        isDone = isDone2
                                    }

                                ChatComponentType.ExerciseFrequency->
                                    ExerciseFrequencyInputMain(
                                        onBoardingChatComponent = OnBoardingChatComponent.ExerciseFrequencyInput(
                                            id = "5",
                                            frequency = 5
                                        ), viewModel = viewModel, isActive = true
                                    ) { isDone2, it ->
                                        onNext = it
                                        isDone = isDone2
                                    }

                                ChatComponentType.PreferredExercise->
                                    ExercisePreferenceInputMain(
                                        onBoardingChatComponent = OnBoardingChatComponent.PreferredExerciseInput(
                                            id = "5",
                                            exercise = ExerciseType.GYM
                                        ), viewModel = viewModel, isActive = true
                                    ) { isDone2, it ->
                                        onNext = it
                                        isDone = isDone2
                                    }

                                ChatComponentType.StepCount ->
                                    StepsInputMain(
                                        onBoardingChatComponent = OnBoardingChatComponent.StepCountInput(
                                            id = "5",
                                            steps = StepsInputEntity.GREATER_THAN_TEN_THOUSAND
                                        ), viewModel = viewModel, isActive = true
                                    ) { isDone2, it ->
                                        onNext = it
                                        isDone = isDone2
                                    }

                                ChatComponentType.MealPreference ->
                                    MealPreferenceInputMain(
                                        onBoardingChatComponent = OnBoardingChatComponent.MealPreference(
                                            id = "5",
                                            preference = MealPreferenceEntity.VEGETARIAN
                                        ), viewModel = viewModel, isActive = true
                                    ) { isDone2, it ->
                                        onNext = it
                                        isDone = isDone2
                                    }

                                ChatComponentType.SleepSchedule ->
                                    WakeSleepTimeInputMain(
                                        onBoardingChatComponent = OnBoardingChatComponent.SleepSchedule(
                                            id = "5",
                                            schedule = WakeSleepEntity(TimeEntity(6, 15),
                                                TimeEntity(11, 15))
                                        ), viewModel = viewModel, isActive = true
                                    ) { isDone2, it ->
                                        onNext = it
                                        isDone = isDone2
                                    }

                                ChatComponentType.CalorieIntake->
                                    CalorieIntakeInputMain(
                                        onBoardingChatComponent = OnBoardingChatComponent.CalorieIntake(
                                            id = "5",
                                            calories = 2000
                                        ), viewModel = viewModel, isActive = true
                                    ) { isDone2, it ->
                                        onNext = it
                                        isDone = isDone2
                                    }

                                ChatComponentType.HealthIssues->
                                    HealthConditionInputMain(
                                        onBoardingChatComponent = OnBoardingChatComponent.HealthIssues(
                                            id = "5",
                                            issues = HealthCondition.NONE
                                        ), viewModel = viewModel, isActive = true
                                    ) { isDone2, it ->
                                        onNext = it
                                        isDone = isDone2
                                    }

                                ChatComponentType.RoutineCheckUpFrequency->
                                    CheckUpFrequencyInputMain(
                                        onBoardingChatComponent = OnBoardingChatComponent.RoutineCheckUpFrequency(
                                            id = "5",
                                            frequency = CheckUpFrequency.NEVER
                                        ), viewModel = viewModel, isActive = true
                                    ) { isDone2, it ->
                                        onNext = it
                                        isDone = isDone2
                                    }
                            }
                        }
                    }
                }
            }
            NextImageButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            ) {
                if (isDone) {
                    onNext()
                    needScroll.value = true
//                    viewModel.nextComponent()
                    isDone = false
                } else
                    Toast.makeText(context, "Select one option please", Toast.LENGTH_SHORT).show()
            }

        }
    }
}

@Composable
fun CheckUpFrequencyInputMain(
    onBoardingChatComponent: OnBoardingChatComponent.RoutineCheckUpFrequency, viewModel: ChatComponentViewModel,
    isActive: Boolean, onNext: (Boolean, () -> Unit) -> Unit
) {
    val context = LocalContext.current
    var frequency by remember {
        mutableStateOf(onBoardingChatComponent.frequency)
    }
    var isAnswered by remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        ChatRow(chatText = onBoardingChatComponent.message)
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.align(Alignment.End)) {
            FrequencyList(
                selectedFrequency = if (!isActive) onBoardingChatComponent.frequency else null,
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
            viewModel.addHistory(
                OnBoardingChatComponent.RoutineCheckUpFrequency(
                    id = getTime().toString(),
                    frequency = frequency
                )
            )
        }
    }
}


@Composable
fun HealthConditionInputMain(
    onBoardingChatComponent: OnBoardingChatComponent.HealthIssues,
    viewModel: ChatComponentViewModel,
    isActive: Boolean,
    onNext: (Boolean, () -> Unit) -> Unit
) {
    val context = LocalContext.current
    var healthCondition by remember {
        mutableStateOf(onBoardingChatComponent.issues)
    }
    var isAnswered by remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        ChatRow(chatText = onBoardingChatComponent.message)
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.align(Alignment.End)) {
            HealthConditionList(
                selectedCondition = if (!isActive) onBoardingChatComponent.issues else null,
                onConditionSelected = { selectedCondition ->
                    if (isActive) {
                        healthCondition = selectedCondition ?: HealthCondition.NONE
                        isAnswered = true
                    }
                }
            )
        }
    }
    onNext(isActive && isAnswered) {
        if (isActive) {
            viewModel.data = viewModel.data.copy(healthIssues = healthCondition)
            viewModel.addHistory(
                OnBoardingChatComponent.HealthIssues(
                    id = getTime().toString(),
                    issues = healthCondition
                )
            )
        }
    }
}


@Composable
fun CalorieIntakeInputMain(
    onBoardingChatComponent: OnBoardingChatComponent.CalorieIntake,
    viewModel: ChatComponentViewModel,
    isActive: Boolean,
    onNext: (Boolean, () -> Unit) -> Unit
) {
    val context = LocalContext.current
    var calorieIntake by remember {
        mutableStateOf(onBoardingChatComponent.calories.toString())
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        ChatRow(chatText = onBoardingChatComponent.message)
        Box(modifier = Modifier.align(Alignment.End)) {
            ChatInputTextField(
                value = if (!isActive) onBoardingChatComponent.calories.toString() else "",
                isActive = isActive,
                placeholder = "Enter calories intake here"
            ) {
                calorieIntake = it
            }
        }
    }
    onNext(isActive && calorieIntake.isNotEmpty() && calorieIntake.toIntOrNull() != null) {
        if (isActive) {
            viewModel.data = viewModel.data.copy(calorieIntake = calorieIntake.toInt())
            viewModel.addHistory(
                OnBoardingChatComponent.CalorieIntake(
                    id = getTime().toString(),
                    calories = calorieIntake.toInt()
                )
            )
        }
    }
}


@Composable
fun WakeSleepTimeInputMain(
    onBoardingChatComponent: OnBoardingChatComponent.SleepSchedule,
    viewModel: ChatComponentViewModel,
    isActive: Boolean,
    onNext: (Boolean, () -> Unit) -> Unit
) {
    val context = LocalContext.current
    var wakeSleepEntity by remember {
        mutableStateOf(onBoardingChatComponent.schedule)
    }
    var isAnswered by remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        ChatRow(chatText = onBoardingChatComponent.message)
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.align(Alignment.End)) {
            WakeUpTimeAndBedTime(
                initialSchedule = if (!isActive) onBoardingChatComponent.schedule else WakeSleepEntity(
                    TimeEntity(6, 0),
                    TimeEntity(10, 0)
                ),
                isEnabled = isActive
            ) { selectedWakeSleepEntity ->
                if (isActive) {
                    wakeSleepEntity = selectedWakeSleepEntity
                    isAnswered = true
                }
            }
        }
    }
    onNext(isActive && isAnswered) {
        if (isActive) {
            viewModel.data = viewModel.data.copy(
                wakeUpTime = wakeSleepEntity.wakeTime,
                sleepTime = wakeSleepEntity.sleepTime
            )
            viewModel.addHistory(
                OnBoardingChatComponent.SleepSchedule(
                    id = getTime().toString(),
                    schedule = wakeSleepEntity
                )
            )
        }
    }
}


@Composable
fun MealPreferenceInputMain(
    onBoardingChatComponent: OnBoardingChatComponent.MealPreference,
    viewModel: ChatComponentViewModel,
    isActive: Boolean,
    onNext: (Boolean, () -> Unit) -> Unit
) {
    val context = LocalContext.current
    var mealPreferenceEntity by remember {
        mutableStateOf(onBoardingChatComponent.preference)
    }
    var isAnswered by remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        ChatRow(chatText = onBoardingChatComponent.message)
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.align(Alignment.End)) {
            MealPreferenceSelectionUI(
                initialValue = if (!isActive) onBoardingChatComponent.preference else MealPreferenceEntity.VEGETARIAN,
                isEnabled = isActive
            ) { selectedMealPreference ->
                if (isActive) {
                    mealPreferenceEntity = selectedMealPreference
                    isAnswered = true
                }
            }
        }
    }
    onNext(isActive && isAnswered) {
        if (isActive) {
            viewModel.data = viewModel.data.copy(mealPreferenceEntity = mealPreferenceEntity)
            viewModel.addHistory(
                OnBoardingChatComponent.MealPreference(
                    id = getTime().toString(),
                    preference = mealPreferenceEntity
                )
            )
        }
    }
}


@Composable
fun StepsInputMain(
    onBoardingChatComponent: OnBoardingChatComponent.StepCountInput,
    viewModel: ChatComponentViewModel,
    isActive: Boolean,
    onNext: (Boolean, () -> Unit) -> Unit
) {
    val context = LocalContext.current
    var stepsInputEntity by remember {
        mutableStateOf(onBoardingChatComponent.steps)
    }
    var isAnswered by remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        ChatRow(chatText = onBoardingChatComponent.message)
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.align(Alignment.End)) {
            StepsInputSelectionUI(
                initialValue = if (!isActive) onBoardingChatComponent.steps else StepsInputEntity.LESS_THAN_FIVE_THOUSAND,
                isEnabled = isActive
            ) { selectedSteps ->
                if (isActive) {
                    stepsInputEntity = selectedSteps
                    isAnswered = true
                }
            }
        }
    }
    onNext(isActive && isAnswered) {
        if (isActive) {
            viewModel.data = viewModel.data.copy(stepsInput = stepsInputEntity)
            viewModel.addHistory(
                OnBoardingChatComponent.StepCountInput(
                    id = getTime().toString(),
                    steps = stepsInputEntity
                )
            )
        }
    }
}


@Composable
fun ExercisePreferenceInputMain(
    onBoardingChatComponent: OnBoardingChatComponent.PreferredExerciseInput,
    viewModel: ChatComponentViewModel,
    isActive: Boolean,
    onNext: (Boolean, () -> Unit) -> Unit
) {
    val context = LocalContext.current
    var exerciseType by remember {
        mutableStateOf(onBoardingChatComponent.exercise)
    }
    var isAnswered by remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        ChatRow(chatText = onBoardingChatComponent.message)
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.align(Alignment.End)) {
            ExercisePreferenceUI(
                inactiveValue = if (!isActive) onBoardingChatComponent.exercise else ExerciseType.WALKING,
                isActive = isActive,
                modifier = Modifier
                    .width(280.dp)
                    .height(250.dp)
            ) { selectedExercise ->
                if (isActive) {
                    exerciseType = selectedExercise
                    isAnswered = true
                }
            }
        }
    }
    onNext(isActive && isAnswered) {
        if (isActive) {
            viewModel.data = viewModel.data.copy(exercisePreference = exerciseType)
            viewModel.addHistory(
                OnBoardingChatComponent.PreferredExerciseInput(
                    id = getTime().toString(),
                    exercise = exerciseType
                )
            )
        }
    }
}

@Composable
fun ExerciseFrequencyInputMain(
    onBoardingChatComponent: OnBoardingChatComponent.ExerciseFrequencyInput, viewModel: ChatComponentViewModel,
    isActive: Boolean, onNext: (Boolean, () -> Unit) -> Unit
) {
    val context = LocalContext.current
    var sliderPosition by remember {
        mutableStateOf(onBoardingChatComponent.frequency)
    }
    var isAnswered by remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        ChatRow(chatText = onBoardingChatComponent.message)
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.align(Alignment.End)) {
            DaysSlider(
                initalSliderPosition = if (!isActive) onBoardingChatComponent.frequency else 0,
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
            viewModel.addHistory(
                OnBoardingChatComponent.ExerciseFrequencyInput(
                    id = getTime().toString(),
                    frequency = sliderPosition
                )
            )
        }
    }
}

@Composable
fun HeightInputMain(
    onBoardingChatComponent: OnBoardingChatComponent.HeightInput, viewModel: ChatComponentViewModel,
    isActive: Boolean, onNext: (Boolean, () -> Unit) -> Unit
) {
    val context = LocalContext.current
    var height by remember {
        mutableStateOf(onBoardingChatComponent.height)
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        ChatRow(chatText = onBoardingChatComponent.message)
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.align(Alignment.End)) {
            HeightPickerCard(
                initialHeight = if (!isActive) onBoardingChatComponent.height else "5'0",
                isEnabled = isActive
            ) { selectedHeight ->
                if (isActive) {
                    height = selectedHeight
                    onNext(isActive) {
                        if (isActive) {
                            viewModel.data = viewModel.data.copy(height = height)
                            viewModel.addHistory(
                                OnBoardingChatComponent.HeightInput(
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
    onBoardingChatComponent: OnBoardingChatComponent.BirthdayInput, viewModel: ChatComponentViewModel,
    isActive: Boolean, onNext: (Boolean, () -> Unit) -> Unit
) {
    val context = LocalContext.current
    var date by remember {
        mutableStateOf(onBoardingChatComponent.date)
    }
    var isAnswered by remember {
        mutableStateOf(true)
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        ChatRow(chatText = onBoardingChatComponent.message)
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.align(Alignment.End)) {
            DatePickerCard(
                initialDate = if (!isActive) onBoardingChatComponent.date else DateEntity(
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
            viewModel.addHistory(
                OnBoardingChatComponent.BirthdayInput(
                    id = getTime().toString(),
                    date = date
                )
            )
        }
    }
}

@Composable
fun WeightInputMain(
    onBoardingChatComponent: OnBoardingChatComponent.WeightInput, viewModel: ChatComponentViewModel,
    isActive: Boolean, onNext: (Boolean, () -> Unit) -> Unit
) {
    Log.i("WeightInputMain", "$onBoardingChatComponent")
    val context = LocalContext.current
    var weight by remember {
        mutableStateOf(onBoardingChatComponent.weight)
    }
    var isAnswered by remember {
        mutableStateOf(true)
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        ChatRow(chatText = onBoardingChatComponent.message)
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.align(Alignment.End)) {
            WeightPickerCard(
                initialWeight = if (!isActive) onBoardingChatComponent.weight else 65,
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
            viewModel.addHistory(
                OnBoardingChatComponent.WeightInput(
                    id = getTime().toString(),
                    weight = weight
                )
            )
        }
    }
}

@Composable
fun GenderInputMain(
    onBoardingChatComponent: OnBoardingChatComponent.GenderInputUI, viewModel: ChatComponentViewModel,
    isActive: Boolean, onNext: (Boolean, () -> Unit) -> Unit
) {
    Log.i(("GenderInputMain"), "$onBoardingChatComponent")
    val context = LocalContext.current
    var genderEntity by remember {
        mutableStateOf(onBoardingChatComponent.gender)
    }
    var isAnswered by remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        ChatRow(chatText = onBoardingChatComponent.message)
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.align(Alignment.End)) {
            GenderSelection(
                selectedGenderIn = if (isActive) null else onBoardingChatComponent.gender,
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
                viewModel.addHistory(
                    OnBoardingChatComponent.GenderInputUI(
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
    onBoardingChatComponent: OnBoardingChatComponent.NameInputText, viewModel: ChatComponentViewModel,
    isActive: Boolean, onNext: (Boolean, () -> Unit) -> Unit
) {
    val context = LocalContext.current
    var name by remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        ChatRow(chatText = onBoardingChatComponent.message)
        Box(modifier = Modifier.align(Alignment.End)) {
            ChatInputTextField(
                value = if (!isActive) onBoardingChatComponent.response else "",
                isActive = isActive
            ) {
                name = it
            }
        }
    }
    onNext(isActive && name.isNotEmpty()) {
        if (isActive) {
            viewModel.data = viewModel.data.copy(name = name)
            viewModel.addHistory(
                OnBoardingChatComponent.NameInputText(
                    id = getTime().toString(),
                    response = name
                )
            )
        }
    }
}

fun getTime() = System.currentTimeMillis()