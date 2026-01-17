package com.example.tugasakhirpam.uicontroller.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpam.modeldata.Antrian
import com.example.tugasakhirpam.repositori.RepositoryAntrian
import kotlinx.coroutines.launch
import java.io.IOException

// ==========================================
// 1. DATA CLASS BERSAMA (HANYA DITULIS DI SINI)
// ==========================================
data class DetailAntrian(
    val id: String = "",
    val namaPasien: String = "",
    val noRekamMedis: String = "",
    val poli: String = "",
    val alamat: String = "",
    val dokter: String = "",
    val tanggal: String = "",
    val status: String = "Menunggu"
)

data class InsertUiState(
    val insertUiEvent: DetailAntrian = DetailAntrian()
)

// Fungsi Konversi
fun DetailAntrian.toAntrian(): Antrian = Antrian(
    id = id,
    namaPasien = namaPasien,
    noRekamMedis = noRekamMedis,
    poli = poli,
    alamat = alamat,
    dokter = dokter,
    tanggal = tanggal,
    status = status
)

fun Antrian.toDetailAntrian(): DetailAntrian = DetailAntrian(
    id = id ?: "",
    namaPasien = namaPasien ?: "",
    noRekamMedis = noRekamMedis ?: "",
    poli = poli ?: "",
    alamat = alamat ?: "",
    dokter = dokter ?: "",
    tanggal = tanggal ?: "",
    status = status ?: "Menunggu"
)

fun Antrian.toUiStateAntrian(): InsertUiState = InsertUiState(
    insertUiEvent = this.toDetailAntrian()
)

// ==========================================
// 2. VIEWMODEL HOME
// ==========================================
sealed interface AntrianUiState {
    data class Success(val antrian: List<Antrian>) : AntrianUiState
    object Error : AntrianUiState
    object Loading : AntrianUiState
}

class AntrianViewModel(private val repositoryAntrian: RepositoryAntrian) : ViewModel() {
    var antrianUiState: AntrianUiState by mutableStateOf(AntrianUiState.Loading)
        private set

    init {
        getAntrian()
    }

    fun getAntrian() {
        viewModelScope.launch {
            antrianUiState = AntrianUiState.Loading
            antrianUiState = try {
                AntrianUiState.Success(repositoryAntrian.getAntrian())
            } catch (e: Exception) {
                AntrianUiState.Error
            }
        }
    }

    fun deleteAntrian(id: String) {
        viewModelScope.launch {
            try {
                repositoryAntrian.deleteAntrian(id)
                getAntrian()
            } catch (e: Exception) {
                antrianUiState = AntrianUiState.Error
            }
        }
    }
}