package com.skid.fitnesskittestapp.data.repositories

import com.skid.fitnesskittestapp.data.remote.ScheduleService
import com.skid.fitnesskittestapp.domain.model.LessonItem
import com.skid.fitnesskittestapp.domain.repositories.ScheduleRepository
import com.skid.fitnesskittestapp.utils.asLessonItem
import java.io.IOException

class ScheduleRepositoryImpl(private val scheduleService: ScheduleService) : ScheduleRepository {

    override suspend fun getSchedule(): Result<List<LessonItem>> {
        return try {
            val response = scheduleService.getSchedule()
            if (response.isSuccessful) {
                val lessonList = response.body()!!.let { schedule ->
                    schedule.lessons.map { lesson ->
                        val coachName =
                            if (lesson.coach_id.isBlank()) ""
                            else schedule.trainers.first { it.id == lesson.coach_id }.full_name
                        lesson.asLessonItem(coachName)
                    }
                }
                Result.success(lessonList)
            } else {
                Result.failure(Exception("Не могу обновить данные.\nЧто-то пошло не так."))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Не могу обновить данные.\nПроверь соединение с интернетом."))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}