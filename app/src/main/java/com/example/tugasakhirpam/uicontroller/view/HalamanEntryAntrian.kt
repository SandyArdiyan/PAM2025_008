package com.example.tugasakhirpam.uicontroller.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhirpam.uicontroller.route.DestinasiEntry
import com.example.tugasakhirpam.uicontroller.viewmodel.DetailAntrian
import com.example.tugasakhirpam.uicontroller.viewmodel.EntryAntrianViewModel
import com.example.tugasakhirpam.uicontroller.viewmodel.InsertUiState
import com.example.tugasakhirpam.uicontroller.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanEntryAntrian(
    navigateBack: () -> Unit,
    viewModel: EntryAntrianViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            HospitalTopAppBar(
                title = DestinasiEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        ) {
            // Tampilkan Pesan Error jika ada
            if (viewModel.isError) {
                Text(
                    text = viewModel.errorMessage,
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }

            EntryBody(
                insertUiState = viewModel.uiStateAntrian,
                onAntrianValueChange = viewModel::updateUiState,
                onSaveClick = {
                    coroutineScope.launch {
                        viewModel.saveAntrian()
                        // Hanya kembali jika sukses
                        if (viewModel.isSuccess) {
                            navigateBack()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun EntryBody(
    insertUiState: InsertUiState,
    onAntrianValueChange: (DetailAntrian) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.padding(16.dp)
    ) {
        FormInput(
            detailAntrian = insertUiState.insertUiEvent,
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
fun FormInput(
    detailAntrian: DetailAntrian,
    onValueChange: (DetailAntrian) -> Unit = {},
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = detailAntrian.namaPasien,
            onValueChange = { onValueChange(detailAntrian.copy(namaPasien = it)) },
            label = { Text("Nama Pasien") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = detailAntrian.noRekamMedis,
            onValueChange = { onValueChange(detailAntrian.copy(noRekamMedis = it)) },
            label = { Text("No Rekam Medis") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = detailAntrian.alamat,
            onValueChange = { onValueChange(detailAntrian.copy(alamat = it)) },
            label = { Text("Alamat") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = false,
            minLines = 3
        )
        OutlinedTextField(
            value = detailAntrian.poli,
            onValueChange = { onValueChange(detailAntrian.copy(poli = it)) },
            label = { Text("Poli Tujuan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = detailAntrian.tanggal,
            onValueChange = { onValueChange(detailAntrian.copy(tanggal = it)) },
            label = { Text("Tanggal (YYYY-MM-DD)") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
    }
}