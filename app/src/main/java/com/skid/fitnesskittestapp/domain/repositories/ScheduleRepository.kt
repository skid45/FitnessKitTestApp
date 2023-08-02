package com.skid.fitnesskittestapp.domain.repositories

import com.skid.fitnesskittestapp.domain.model.LessonItem

interface ScheduleRepository {

    suspend fun getSchedule(): Result<List<LessonItem>>
}