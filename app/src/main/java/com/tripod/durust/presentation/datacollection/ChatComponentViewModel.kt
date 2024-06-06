package com.tripod.durust.presentation.datacollection

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.tripod.durust.data.PrimaryUserData
import com.tripod.durust.domain.repositories.PrimaryUserDataRepo
import com.tripod.durust.presentation.MainActivity
import kotlinx.coroutines.launch
import java.util.EnumSet.range

class ChatComponentViewModel(val userDataRepo: PrimaryUserDataRepo): ViewModel() {

    private val orderOfComponents = listOf(
        ChatComponentType.Name,
        ChatComponentType.Gender,
        ChatComponentType.Weight,
        ChatComponentType.Birthday,
        ChatComponentType.Height,
        ChatComponentType.ExerciseFrequency,
        ChatComponentType.PreferredExercise,
        ChatComponentType.StepCount,
        ChatComponentType.MealPreference,
        ChatComponentType.SleepSchedule,
        ChatComponentType.CalorieIntake,
        ChatComponentType.HealthIssues,
        ChatComponentType.RoutineCheckUpFrequency
    )
    var topComponentStep = mutableStateOf(1)
    private var activeComponentIndex = mutableIntStateOf(0)
    var activeComponent = mutableStateOf(orderOfComponents[activeComponentIndex.intValue])
    private set

    var history = mutableStateOf(listOf<OnBoardingChatComponent>())
    private set
    var data: PrimaryUserData = PrimaryUserData()

    fun addHistory(comp:OnBoardingChatComponent){
        history.value += listOf(comp)
        nextComponent()
    }

    fun nextComponent(){
        if(activeComponentIndex.intValue < orderOfComponents.size - 1){
            activeComponentIndex.intValue += 1
            activeComponent.value = orderOfComponents[activeComponentIndex.intValue]
            when(activeComponent.value){
                in range(ChatComponentType.Name, ChatComponentType.Height) -> topComponentStep.value = 1
                in range(ChatComponentType.ExerciseFrequency, ChatComponentType.SleepSchedule) -> topComponentStep.value = 2
                in range(ChatComponentType.CalorieIntake, ChatComponentType.RoutineCheckUpFrequency) -> topComponentStep.value = 3
                else-> topComponentStep.value = 3
            }
            savePersonalData()
        }else{
            savePersonalData()
            
        }
    }

    fun savePersonalData(){
        MainActivity.primaryUserData.value = data
        viewModelScope.launch {
            userDataRepo.setUserData(data)
        }
    }
}

class ChatComponentViewModelFactory(val userDataRepo: PrimaryUserDataRepo): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ChatComponentViewModel::class.java))
            return ChatComponentViewModel(userDataRepo) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
