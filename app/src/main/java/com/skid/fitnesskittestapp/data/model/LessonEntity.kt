package com.skid.fitnesskittestapp.data.model

import java.time.LocalDate

data class LessonEntity(
    val appointment_id: String,
    val coach_id: String,
    val date: LocalDate,
    val startTime: String,
    val endTime: String,
    val name: String,
    val place: String,
    val color: String,
)