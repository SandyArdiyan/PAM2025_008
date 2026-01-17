package com.example.tugasakhirpam.uicontroller.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhirpam.uicontroller.route.DestinasiEntry
// PERBAIKAN 1: Import ViewModel yang benar
import com.example.tugasakhirpam.uicontroller.viewmodel.EntryAntrianViewModel
import com.example.tugasakhirpam.uicontroller.viewmodel.InsertUiState
import com.example.tugasakhirpam.uicontroller.viewmodel.DetailAntrian
import com.example.tugasakhirpam.uicontroller.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanEntryAntrian(
    navigateBack: () -> Unit,
    // PERBAIKAN 2: Gunakan 'EntryAntrianViewModel' bukan 'InsertAntrianViewModel'
    viewModel: EntryAntrianViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            HospitalTopAppBar(
                title = DestinasiEntry.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryAntrianBody(
            // PERBAIKAN 3: Gunakan 'uiStateAntrian' (sesuai di EntryAntrianViewModel.kt)
            insertUiState = viewModel.uiStateAntrian,

            // PERBAIKAN 4: Gunakan 'updateUiState' (sesuai di EntryAntrianViewModel.kt)
            onAntrianValueChange = viewModel::updateUiState,

            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveAntrian()
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

@Composable
fun EntryAntrianBody(
    insertUiState: InsertUiState,
    onAntrianValueChange: (DetailAntrian) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.padding(16.dp)
    ) {
        EntryAntrianForm(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onAntrianValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@Composable
fun EntryAntrianForm(
    insertUiEvent: DetailAntrian,
    modifier: Modifier = Modifier,
    onValueChange: (DetailAntrian) -> Unit = {},
    enabled: Boolean = true
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        OutlinedTextField(
            value = insertUiEvent.namaPasien,
            onValueChange = { onValueChange(insertUiEvent.copy(namaPasien = it)) },
            label = { Text("Nama Pasien") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.noRekamMedis,
            onValueChange = { onValueChange(insertUiEvent.copy(noRekamMedis = it)) },
            label = { Text("No Rekam Medis") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.poli,
            onValueChange = { onValueChange(insertUiEvent.copy(poli = it)) },
            label = { Text("Poli Tujuan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.dokter,
            onValueChange = { onValueChange(insertUiEvent.copy(dokter = it)) },
            label = { Text("Dokter") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.alamat,
            onValueChange = { onValueChange(insertUiEvent.copy(alamat = it)) },
            label = { Text("Alamat") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled
        )
        OutlinedTextField(
            value = insertUiEvent.tanggal,
            onValueChange = { onValueChange(insertUiEvent.copy(tanggal = it)) },
            label = { Text("Tanggal (YYYY-MM-DD)") }, // Tambahkan format agar user tahu
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
    }
}