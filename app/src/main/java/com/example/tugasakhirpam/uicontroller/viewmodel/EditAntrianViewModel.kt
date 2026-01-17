package com.example.tugasakhirpam.uicontroller.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpam.repositori.RepositoryAntrian
import com.example.tugasakhirpam.uicontroller.route.DestinasiEdit
import kotlinx.coroutines.launch

class EditAntrianViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryAntrian: RepositoryAntrian
) : ViewModel() {

    var antrianUiState by mutableStateOf(InsertUiState())
        private set

    // Pastikan key ini sama dengan di DestinasiEdit
    val itemId: String = checkNotNull(savedStateHandle[DestinasiEdit.itemIdArg])

    init {
        viewModelScope.launch {
            try {
                val antrian = repositoryAntrian.getAntrianById(itemId)
                antrianUiState = antrian.toUiStateAntrian()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateUiState(detailAntrian: DetailAntrian) {
        antrianUiState = InsertUiState(insertUiEvent = detailAntrian)
    }

    suspend fun updateAntrian() {
        try {
            repositoryAntrian.updateAntrian(itemId, antrianUiState.insertUiEvent.toAntrian())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}