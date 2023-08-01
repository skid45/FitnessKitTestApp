package com.skid.fitnesskittestapp.data.model

data class ScheduleEntity(
    val lessons: List<LessonEntity>,
    val trainers: List<TrainerEntity>
)