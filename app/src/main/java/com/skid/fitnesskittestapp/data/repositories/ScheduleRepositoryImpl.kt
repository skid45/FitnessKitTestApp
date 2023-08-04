package com.skid.fitnesskittestapp.data.repositories

import android.content.Context
import com.skid.fitnesskittestapp.R
import com.skid.fitnesskittestapp.data.remote.ScheduleService
import com.skid.fitnesskittestapp.domain.model.LessonItem
import com.skid.fitnesskittestapp.domain.repositories.ScheduleRepository
import com.skid.fitnesskittestapp.utils.asLessonItem
import java.io.IOException

class ScheduleRepositoryImpl(
    private val scheduleService: ScheduleService,
    private val context: Context,
) : ScheduleRepository {

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
                Result.failure(Exception(context.getString(R.string.network_request_failure)))
            }
        } catch (e: IOException) {
            Result.failure(Exception(context.getString(R.string.network_connection_error)))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}