package com.example.android.marsphotos

import com.example.android.marsphotos.network.MarsApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MarsApiServiceTests : BaseTest() {

    private lateinit var service: MarsApiService

    @Before
    fun setup() {
        val url = mockWebServer.url("/")
        service = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .build()
                )
            )
            .build()
            .create(MarsApiService::class.java)

    }

    @Test
    fun api_service() {
        // Given
        enqueue("mars_photos.json")
        runBlocking {
            // When
            val apiResponse = service.getPhotos()

            // Then
            assertNotNull(apiResponse)
            assertTrue("The list was empty", apiResponse.body()!!.isNotEmpty())
            assertEquals("The IDs did not match", "424905", apiResponse.body()!![0].id)
        }
    }
}