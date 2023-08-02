package com.skid.fitnesskittestapp.domain.model

import java.time.LocalDate

data class LessonItem(
    val id: String,
    val name: String,
    val coachName: String,
    val date: LocalDate,
    val startTime: String,
    val endTime: String,
    val duration: String,
    val place: String,
    val color: Int,
) : ScheduleListItem()
