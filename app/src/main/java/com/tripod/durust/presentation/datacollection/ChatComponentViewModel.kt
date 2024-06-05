package com.tripod.durust.presentation.datacollection

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tripod.durust.data.PrimaryUserData
import com.tripod.durust.presentation.MainActivity

class ChatComponentViewModel: ViewModel() {

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

    private var activeComponentIndex = mutableIntStateOf(0)
    var activeComponent = mutableStateOf(orderOfComponents[activeComponentIndex.intValue])
    private set

    var history = mutableStateOf(listOf<ChatComponent>())
    private set
    var data: PrimaryUserData = PrimaryUserData()

    fun addHistory(comp:ChatComponent){
        history.value += listOf(comp)
        nextComponent()
    }

    fun nextComponent(){
        if(activeComponentIndex.intValue < orderOfComponents.size - 1){
            activeComponentIndex.intValue += 1
            activeComponent.value = orderOfComponents[activeComponentIndex.intValue]
        }
    }

    fun previousComponent(){
        if(activeComponentIndex.intValue > 0){
            activeComponentIndex.intValue -= 1
            activeComponent.value = orderOfComponents[activeComponentIndex.intValue]
        }
    }

    fun savePersonalData(){

    }
}

class ChatComponentViewModelFactory: ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ChatComponentViewModel::class.java))
            return ChatComponentViewModel() as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
