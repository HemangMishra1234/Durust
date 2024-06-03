package com.tripod.durust.presentation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tripod.durust.data.HealthConnectManager
import java.util.UUID

@Composable
fun TrackWeight(healthConnectManager: HealthConnectManager){
    val viewModel: TrackWeightViewModel = viewModel(factory = TrackWeightViewModelFactory(healthConnectManager))
    val onPermissionResult = {viewModel.initialLoad()}
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = viewModel.permissionLauncher) {
        onPermissionResult()
    }
    val errorId = rememberSaveable {
        mutableStateOf(UUID.randomUUID())
    }
//    val uiState by viewModel.uiState
    val uiState = viewModel.uiState

    LaunchedEffect(key1 = uiState) {
        if(uiState is TrackWeightViewModel.UiState.Uninitialized){
            permissionLauncher.launch(viewModel.permissions)
        }

        if(uiState is TrackWeightViewModel.UiState.Error && errorId.value != uiState.uuid){
            //TODO(onError(uiState.exception))
            errorId.value =uiState.uuid
        }
    }

    if(uiState != TrackWeightViewModel.UiState.Uninitialized){
        if(!viewModel.permissionsGranted.value){
            Button(onClick = { permissionLauncher.launch(viewModel.permissions)}) {
                Text(text ="GrantPermission")
            }
        }
        else{
            Text(text = "Permission Granted")
        }
    }
}