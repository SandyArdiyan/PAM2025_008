package com.example.tugasakhirpam.uicontroller.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhirpam.uicontroller.viewmodel.EditAntrianViewModel
import com.example.tugasakhirpam.uicontroller.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanEditAntrian(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditAntrianViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            // Menggunakan CenterAlignedTopAppBar agar tampilan lebih rapi
            androidx.compose.material3.CenterAlignedTopAppBar(
                title = { Text("EDIT / PANGGIL PASIEN", fontWeight = FontWeight.Bold) }
            )
        }
    ) { innerPadding ->
        // PERBAIKAN: Memanggil 'EntryBody' yang sudah didefinisikan di HalamanEntryAntrian.kt
        // Sebelumnya error karena memanggil 'EntryAntrianBody' yang tidak ada.
        EntryAntrianBody(
            // PERBAIKAN: Menggunakan 'antrianUiState' (sesuai nama di EditAntrianViewModel)
            insertUiState = viewModel.antrianUiState,

            // PERBAIKAN: Menggunakan 'updateUiState' (sesuai nama di EditAntrianViewModel)
            onAntrianValueChange = viewModel::updateUiState,

            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateAntrian()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}