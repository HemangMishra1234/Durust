package com.tripod.durust.presentation.chats

import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.tripod.durust.BuildConfig
import com.tripod.durust.GeminiUiState
import com.tripod.durust.presentation.MainActivity
import com.tripod.durust.presentation.chats.data.BotComponent
import com.tripod.durust.presentation.chats.data.BotUiState
import com.tripod.durust.presentation.chats.data.GeminiData
import com.tripod.durust.presentation.chats.individual.DataEntryCarouselEntity
import com.tripod.durust.presentation.chats.individual.GlucoseTestType
import com.tripod.durust.presentation.chats.individual.MedicineFrequency
import com.tripod.durust.presentation.chats.individual.MedicineTime
import com.tripod.durust.presentation.chats.individual.MenuOptions
import com.tripod.durust.presentation.chats.individual.StatsEntity
import com.tripod.durust.presentation.chats.individual.calculateCaloriesBurnt
import com.tripod.durust.presentation.datacollection.ExerciseType
import com.tripod.durust.presentation.datacollection.TimeEntity
import com.tripod.durust.presentation.datacollection.WakeSleepEntity
import com.tripod.durust.presentation.datacollection.formatTime
import com.tripod.durust.presentation.datacollection.getTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GeminiViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<GeminiUiState> =
        MutableStateFlow(GeminiUiState.Initial)
    val geminiUiState: StateFlow<GeminiUiState> =
        _uiState.asStateFlow()
    val chatUiState = mutableStateOf(BotUiState.MENU)
    var currentPrompt = GeminiPrompts.DEFAULT
    val needScroll = mutableStateOf(false)

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash-latest",
        apiKey = BuildConfig.apiKey
    )

    val data = mutableStateOf(GeminiData())

    var history = mutableStateOf(listOf<BotComponent>())
        private set

    fun addHistory(comp: BotComponent) {
        history.value += listOf(comp)
        needScroll.value = true
    }


    fun onClick(snackbarHostState: SnackbarHostState, prompt: String) {
        if (prompt.isEmpty()) {
            if (chatUiState.value == BotUiState.MENU) {
                showSnackBar(snackbarHostState, "Please select an option from the menu")
                return
            }
            //Step 1: Check if this state is a particular botstate
            when (chatUiState.value) {
                BotUiState.DE_CAROUSEL_STATE -> {
                    if (data.value.selectedCarouselEntity == null) {
                        showSnackBar(snackbarHostState, "Please select an option from the list")
                        return
                    }
                    onDECarouselStateSelected(data.value.selectedCarouselEntity!!)
                    return
                }

                BotUiState.DE_EXERCISE_TYPE_CAROUSEL_STATE -> {
                    // Similar steps for DE_EXERCISE_TYPE_CAROUSEL_STATE
                    // Replace `selectedExerciseType` with the actual field you need to check
                    if (data.value.selectedExerciseType == null) {
                        showSnackBar(snackbarHostState, "Please select an option from the list")
                        return
                    }
                    onDEExerciseTypeCarouselStateSelected(data.value.selectedExerciseType!!)
                    return
                }

                BotUiState.DE_EXERCISE_DURATION_STATE -> {
                    // Similar steps for DE_EXERCISE_DURATION_STATE
                    // Replace `exerciseDuration` with the actual field you need to check
                    if (data.value.exerciseDuration == null) {
                        showSnackBar(snackbarHostState, "Please enter the exercise duration")
                        return
                    }
                    onDEExerciseDurationStateSelected(data.value.exerciseDuration!!)
                    return
                }

                BotUiState.DE_EXERCISE_ADD_ONE_MORE_STATE -> {
                    // Similar steps for DE_EXERCISE_ADD_ONE_MORE_STATE
                    // Replace `shouldAddExercise` with the actual field you need to check
                    if (data.value.shouldAddExercise == null) {
                        showSnackBar(
                            snackbarHostState,
                            "Please select whether to add one more exercise"
                        )
                        return
                    }
                    onDEExerciseAddOneMoreStateSelected(data.value.shouldAddExercise!!)
                    return
                }

                BotUiState.DE_SLEEP_DURATION_STATE -> {
                    // Similar steps for DE_SLEEP_DURATION_STATE
                    // Replace `sleepDuration` with the actual field you need to check
                    if (data.value.sleepDuration == null) {
                        showSnackBar(snackbarHostState, "Please enter the sleep duration")
                        return
                    }
                    onDESleepDurationStateSelected(data.value.sleepDuration!!)
                    return
                }

                BotUiState.DE_MEDICINE_SEARCH_STATE -> {
                    // Similar steps for DE_MEDICINE_SEARCH_STATE
                    // Replace `medicineName` with the actual field you need to check
                    if (data.value.medicineName == null) {
                        showSnackBar(snackbarHostState, "Please enter the medicine name")
                        return
                    }
                    onDEMedicineSearchStateSelected(data.value.medicineName!!)
                    return
                }

                BotUiState.DE_MEDICINE_TIME ->{
                    if(data.value.medicineTime == null){
                        showSnackBar(snackbarHostState, "Please select your medicine time")
                        return
                    }
                    onDEMedicineTimeSelected(data.value.medicineTime!!)
                    return
                }

                BotUiState.DE_MEDICINE_FREQUENCY_IS_DAILY -> {
                    // Similar steps for DE_MEDICINE_FREQUENCY_IS_DAILY
                    // Replace `isMedicineDaily` with the actual field you need to check
                    if (data.value.isMedicineDaily == null) {
                        showSnackBar(
                            snackbarHostState,
                            "Please select whether the medicine frequency is daily"
                        )
                        return
                    }
                    onDEMedicineFrequencyIsDailySelected(data.value.isMedicineDaily!!)
                    return
                }

                BotUiState.DE_MEDICINE_WEEKDAYS_STATE -> {
                    //Step 2: Check if data is null, it means user has not reacted to the ui changes
                    if (data.value.medicineFrequency == null) {
                        showSnackBar(
                            snackbarHostState,
                            "Please select the weekdays for the medicine"
                        )
                        return
                    }
                    //Step 3: if user has reacted to ui changes update the state
                    onDEMedicineWeekdaysStateSelected(data.value.medicineFrequency!!)
                    return
                }

                BotUiState.DE_MEDICINE_NOTIFICATION_STATE -> {
                    if (data.value.shouldNotifyForMedicine == null) {
                        showSnackBar(
                            snackbarHostState,
                            "Please select whether to notify for the medicine"
                        )
                        return
                    }
                    onDEMedicineNotificationStateSelected(data.value.shouldNotifyForMedicine!!)
                    return
                }

                BotUiState.DE_MEDICINE_NOTIFICATION_TIME_STATE -> {
                    if (data.value.medicineNotifcationTime == null) {
                        showSnackBar(snackbarHostState, "Please enter the time for the medicine")
                        return
                    }
                    onDEMedicineNotificationTimeStateSelected(data.value.medicineNotifcationTime!!)
                    return
                }

                BotUiState.DE_MEDICINE_ADD_ONE_MORE_STATE -> {
                    if (data.value.shouldAddMedicine == null) {
                        showSnackBar(
                            snackbarHostState,
                            "Please select whether to add one more medicine"
                        )
                        return
                    }
                    onDEMedicineAddOneMoreStateSelected(data.value.shouldAddMedicine!!)
                    return
                }

                BotUiState.DE_STATS_CAROUSEL_STATE -> {
                    if (data.value.selectedStatsEntity == null) {
                        showSnackBar(snackbarHostState, "Please select an option from the list")
                        return
                    }
                    onDEStatsCarouselStateSelected(data.value.selectedStatsEntity!!)
                    return
                }

                BotUiState.DE_STATS_WEIGHT_ENTRY_STATE -> {
                    if (data.value.weight == null) {
                        showSnackBar(snackbarHostState, "Please enter a weight")
                        return
                    }
                    onDEStatsWeightEntryStateSelected(data.value.weight!!)
                    return
                }

                BotUiState.DE_STATS_BLOOD_PRESSURE_SYSTOLIC_ENTRY_STATE -> {
                    if (data.value.systolicPressure == null) {
                        showSnackBar(snackbarHostState, "Please enter a systolic pressure")
                        return
                    }
                    onDEStatsBloodPressureSystolicEntryStateSelected(data.value.systolicPressure!!)
                    return
                }

                BotUiState.DE_STATS_BLOOD_PRESSURE_DIASTOLIC_ENTRY_STATE -> {
                    if (data.value.diastolicPressure == null) {
                        showSnackBar(snackbarHostState, "Please enter a diastolic pressure")
                        return
                    }
                    onDEStatsBloodPressureDiastolicEntryStateSelected(data.value.diastolicPressure!!)
                    return
                }

                BotUiState.DE_STATS_GLUCOSE_TEST_TYPE_STATE -> {
                    if (data.value.glucoseTestType == null) {
                        showSnackBar(snackbarHostState, "Please select a glucose test type")
                        return
                    }
                    onGlucoseTestTypeSelected(data.value.glucoseTestType!!)
                    return
                }

                BotUiState.DE_STATS_GLUCOSE_ENTRY_STATE -> {
                    if (data.value.glucoseLevel == null) {
                        showSnackBar(snackbarHostState, "Please enter a glucose level")
                        return
                    }
                    onDEStatsGlucoseEntryStateSelected(data.value.glucoseLevel!!)
                    return
                }

                else -> {

                }
            }
        } else
            sendPrompt(prompt)
    }

    fun caloriesBurntDataEntry() {}
    fun saveMedicineData() {
        //work with a copy of data it can be null at the next instant
        //Saving state without notification
    }

    fun saveBP() {}


    fun sendPrompt(
        prompt: String
    ) {
        data.value = GeminiData()
        _uiState.value = GeminiUiState.Loading
        addHistory(BotComponent.UserResponseState("1", prompt))
        chatUiState.value = BotUiState.GEMINI
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = generativeModel.generateContent(
                    content {
                        text(prompt)
                    }
                )
                _uiState.value = GeminiUiState.Loading
                response.text?.let { outputContent ->
                    addHistory(
                        BotComponent.GeminiResponseState(
                            getTime().toString(),
                            outputContent.replace("*", "")
                        )
                    )
                    _uiState.value = GeminiUiState.Success("")
                    chatUiState.value = BotUiState.MENU
                }
            } catch (e: Exception) {
                _uiState.value = GeminiUiState.Error(e.localizedMessage ?: "")
            }
        }
    }

    fun showSnackBar(snackbarHostState: SnackbarHostState, message: String) {
        viewModelScope.launch {
            snackbarHostState.showSnackbar(message)
        }
    }

    fun makeExerciseDataNull() {
        data.value = data.value.copy(selectedExerciseType = null)
        data.value = data.value.copy(exerciseDuration = null)
    }

    fun makeMedicineDataNull() {
        data.value = data.value.copy(medicineName = null)
        data.value = data.value.copy(medicineTime = null)
        data.value = data.value.copy(isMedicineDaily = null)
        data.value = data.value.copy(medicineFrequency = null)
        data.value = data.value.copy(shouldNotifyForMedicine = null)
        data.value = data.value.copy(medicineNotifcationTime = null)
    }


    fun onMenuOptionSelected(menuOption: MenuOptions) {
        addHistory(BotComponent.MenuState(getTime().toString(), menuOption))
        addHistory(BotComponent.UserResponseState(getTime().toString(), menuOption.displayName))
        needScroll.value = true
        //Implement the logic to handle the menu option selected
        if (menuOption == MenuOptions.DATA_ENTRY) {
            chatUiState.value = BotUiState.DE_CAROUSEL_STATE
            return
        }
    }

    private fun onDECarouselStateSelected(selectedOption: DataEntryCarouselEntity) {
        //Step 1: Add the selected option to the history
        addHistory(BotComponent.DECarouselState(getTime().toString(), selectedOption))
        //Step 2: Add response to the history
        addHistory(
            BotComponent.UserResponseState(
                id = getTime().toString(),
                response = selectedOption.displayName
            )
        )
        //Step 3: Implement the logic to handle the selected option
        when (selectedOption) {
            DataEntryCarouselEntity.EXERCISE -> {
                makeExerciseDataNull()
                chatUiState.value = BotUiState.DE_EXERCISE_TYPE_CAROUSEL_STATE
            }

            DataEntryCarouselEntity.SLEEP -> {
                chatUiState.value = BotUiState.DE_SLEEP_DURATION_STATE
            }

            DataEntryCarouselEntity.MEDICINE -> {
                makeMedicineDataNull()
                chatUiState.value = BotUiState.DE_MEDICINE_SEARCH_STATE
            }

            DataEntryCarouselEntity.YOUR_STATS -> {
                chatUiState.value = BotUiState.DE_STATS_CAROUSEL_STATE
            }

            DataEntryCarouselEntity.FOOD -> {
                //TODO: Implement the logic for food
            }

            DataEntryCarouselEntity.WATER -> {
                //TODO: Implement the logic for water
            }
        }
        //Step 4: Update the data
        data.value = data.value.copy(selectedCarouselEntity = null)
    }

    private fun onDEExerciseTypeCarouselStateSelected(selectedOption: ExerciseType) {
        // Step 1: Add the selected option to the history
        addHistory(BotComponent.DEExerciseTypeCarouselState(getTime().toString(), selectedOption))

        // Step 2: Add response to the history
        addHistory(
            BotComponent.UserResponseState(
                id = getTime().toString(),
                response = selectedOption.exerciseName
            )
        )

        // Step 3: Implement the logic to handle the selected option
        chatUiState.value = BotUiState.DE_EXERCISE_DURATION_STATE

    }

    private fun onDEExerciseDurationStateSelected(duration: TimeEntity) {
        // Step 1: Add the selected duration to the history
        addHistory(BotComponent.DEExerciseDurationState(getTime().toString(), duration))

        // Step 2: Add response to the history
        addHistory(
            BotComponent.UserResponseState(
                id = getTime().toString(),
                response = "${duration.hour} hours and ${duration.minute} minutes"
            )
        )

        // Step 3: Implement the logic to handle the selected duration
        // Here, you might want to update the UI state based on the selected exercise duration.
        // For example, you might want to move to the next UI state after the exercise duration is selected.
        // This is just a placeholder. Replace it with your actual logic.
        chatUiState.value = BotUiState.DE_EXERCISE_ADD_ONE_MORE_STATE
    }

    fun onDEExerciseAddOneMoreStateSelected(shouldAdd: Boolean) {
        // Step 1: Add the selected option to the history
        addHistory(BotComponent.DEExerciseAddOneMoreState(getTime().toString(), shouldAdd))

        // Step 2: Add response to the history
        addHistory(
            BotComponent.UserResponseState(
                id = getTime().toString(),
                response = if(shouldAdd)"Add" else "Skip"
            )
        )
        if (data.value.selectedExerciseType != null && data.value.exerciseDuration != null && MainActivity.primaryUserData.value != null) {
            val caloriesBurnt = calculateCaloriesBurnt(
                exerciseType = data.value.selectedExerciseType!!,
                duration = data.value.exerciseDuration!!,
                height = MainActivity.primaryUserData.value!!.height,
                weight = MainActivity.primaryUserData.value!!.weight
            )
            addHistory(
                BotComponent.GeminiResponseState(
                    getTime().toString(),
                    "You burnt $caloriesBurnt calories!!"
                )
            )
        } else {
            Log.e(
                "GeminiViewModel",
                "Some error occurred showing calories burnt ${data.value} ${MainActivity.primaryUserData.value}"
            )
            addHistory(
                BotComponent.GeminiResponseState(
                    getTime().toString(),
                    "Some error occurred showing calories burnt"
                )
            )
        }
        when (shouldAdd) {
            true -> {
                makeExerciseDataNull()
                // Assuming the next state is DE_EXERCISE_TYPE_CAROUSEL_STATE
                chatUiState.value = BotUiState.DE_EXERCISE_TYPE_CAROUSEL_STATE
            }

            false -> {
                // Assuming the next state is DE_CAROUSEL_STATE
                chatUiState.value = BotUiState.MENU
            }
        }
        // Step 4: Update the data
        data.value = data.value.copy(shouldAddExercise = null)
    }

    private fun onDESleepDurationStateSelected(duration: WakeSleepEntity) {
        // Step 1: Add the selected duration to the history
        addHistory(BotComponent.DESleepDurationState(getTime().toString(), duration))

        // Step 2: Add response to the history
        addHistory(
            BotComponent.UserResponseState(
                id = getTime().toString(),
                response = "WakeUp Time: ${duration.wakeTime.hour} hours and ${duration.wakeTime.minute} minutes\n"+
                "Sleep Time:${duration.sleepTime.hour} hours and ${duration.sleepTime.minute} minutes"
            )
        )

        // Step 3: Implement the logic to handle the selected duration
        //TODO Save the data here
        addHistory(
            BotComponent.GeminiResponseState(
                getTime().toString(),
                analyzeSleepQuality(duration)
            )
        )
        // For example, you might want to move to the next UI state after the sleep duration is selected.
        // This is just a placeholder. Replace it with your actual logic.
        chatUiState.value = BotUiState.MENU

        // Step 4: Update the data
        data.value = data.value.copy(sleepDuration = null)
    }

    private fun onDEMedicineSearchStateSelected(medicine: String) {
        // Step 1: Add the selected medicine to the history
        addHistory(BotComponent.DEMedicineSearchState(getTime().toString(), medicine))

        // Step 2: Add response to the history
//        addHistory(BotComponent.UserResponseState(id = getTime().toString(), response = medicine))

        // Step 3: Implement the logic to handle the selected medicine
        // Here, you might want to update the UI state based on the selected medicine.
        // For example, you might want to move to the next UI state after the medicine is selected.
        // This is just a placeholder. Replace it with your actual logic.
        chatUiState.value = BotUiState.DE_MEDICINE_TIME
    }

    private fun onDEMedicineTimeSelected(time: MedicineTime){
        // Step 1: Add the selected time to the history
        addHistory(BotComponent.DEMedicineTimeState(getTime().toString(), time))

        // Step 2: Add response to the history
        addHistory(
            BotComponent.UserResponseState(
                id = getTime().toString(),
                response = time.displayName
            )
        )

        chatUiState.value = BotUiState.DE_MEDICINE_FREQUENCY_IS_DAILY

    }

    fun onDEMedicineFrequencyIsDailySelected(isDaily: Boolean) {
        // Step 1: Add the selected option to the history
        addHistory(BotComponent.DEMedicineFrequencyIsDaily(getTime().toString(), isDaily))

        // Step 2: Add response to the history
        addHistory(
            BotComponent.UserResponseState(
                id = getTime().toString(),
                response = isDaily.toString()
            )
        )

        // Step 3: Implement the logic to handle the selected option
        // Here, you might want to update the UI state based on the selected option.
        when (isDaily) {
            true -> {
                chatUiState.value = BotUiState.DE_MEDICINE_NOTIFICATION_STATE
            }

            false -> {
                chatUiState.value = BotUiState.DE_MEDICINE_WEEKDAYS_STATE
            }
        }

        // Step 4: Update the data
    }

    private fun onDEMedicineWeekdaysStateSelected(frequency: List<MedicineFrequency>) {
        // Step 1: Add the selected frequency to the history
        addHistory(BotComponent.DEMedicineWeekdaysState(getTime().toString(), frequency))

        // Step 2: Add response to the history
        addHistory(
            BotComponent.UserResponseState(
                id = getTime().toString(),
                response = frequency.toString()
            )
        )

        // Step 3: Implement the logic to handle the selected frequency
        // Here, you might want to update the UI state based on the selected frequency.
        // For example, you might want to move to the next UI state after the frequency is selected.
        // This is just a placeholder. Replace it with your actual logic.
        chatUiState.value = BotUiState.DE_MEDICINE_NOTIFICATION_STATE

        // Step 4: Update the data
    }

    fun onDEMedicineNotificationStateSelected(shouldNotify: Boolean) {
        // Step 1: Add the selected option to the history
        addHistory(BotComponent.DEMedicineNotificationState(getTime().toString(), shouldNotify))

        // Step 2: Add response to the history
        addHistory(
            BotComponent.UserResponseState(
                id = getTime().toString(),
                response = shouldNotify.toString()
            )
        )

        // Step 3: Implement the logic to handle the selected option
        saveMedicineData()
        when (shouldNotify) {
            true -> {
                chatUiState.value = BotUiState.DE_MEDICINE_NOTIFICATION_TIME_STATE
            }

            false -> {
                makeMedicineDataNull()
                chatUiState.value = BotUiState.DE_MEDICINE_ADD_ONE_MORE_STATE
            }
        }
        // Step 4: Update the data
    }

    private fun onDEMedicineNotificationTimeStateSelected(time: TimeEntity) {
        // Step 1: Add the selected time to the history
        addHistory(BotComponent.DEMedicineNotificationTimeState(getTime().toString(), time))

        // Step 2: Add response to the history
        addHistory(
            BotComponent.UserResponseState(
                id = getTime().toString(),
                response = formatTime(time)
            )
        )

        addHistory(BotComponent.GeminiResponseState(getTime().toString(), "Well we will notify you!"))

        // Step 3: Implement the logic to handle the selected time
        // Here, you might want to update the UI state based on the selected time.
        // For example, you might want to move to the next UI state after the time is selected.
        // This is just a placeholder. Replace it with your actual logic.
        saveMedicineData()
        chatUiState.value = BotUiState.DE_MEDICINE_ADD_ONE_MORE_STATE

        // Step 4: Update the data
    }

    fun onDEMedicineAddOneMoreStateSelected(shouldAdd: Boolean) {
        // Step 1: Add the selected option to the history
        addHistory(BotComponent.DEMedicineAddOneMoreState(getTime().toString(), shouldAdd))

        // Step 2: Add response to the history
        addHistory(
            BotComponent.UserResponseState(
                id = getTime().toString(),
                response = if (shouldAdd) "Yes" else "Skip"
            )
        )

        // Step 3: Implement the logic to handle the selected option
        when (shouldAdd) {
            true -> {
                makeMedicineDataNull()
                chatUiState.value = BotUiState.DE_MEDICINE_SEARCH_STATE
            }

            false -> {
                makeMedicineDataNull()
                chatUiState.value = BotUiState.MENU
            }
        }

        // Step 4: Update the data
        data.value = data.value.copy(shouldAddMedicine = null)
    }

    private fun onDEStatsCarouselStateSelected(selectedOption: StatsEntity) {
        // Step 1: Add the selected option to the history
        addHistory(BotComponent.DEStatsCarouselState(getTime().toString(), selectedOption))

        // Step 2: Add response to the history
        addHistory(
            BotComponent.UserResponseState(
                id = getTime().toString(),
                response = selectedOption.displayName
            )
        )

        // Step 3: Implement the logic to handle the selected option
        when (selectedOption) {
            StatsEntity.WEIGHT -> {
                chatUiState.value = BotUiState.DE_STATS_WEIGHT_ENTRY_STATE
            }

            StatsEntity.BLOOD_PRESSURE -> {
                makeBPNull()
                chatUiState.value = BotUiState.DE_STATS_BLOOD_PRESSURE_SYSTOLIC_ENTRY_STATE
            }

            StatsEntity.GLUCOSE -> {
                makeGlucoseNull()
                chatUiState.value = BotUiState.DE_STATS_GLUCOSE_TEST_TYPE_STATE
            }
        }

        // Step 4: Update the data
        data.value = data.value.copy(selectedStatsEntity = null)
    }

    private fun onDEStatsWeightEntryStateSelected(weight: Float) {
        // Step 1: Add the selected weight to the history
        addHistory(BotComponent.DEStatsWeightEntryState(getTime().toString(), weight))

        // Step 2: Add response to the history
        addHistory(
            BotComponent.UserResponseState(
                id = getTime().toString(),
                response = weight.toString()
            )
        )

        // Step 3: Implement the logic to handle the selected weight
        // Here, you might want to update the UI state based on the selected weight.
        // For example, you might want to move to the next UI state after the weight is selected.
        // This is just a placeholder. Replace it with your actual logic.
        // Assuming the next state is MENU
        chatUiState.value = BotUiState.MENU

        // Step 4: Update the data
        data.value = data.value.copy(weight = null)
    }

    private fun onDEStatsBloodPressureSystolicEntryStateSelected(systolic: Int) {
        // Step 1: Add the selected systolic pressure to the history
        addHistory(
            BotComponent.DEStatsBloodPressureSystolicEntryState(
                getTime().toString(),
                systolic
            )
        )

        // Step 2: Add response to the history
//        addHistory(
//            BotComponent.UserResponseState(
//                id = getTime().toString(),
//                response = systolic.toString()
//            )
//        )

        // Step 3: Implement the logic to handle the selected systolic pressure
        // Here, you might want to update the UI state based on the selected systolic pressure.
        // For example, you might want to move to the next UI state after the systolic pressure is selected.
        // This is just a placeholder. Replace it with your actual logic.
        chatUiState.value = BotUiState.DE_STATS_BLOOD_PRESSURE_DIASTOLIC_ENTRY_STATE

    }

    private fun makeBPNull() {
        data.value = data.value.copy(systolicPressure = null)
        data.value = data.value.copy(diastolicPressure = null)
    }

    private fun onDEStatsBloodPressureDiastolicEntryStateSelected(diastolic: Int) {
        // Step 1: Add the selected diastolic pressure to the history
        addHistory(
            BotComponent.DEStatsBloodPressureDiastolicEntryState(
                getTime().toString(),
                diastolic
            )
        )

        // Step 2: Add response to the history
//        addHistory(
//            BotComponent.UserResponseState(
//                id = getTime().toString(),
//                response = diastolic.toString()
//            )
//        )

        // Step 3: Implement the logic to handle the selected diastolic pressure
        // Here, you might want to update the UI state based on the selected diastolic pressure.
        // For example, you might want to move to the next UI state after the diastolic pressure is selected.
        // This is just a placeholder. Replace it with your actual logic.
        // Assuming the next state is MENU
        saveBP()
        chatUiState.value = BotUiState.MENU

        makeBPNull()
    }

    private fun onGlucoseTestTypeSelected(glucoseTestType: GlucoseTestType) {
        // Step 1: Add the selected glucose test type to the history
        addHistory(BotComponent.DEStatsGlucoseTestTypeState(getTime().toString(), glucoseTestType))

        // Step 2: Add response to the history
        addHistory(
            BotComponent.UserResponseState(
                id = getTime().toString(),
                response = glucoseTestType.displayName
            )
        )

        // Step 3: Implement the logic to handle the selected glucose test type
        // Here, you might want to update the UI state based on the selected glucose test type.
        // For example, you might want to move to the next UI state after the glucose test type is selected.
        // This is just a placeholder. Replace it with your actual logic.
        // Assuming the next state is DEStatsGlucoseEntryState
        chatUiState.value = BotUiState.DE_STATS_GLUCOSE_ENTRY_STATE

        // Step 4: Update the data
    }

    private fun makeGlucoseNull() {
        data.value = data.value.copy(glucoseTestType = null)
        data.value = data.value.copy(glucoseLevel = null)
    }

    private fun onDEStatsGlucoseEntryStateSelected(glucose: Int) {
        // Step 1: Add the selected glucose level to the history
        addHistory(BotComponent.DEStatsGlucoseEntryState(getTime().toString(), glucose))

        // Step 2: Add response to the history
//        addHistory(
//            BotComponent.UserResponseState(
//                id = getTime().toString(),
//                response = glucose.toString()
//            )
//        )

        // Step 3: Implement the logic to handle the selected glucose level
        // Here, you might want to update the UI state based on the selected glucose level.
        // For example, you might want to move to the next UI state after the glucose level is selected.
        // This is just a placeholder. Replace it with your actual logic.
        // Assuming the next state is MENU
        chatUiState.value = BotUiState.MENU

        // Step 4: Update the data
        makeGlucoseNull()
    }
}


class GeminiViewModelFactory(
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GeminiViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GeminiViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}