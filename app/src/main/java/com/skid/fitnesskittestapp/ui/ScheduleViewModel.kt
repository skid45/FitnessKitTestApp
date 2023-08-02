package com.skid.fitnesskittestapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.skid.fitnesskittestapp.FitnessKitApp
import com.skid.fitnesskittestapp.domain.model.ScheduleListItem
import com.skid.fitnesskittestapp.domain.usecases.GetSortedScheduleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ScheduleViewModel(
    private val getSortedScheduleUseCase: GetSortedScheduleUseCase,
) : ViewModel() {

    private val _schedule = MutableStateFlow(emptyList<ScheduleListItem>())
    val schedule = _schedule.asStateFlow()

    private val _networkError = MutableStateFlow<String?>(null)
    val networkError = _networkError.asStateFlow()

    init {
        refreshSchedule()
    }

    fun refreshSchedule() {
        viewModelScope.launch {
            val result = getSortedScheduleUseCase.invoke()
            if (result.isSuccess) {
                _schedule.value = result.getOrNull() ?: emptyList()
            } else {
                _networkError.value = result.exceptionOrNull()?.localizedMessage
            }
        }
    }

    fun setNetworkErrorToNull() {
        _networkError.value = null
    }
}


@Suppress("UNCHECKED_CAST")
class ScheduleViewModelFactory(private val application: FitnessKitApp) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScheduleViewModel::class.java)) {
            return ScheduleViewModel(application.getSortedScheduleUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}