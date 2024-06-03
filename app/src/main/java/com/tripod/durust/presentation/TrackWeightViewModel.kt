package com.tripod.durust.presentation

import android.os.RemoteException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.WeightRecord
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.tripod.durust.data.HealthConnectManager
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.UUID

class TrackWeightViewModel(val healthConnectManager: HealthConnectManager): ViewModel() {
    val permissions = setOf(
        HealthPermission.getReadPermission(WeightRecord::class),
        HealthPermission.getWritePermission(WeightRecord::class),
    )

    var permissionsGranted = mutableStateOf(false)
    private set

    val permissionLauncher =healthConnectManager.requestPermissionsActivityContract()

    var uiState:UiState by mutableStateOf(UiState.Uninitialized)

    fun initialLoad(){
        viewModelScope.launch {
            tryWithPermissionCheck {
//                readWeightInputs()
            }
        }
    }

    private suspend fun tryWithPermissionCheck(block: suspend () -> Unit){
        permissionsGranted.value = healthConnectManager.hasAllPermissions(permissions)

        uiState = try{
            if(permissionsGranted.value)
                block()
        UiState.Done
        }
        catch (remoteException : RemoteException){
            UiState.Error(remoteException)
        }
        catch (ioException: IOException){
            UiState.Error(ioException)
        }
        catch (securityException: SecurityException){
            UiState.Error(securityException)
        }
        catch (e: Exception){
            UiState.Error(e)
        }
    }


    sealed class UiState{
        object Uninitialized: UiState()
        object Done: UiState()
        data class Error(val exception: Throwable, val uuid: UUID  = UUID.randomUUID()): UiState()
    }
}

class TrackWeightViewModelFactory(val healthConnectManager: HealthConnectManager):ViewModelProvider.Factory{
override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if(modelClass.isAssignableFrom(TrackWeightViewModel::class.java))
        return TrackWeightViewModel(healthConnectManager) as T
throw IllegalArgumentException("Unknown ViewModel class")
    }

}