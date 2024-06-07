package com.tripod.durust.presentation.chats

import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.firebase.auth.FirebaseAuth
import com.tripod.durust.BuildConfig
import com.tripod.durust.GeminiUiState
import com.tripod.durust.data.AppointmentDataUpload
import com.tripod.durust.data.AppointmentEntity
import com.tripod.durust.data.DateEntity
import com.tripod.durust.domain.repositories.AppointmentRepository
import com.tripod.durust.domain.repositories.GeminiChatRepository
import com.tripod.durust.presentation.MainActivity
import com.tripod.durust.presentation.chats.data.BotComponent
import com.tripod.durust.presentation.chats.data.BotUiState
import com.tripod.durust.presentation.chats.data.GeminiData
import com.tripod.durust.presentation.chats.individual.AppointmentMenu
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
import com.tripod.durust.presentation.home.individuals.DoctorProfession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GeminiViewModel(auth: FirebaseAuth) : ViewModel() {
    private val _uiState: MutableStateFlow<GeminiUiState> =
        MutableStateFlow(GeminiUiState.Initial)
    val geminiUiState: StateFlow<GeminiUiState> =
        _uiState.asStateFlow()
    val chatUiState = mutableStateOf(BotUiState.MENU)
    var appointments by MainActivity.appointments
//    var currentPrompt = GeminiPrompts.DEFAULT
    val needScroll = mutableStateOf(false)
    private val appointmentRepository = AppointmentRepository(auth)
    private val chatRepository = GeminiChatRepository(auth)

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
        uploadChat(comp)
    }

    fun uploadChat(comp: BotComponent){
        viewModelScope.launch {
            chatRepository.uploadChatData(comp)
        }
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
                        showSnackBar(snackbarHostState, "Please enter a valid systolic pressure")
                        return
                    }
                    onDEStatsBloodPressureSystolicEntryStateSelected(data.value.systolicPressure!!)
                    return
                }

                BotUiState.DE_STATS_BLOOD_PRESSURE_DIASTOLIC_ENTRY_STATE -> {
                    if (data.value.diastolicPressure == null) {
                        showSnackBar(snackbarHostState, "Please enter a valid diastolic pressure")
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
                        showSnackBar(snackbarHostState, "Please enter a valid glucose level")
                        return
                    }
                    onDEStatsGlucoseEntryStateSelected(data.value.glucoseLevel!!)
                    return
                }

                BotUiState.DE_MEAL_TRACK_STATE -> {
                    if (data.value.meal == null) {
                        showSnackBar(snackbarHostState, "Please enter a meal")
                        return
                    }
                    onDEMealTrackStateSelected(data.value.meal!!)
                    return
                }

                BotUiState.DE_WATER_TRACK_STATE -> {
                    if (data.value.water == null) {
                        showSnackBar(snackbarHostState, "Please enter the amount of water in numbers")
                        return
                    }
                    onWaterTrackStateSelected(data.value.water!!)
                    return
                }

                BotUiState.APPOINTMENT_LIST -> {
                    if (data.value.appList == null) {
                        showSnackBar(snackbarHostState, "Please select an option from the list")
                        return
                    }
                    onAppointmentListDisplay(data.value.appList!!)
                    return
                }

                BotUiState.APPOINTMENT_TIME -> {
                    if (data.value.appTime == null) {
                        showSnackBar(snackbarHostState, "Please enter the appointment time")
                        return
                    }
                    onAppointmentTimeSelected(data.value.appTime!!)
                    return
                }

                BotUiState.APPOINTMENT_DATE -> {
                    if (data.value.appDate == null) {
                        showSnackBar(snackbarHostState, "Please enter the appointment date")
                        return
                    }
                    onAppointmentDateSelected(data.value.appDate!!)
                    return
                }

                BotUiState.APPOINTMENT_SPECIALITY -> {
                    if (data.value.appSpeciality == null) {
                        showSnackBar(snackbarHostState, "Please select a speciality")
                        return
                    }
                    onAppmtSpecialitySelected(data.value.appSpeciality!!)
                    return
                }

                BotUiState.APPOINTMENT_DOCTOR -> {
                    if (data.value.appDoctor == null) {
                        showSnackBar(snackbarHostState, "Please enter the doctor's name")
                        return
                    }
                    onAppmtDoctorSelected(data.value.appDoctor!!)
                    return
                }

                BotUiState.MOOD -> {
                    if (data.value.mood == null) {
                        showSnackBar(snackbarHostState, "Please enter your mood")
                        return
                    }
                    onMoodSelected(data.value.mood!!)
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
        prompt: String,
        basePrompts: GeminiPrompts = GeminiPrompts.DEFAULT
    ) {
        data.value = GeminiData()
        _uiState.value = GeminiUiState.Loading
        addHistory(BotComponent.UserResponseState("1", prompt))
        chatUiState.value = BotUiState.GEMINI
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = generativeModel.generateContent(
                    content {
                        text(basePrompts.prompt+prompt)
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
        when (menuOption) {
            MenuOptions.DATA_ENTRY -> {
                chatUiState.value = BotUiState.DE_CAROUSEL_STATE
            }

            MenuOptions.RECOMMENDATIONS -> {
                sendPrompt("recommendations", GeminiPrompts.RECOMMENDATION)
            }

            MenuOptions.MOOD_TODAY -> {
                chatUiState.value = BotUiState.MOOD
            }

            MenuOptions.APPOINTMENT -> {
                chatUiState.value = BotUiState.APPOINTMENT_LIST
            }
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
                chatUiState.value = BotUiState.DE_MEAL_TRACK_STATE
            }

            DataEntryCarouselEntity.WATER -> {
                chatUiState.value = BotUiState.DE_WATER_TRACK_STATE
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

        chatUiState.value = BotUiState.MENU

        // Step 4: Update the data
        makeGlucoseNull()
    }

    private fun onDEMealTrackStateSelected(meal: String) {
        // Step 1: Add the selected meal to the history

        chatUiState.value = BotUiState.MENU

        sendPrompt(prompt = meal, basePrompts = GeminiPrompts.MEAL)

        // Step 4: Update the data
        data.value = data.value.copy(meal = null)
    }

    private fun onWaterTrackStateSelected(water: Int) {
        // Step 1: Add the selected water to the history
        addHistory(BotComponent.DEWaterTrackState(getTime().toString(), water))

        addHistory(
            BotComponent.GeminiResponseState(
                id = getTime().toString(),
                response = "You drank $water l of water. Good job!"
            )
        )

        chatUiState.value = BotUiState.MENU

        // Step 4: Update the data
        data.value = data.value.copy(water = null)
    }

    fun onAppointmentListDisplay(appointmentMenu: AppointmentMenu){
        // Step 1: Add the selected appointment to the history
        addHistory(BotComponent.AppmtList(getTime().toString(), appointmentMenu))

        addHistory(BotComponent.UserResponseState(getTime().toString(), appointmentMenu.name))

        when(appointmentMenu){
            AppointmentMenu.UPCOMING_APPOINTMENT -> {
                chatUiState.value = BotUiState.UPCOMING_APPOINTMENTS
            }
            AppointmentMenu.ADD_APPOINTMENT -> {
                makeAppointmentDataNull()
                chatUiState.value = BotUiState.APPOINTMENT_DATE
            }
            AppointmentMenu.APPOINTMENT_HISTORY -> {
//                chatUiState.value = BotUiStat
                chatUiState.value = BotUiState.APPOINTMENT_HISTORY
            }
        }
        data.value = data.value.copy(appList = null)
    }

    private fun onAppointmentDateSelected(date: DateEntity) {
        // Step 1: Add the selected date to the history
        addHistory(BotComponent.AppmtDate(getTime().toString(), date))

        // Step 2: Add response to the history
        addHistory(
            BotComponent.UserResponseState(
                id = getTime().toString(),
                response = "Appointment Date: ${date.day}/${date.month}/${date.year}"
            )
        )

        chatUiState.value = BotUiState.APPOINTMENT_TIME
    }

    private fun onAppointmentTimeSelected(time: TimeEntity) {
        // Step 1: Add the selected time to the history
        addHistory(BotComponent.AppmtTime(getTime().toString(), time))

        // Step 2: Add response to the history
        addHistory(
            BotComponent.UserResponseState(
                id = getTime().toString(),
                response = "Appointment Time: ${time.hour} hours and ${time.minute} minutes"
            )
        )

        // Step 3: Implement the logic to handle the selected time
        // For example, you might want to move to the next UI state after the appointment time is selected.
        // This is just a placeholder. Replace it with your actual logic.
        chatUiState.value = BotUiState.APPOINTMENT_SPECIALITY

    }

    private fun onAppmtSpecialitySelected(speciality: DoctorProfession) {
        // Step 1: Add the selected speciality to the history
        addHistory(BotComponent.AppmtSpeciality(getTime().toString(), speciality))

        // Step 2: Add response to the history
        addHistory(
            BotComponent.UserResponseState(
                id = getTime().toString(),
                response = "Selected Speciality: ${speciality.professionName}"
            )
        )

        // For example, you might want to move to the next UI state after the speciality is selected.
        // This is just a placeholder. Replace it with your actual logic.
        chatUiState.value = BotUiState.APPOINTMENT_DOCTOR

    }

    private fun onAppmtDoctorSelected(doctor: String) {
        // Step 1: Add the selected doctor to the history
        addHistory(BotComponent.AppmtDoctor(getTime().toString(), doctor))

        // Step 2: Add response to the history
        addHistory(
            BotComponent.UserResponseState(
                id = getTime().toString(),
                response = "Doctor: $doctor"
            )
        )

        // Step 3: Implement the logic to handle the selected doctor
        //TODO Save the data here
        saveAppointement()
        addHistory(
            BotComponent.GeminiResponseState(
                getTime().toString(),
                "Appointment added successfully."
            )
        )
        // For example, you might want to move to the next UI state after the doctor is selected.
        // This is just a placeholder. Replace it with your actual logic.
        chatUiState.value = BotUiState.MENU

        // Step 4: Update the data
        data.value = data.value.copy(appDoctor = null)
        makeAppointmentDataNull()
    }

    private fun onMoodSelected(mood: String) {
        // Step 1: Add the selected mood to the history

        // Step 2: Add response to the history


        sendPrompt(prompt = mood, basePrompts = GeminiPrompts.MOOD)
        // Step 3: Implement the logic to handle the selected mood

        // For example, you might want to move to the next UI state after the mood is selected.
        // This is just a placeholder. Replace it with your actual logic.

        // Step 4: Update the data
        data.value = data.value.copy(mood = null)
    }

    fun makeAppointmentDataNull(){
        data.value = data.value.copy(appDate = null)
        data.value = data.value.copy(appTime = null)
        data.value = data.value.copy(appSpeciality = null)
        data.value = data.value.copy(appDoctor = null)
    }

    fun saveAppointement(){
        val currentApp = appointments.toMutableList()
        if(data.value.appDate == null || data.value.appTime == null || data.value.appSpeciality == null || data.value.appDoctor == null){
            Log.e("GeminiViewModel", "Some error occurred saving appointment")
            return
        }
        uploadAppointment(AppointmentEntity(
            date = data.value.appDate!!,
            time = data.value.appTime!!,
            doctorSpeciality = data.value.appSpeciality!!,
            doctorName = data.value.appDoctor!!
        ))
        currentApp.add(AppointmentEntity(
            date = data.value.appDate!!,
            time = data.value.appTime!!,
            doctorSpeciality = data.value.appSpeciality!!,
            doctorName = data.value.appDoctor!!))
        appointments = currentApp
    }

    fun uploadAppointment(appointmentEntity: AppointmentEntity){
        viewModelScope.launch {
            val result = appointmentRepository.uploadAppointment(appointmentEntity)
        }
    }

}


class GeminiViewModelFactory(val auth: FirebaseAuth
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GeminiViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GeminiViewModel(auth) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}