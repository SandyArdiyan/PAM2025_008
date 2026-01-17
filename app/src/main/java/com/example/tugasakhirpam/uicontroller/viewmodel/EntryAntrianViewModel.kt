package com.example.tugasakhirpam.uicontroller.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.tugasakhirpam.repositori.RepositoryAntrian

// Jangan tulis 'data class DetailAntrian' lagi di sini!
// Class ini otomatis akan membaca DetailAntrian dari AntrianViewModel.kt karena satu package.

class EntryAntrianViewModel(private val repositoryAntrian: RepositoryAntrian) : ViewModel() {
    var uiStateAntrian by mutableStateOf(InsertUiState())
        private set

    fun updateUiState(detailAntrian: DetailAntrian) {
        uiStateAntrian = InsertUiState(insertUiEvent = detailAntrian)
    }

    suspend fun saveAntrian() {
        try {
            repositoryAntrian.insertAntrian(uiStateAntrian.insertUiEvent.toAntrian())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}