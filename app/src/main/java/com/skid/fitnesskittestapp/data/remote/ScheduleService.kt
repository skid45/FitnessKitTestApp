package com.skid.fitnesskittestapp.data.remote

import com.google.gson.GsonBuilder
import com.skid.fitnesskittestapp.data.model.ScheduleEntity
import com.skid.fitnesskittestapp.utils.LocalDateDeserializer
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.time.LocalDate

interface ScheduleService {

    @GET("schedule/get_v3/?club_id=2")
    suspend fun getSchedule(): Response<ScheduleEntity>
}

const val BASE_URL = "https://olimpia.fitnesskit-admin.ru/"

fun ScheduleService(): ScheduleService {
    val gson = GsonBuilder()
        .registerTypeAdapter(LocalDate::class.java, LocalDateDeserializer())
        .create()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    return retrofit.create(ScheduleService::class.java)
}