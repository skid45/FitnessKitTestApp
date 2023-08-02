package com.skid.fitnesskittestapp

import android.app.Application
import com.skid.fitnesskittestapp.data.remote.ScheduleService
import com.skid.fitnesskittestapp.data.repositories.ScheduleRepositoryImpl
import com.skid.fitnesskittestapp.domain.usecases.GetSortedScheduleUseCase

class FitnessKitApp : Application() {
    private val scheduleService by lazy { ScheduleService() }
    private val scheduleRepository by lazy { ScheduleRepositoryImpl(scheduleService) }
    val getSortedScheduleUseCase by lazy { GetSortedScheduleUseCase(scheduleRepository) }
}