package com.example.tugasakhirpam.repositori

import com.example.tugasakhirpam.apiservice.ServiceApiRumahSakit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val repositoryAntrian: RepositoryAntrian
}

class ContainerApp : AppContainer {
    // Ganti "10.0.2.2" dengan IP Laptop jika pakai HP Asli (misal "192.168.1.5")
    private val baseUrl = "http://10.0.2.16:3000/api/"

    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val serviceApiRumahSakit: ServiceApiRumahSakit by lazy {
        retrofit.create(ServiceApiRumahSakit::class.java)
    }

    override val repositoryAntrian: RepositoryAntrian by lazy {
        NetworkRepositoryAntrian(serviceApiRumahSakit)
    }
}