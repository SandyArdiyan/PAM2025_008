package com.example.tugasakhirpam.uicontroller.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpam.modeldata.Antrian
import com.example.tugasakhirpam.repositori.RepositoryAntrian
import kotlinx.coroutines.launch

class EntryAntrianViewModel(private val repositoryAntrian: RepositoryAntrian) : ViewModel() {

    var uiStateAntrian by mutableStateOf(InsertUiState())
        private set

    // State untuk menangani status sukses/gagal
    var isSuccess by mutableStateOf(false)
    var isError by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    fun updateUiState(detailAntrian: DetailAntrian) {
        uiStateAntrian = InsertUiState(insertUiEvent = detailAntrian)
    }

    // Fungsi simpan dengan penanganan error (Try-Catch)
    suspend fun saveAntrian() {
        if (validateInput()) {
            try {
                // Reset status sebelum mencoba menyimpan
                isError = false
                errorMessage = ""

                // Coba simpan ke server
                repositoryAntrian.insertAntrian(uiStateAntrian.insertUiEvent.toAntrian())

                // Jika tidak ada error di atas, berarti sukses
                isSuccess = true
            } catch (e: Exception) {
                // Tangkap error jika server mati/koneksi gagal
                isError = true
                errorMessage = "Gagal menyimpan: ${e.message}"
                isSuccess = false
            }
        } else {
            isError = true
            errorMessage = "Data tidak boleh kosong!"
        }
    }

    fun validateInput(uiEvent: DetailAntrian = uiStateAntrian.insertUiEvent): Boolean {
        return with(uiEvent) {
            namaPasien.isNotBlank() && noRekamMedis.isNotBlank() && poli.isNotBlank()
        }
    }
}

data class InsertUiState(
    val insertUiEvent: DetailAntrian = DetailAntrian()
)

data class DetailAntrian(
    val id: String = "",
    val namaPasien: String = "",
    val noRekamMedis: String = "",
    val alamat: String = "",
    val poli: String = "",
    val tanggal: String = ""
)

fun DetailAntrian.toAntrian(): Antrian = Antrian(
    id = id,
    namaPasien = namaPasien,
    noRekamMedis = noRekamMedis,
    alamat = alamat,
    poli = poli,
    tanggal = tanggal
)

fun Antrian.toDetailAntrian(): DetailAntrian = DetailAntrian(
    id = id,
    namaPasien = namaPasien,
    noRekamMedis = noRekamMedis,
    alamat = alamat,
    poli = poli,
    tanggal = tanggal
)

fun Antrian.toUiStateAntrian(): InsertUiState = InsertUiState(
    insertUiEvent = this.toDetailAntrian()
)