package com.tripod.durust.presentation.Loading

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.tripod.durust.data.AllTrackEntities
import com.tripod.durust.domain.repositories.AppointmentRepository
import com.tripod.durust.domain.repositories.PrimaryUserDataRepo
import com.tripod.durust.domain.repositories.TaskRepository
import com.tripod.durust.domain.repositories.TrackRepository
import com.tripod.durust.presentation.MainActivity
import com.tripod.durust.presentation.datacollection.NavChatScreen
import com.tripod.durust.presentation.home.NavHomeScreen
import kotlinx.coroutines.launch

class LoadingViewModel(val auth: FirebaseAuth) : ViewModel() {
    private val primaryUserDataRepo = PrimaryUserDataRepo(auth)
    private val trackRepository = TrackRepository(auth)
    private val taskRepository = TaskRepository(auth)
    private val appointmentRepository = AppointmentRepository(auth)
    var userData: AllTrackEntities = AllTrackEntities()
    val isLoadingComplete = mutableStateOf(false)
    val isUserPrimaryDataFetched = mutableStateOf(false)
    val isUserAppointmentDataFetched = mutableStateOf(false)
    val isUserTaskDataFetched = mutableStateOf(false)

    init {
        fetchData()
        fetchTasks()
        fetchAppointments()
    }


    fun fetchData() {
        if (auth.currentUser == null)
            return
        fetchPrimaryData()
        fetchTasks()
        fetchAppointments()
    }

    private fun fetchPrimaryData() = viewModelScope.launch {
        val data = primaryUserDataRepo.getUserData()
        Log.i("loadingViewModel", "Primary data fetched: $data")
        MainActivity.primaryUserData.value = data
        userData = userData.copy(isPrimaryUserData = data)
        isUserPrimaryDataFetched.value = true
        isLoadingComplete()
    }

    fun isLoadingComplete() {
        Log.i("loadingViewModel", "Loading complete ${MainActivity.primaryUserData.value} " +
                "${MainActivity.tasks.value} ${MainActivity.appointments.value}")

        isLoadingComplete.value =
        isUserPrimaryDataFetched.value && isUserTaskDataFetched.value && isUserAppointmentDataFetched.value
        Log.i("loadingViewModel", "Loading complete ${isLoadingComplete.value}")
    }

    fun onCompleterFetch(navController: NavController) {
         if (MainActivity.primaryUserData.value != null) {
            navController.navigate(NavHomeScreen())
        } else
            navController.navigate(NavChatScreen)
    }

    fun fetchTasks() = viewModelScope.launch {
        val fetchedTasks = taskRepository.fetchTasks()
        if (fetchedTasks != null) {
            MainActivity.tasks.value = fetchedTasks.tasks
        } else {
            // Handle the error
            Log.e("LoadingViewModel", "Error fetching tasks")
        }
        isUserTaskDataFetched.value = true
        isLoadingComplete()
    }

    fun fetchAppointments() = viewModelScope.launch {
        val fetchedAppointments = appointmentRepository.fetchAppointments()
        if (fetchedAppointments != null) {
            MainActivity.appointments.value = fetchedAppointments
        } else {
            // Handle the error
            Log.e("LoadingViewModel", "Error fetching appointments")
        }
        isUserAppointmentDataFetched.value = true
        isLoadingComplete()
    }


//    private fun fetchExerciseTrack() = viewModelScope.launch {
//        val data = trackRepository.getAllExerciseTracks()
//        Log.i("loadingViewModel", "ExerciseTrack data fetched: $data")
//        userData = userData.copy(exerciseTrackEntity = data)
//    }

//    private fun fetchSleepTrack() = viewModelScope.launch {
//        val data = trackRepository.getSleepTrack()
//        Log.i("loadingViewModel", "SleepTrack data fetched: $data")
//        userData = userData.copy(sleepTrackEntity = data)
//    }
//
//    private fun fetchMedicineTrack() = viewModelScope.launch {
//        val data = trackRepository.getMedicineTrack()
//        Log.i("loadingViewModel", "MedicineTrack data fetched: $data")
//        userData = userData.copy(medicineTrackEntity = data)
//    }
//
//    private fun fetchWaterTrack() = viewModelScope.launch {
//        val data = trackRepository.getWaterTrack()
//        Log.i("loadingViewModel", "WaterTrack data fetched: $data")
//        userData = userData.copy(waterTrackEntity = data)
//    }
//
//    private fun fetchFoodTrack() = viewModelScope.launch {
//        val data = trackRepository.getFoodTrack()
//        Log.i("loadingViewModel", "FoodTrack data fetched: $data")
//        userData = userData.copy(foodTrackEntity = data)
//    }
//
//    private fun fetchWeightTrack() = viewModelScope.launch {
//        val data = trackRepository.getWeightTrack()
//        Log.i("loadingViewModel", "WeightTrack data fetched: $data")
//        userData = userData.copy(weightTrackEntity = data)
//    }
//
//    private fun fetchBloodPressureTrack() = viewModelScope.launch {
//        val data = trackRepository.getBloodPressureTrack()
//        Log.i("loadingViewModel", "BloodPressureTrack data fetched: $data")
//        userData = userData.copy(bloodPressureTrackEntity = data)
//    }
//
//    private fun fetchGlucoseTrack() = viewModelScope.launch {
//        val data = trackRepository.getGlucoseTrack()
//        Log.i("loadingViewModel", "GlucoseTrack data fetched: $data")
//        userData = userData.copy(glucoseTrackEntity = data)
//    }

}

class LoadingViewModelFactory(val auth: FirebaseAuth) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoadingViewModel::class.java))
            return LoadingViewModel(auth) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}