package com.tripod.durust.presentation.datacollection

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.tripod.durust.data.PrimaryUserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatComponentViewModel: ViewModel() {

    private val orderOfComponents = listOf(
        ChatComponentType.Name,
        ChatComponentType.Gender,
        ChatComponentType.Weight,
        ChatComponentType.Birthday,
        ChatComponentType.Height,
        ChatComponentType.ExerciseFrequency,
        ChatComponentType.PreferedExercise,
        ChatComponentType.StepCount,
        ChatComponentType.MealPreference,
        ChatComponentType.CalorieIntake,
        ChatComponentType.SleepSchedule,
        ChatComponentType.SleepSchedule,
        ChatComponentType.RoutineCheckUpFrequency
    )

    private var activeComponentIndex = mutableIntStateOf(5)
    var activeComponent = mutableStateOf(orderOfComponents[activeComponentIndex.intValue])
    private set

    var history = mutableStateOf(listOf<ChatComponent>())
    private set
    var data: PrimaryUserData = PrimaryUserData()

    fun addHistory(comp:ChatComponent){
        history.value += listOf(comp)
    }


    val sampleChatList: List<ChatComponent> = listOf(
        ChatComponentEntity("1", ChatComponentType.Name, "Hemang"),
        ChatComponentEntity("2", ChatComponentType.Gender, "Male")).map {
            it.toChatComponent()
    }

    fun nextComponent(){
        if(activeComponentIndex.intValue < orderOfComponents.size - 1){
//            activeComponentIndex.intValue += 1
//            activeComponent.value = orderOfComponents[activeComponentIndex.intValue]
        }
    }

    fun previousComponent(){
        if(activeComponentIndex.intValue > 0){
            activeComponentIndex.intValue -= 1
            activeComponent.value = orderOfComponents[activeComponentIndex.intValue]
        }
    }
}

class ChatComponentViewModelFactory: ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ChatComponentViewModel::class.java))
            return ChatComponentViewModel() as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
