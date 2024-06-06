package com.tripod.durust.presentation.Loading

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.tripod.durust.data.PrimaryUserData
import com.tripod.durust.domain.repositories.PrimaryUserDataRepo
import com.tripod.durust.presentation.MainActivity
import com.tripod.durust.presentation.chats.NavBotScreen
import com.tripod.durust.presentation.datacollection.NavChatScreen
import kotlinx.coroutines.launch

class LoadingViewModel(val auth: FirebaseAuth) : ViewModel() {
    private val primaryUserDataRepo = PrimaryUserDataRepo(auth)
    var primaryUserData: PrimaryUserData? = null
    val isLoadingComplete = mutableStateOf(false)

    init {
        fetchData()
    }

    fun fetchData() {
        if (auth.currentUser == null)
            return
        fetchPrimaryData()
    }

    private fun fetchPrimaryData() = viewModelScope.launch {
        val data = primaryUserDataRepo.getUserData()
        Log.i("loadingViewModel", "Primary data fetched: $data")
        MainActivity.primaryUserData.value = data
        primaryUserData = data
        isLoadingComplete.value = true
    }


    fun onCompleterFetch(navController: NavController) {
        if (primaryUserData != null) {
            navController.navigate(NavBotScreen)
        } else
            navController.navigate(NavChatScreen)
    }

}

class LoadingViewModelFactory(val auth: FirebaseAuth) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoadingViewModel::class.java))
            return LoadingViewModel(auth) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}