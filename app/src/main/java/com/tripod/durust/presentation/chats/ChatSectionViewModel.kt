package com.tripod.durust.presentation.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ChatSectionViewModel: ViewModel() {

}

class ChatSectionViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ChatSectionViewModel::class.java)){
            return ChatSectionViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}