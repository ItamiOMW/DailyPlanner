package com.example.dailyplanner.presentation.main_screen

import android.app.Application
import androidx.lifecycle.*
import androidx.room.InvalidationTracker
import com.example.dailyplanner.data.repository_impl.TaskRepositoryImpl
import com.example.dailyplanner.domain.model.TaskItem
import com.example.dailyplanner.domain.usecases.ChangeTaskItemUseCase
import com.example.dailyplanner.domain.usecases.DeleteTaskItemUseCase
import com.example.dailyplanner.domain.usecases.GetTaskListUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class MainViewModel @Inject constructor(
    private val changeTaskItemUseCase: ChangeTaskItemUseCase,
    private val getTaskListUseCase: GetTaskListUseCase,
    private val deleteTaskItemUseCase: DeleteTaskItemUseCase,
) : ViewModel() {

    private val _selectedDate = MutableLiveData<LocalDate>()
    val selectedDate: LiveData<LocalDate>
        get() = _selectedDate

    private val _list = MutableLiveData<List<TaskItem>>()
    val list: LiveData<List<TaskItem>>
        get() = _list

    init {
        _selectedDate.value = LocalDate.now()
        updateList()
    }

    fun setNewLocalDate(date: LocalDate) {
        _selectedDate.value = date
        updateList()
    }

    fun plusMonth() {
        _selectedDate.value = _selectedDate.value?.plusMonths(1)
        updateList()
    }

    fun minusMonth() {
        _selectedDate.value = _selectedDate.value?.minusMonths(1)
        updateList()
    }

    fun changeTaskItem(taskItem: TaskItem) {
        viewModelScope.launch {
            val newItem = taskItem.copy(isDone = !taskItem.isDone)
            changeTaskItemUseCase.invoke(newItem)
            updateList()
        }
    }

    fun deleteTaskItem(taskItem: TaskItem) {
        viewModelScope.launch {
            deleteTaskItemUseCase.invoke(taskItem)
            updateList()
        }
    }

    fun updateList() {
        viewModelScope.launch {
            val updatedList = getTaskListUseCase.invoke(selectedDate.value.toString())
            _list.value = updatedList
        }
    }


    fun daysInMonthArray(): ArrayList<LocalDate?> {
        val daysInMonthArray: ArrayList<LocalDate?> = ArrayList()
        val yearMonth: YearMonth = YearMonth.from(selectedDate.value)
        val daysInMonth: Int = yearMonth.lengthOfMonth()
        val firstOfMonth: LocalDate? = selectedDate.value?.withDayOfMonth(1)
        val dayOfWeek: Int = firstOfMonth?.dayOfWeek?.value ?: 0
        for (i in 1..42) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add(null)
            } else {
                daysInMonthArray.add(
                    selectedDate.value?.year?.let {
                        LocalDate.of(
                            it,
                            selectedDate.value?.month,
                            i - dayOfWeek
                        )
                    }
                )
            }
        }
        return daysInMonthArray
    }
}
