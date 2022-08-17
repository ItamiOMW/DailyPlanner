package com.example.dailyplanner.presentation.edit_screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyplanner.domain.model.TaskItem
import com.example.dailyplanner.domain.usecases.AddTaskItemUseCase
import com.example.dailyplanner.domain.usecases.ChangeTaskItemUseCase
import com.example.dailyplanner.domain.usecases.GetTaskItemUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditViewModel @Inject constructor (
    private val addTaskItemUseCase: AddTaskItemUseCase,
    private val changeTaskItemUseCase: ChangeTaskItemUseCase,
    private val getTaskItemUseCase: GetTaskItemUseCase,
) : ViewModel() {

    private val _errorTime = MutableLiveData<Any>()
    val errorTime: LiveData<Any>
        get() = _errorTime

    private val _errorText = MutableLiveData<Any>()
    val errorText: LiveData<Any>
        get() = _errorText

    private val _shouldWindowBeClosed = MutableLiveData<Any>()
    val shouldWindowBeClosed: LiveData<Any>
        get() = _shouldWindowBeClosed

    private val _taskItem = MutableLiveData<TaskItem>()
    val taskItem: LiveData<TaskItem>
        get() = _taskItem

    fun getTaskItem(id: Int) {
        viewModelScope.launch {
            _taskItem.value = (getTaskItemUseCase(id))
        }
    }

    fun addTaskItem(
        selectedDate: String,
        name: String?,
        desc: String?,
        timeFrom: String?,
        timeTo: String?
    ) {
        val isTextSuitable = checkText(name, desc)
        val isTimeSuitable = checkTime(timeFrom, timeTo)
        if (isTextSuitable && isTimeSuitable) {
            viewModelScope.launch {
                addTaskItemUseCase.invoke(
                    TaskItem(
                        date = selectedDate,
                        name = name.toString(),
                        timeFrom = timeFrom.toString(),
                        timeTo = timeTo.toString(),
                        description = desc.toString(),
                        isDone = false
                    )
                )
                finishWork()
            }
        }
    }

    fun editTaskItem(
        name: String?,
        desc: String?,
        timeFrom: String?,
        timeTo: String?
    ) {
        val parsedName = parseName(name)
        val parsedDesc = parseDesc(desc)
        val isTextSuitable = checkText(parsedName, parsedDesc)
        val timeFromMain = timeFrom ?: ""
        val timeToMain = timeTo ?: ""
        val isTimeSuitable = checkTime(timeFrom, timeTo)
        if (isTextSuitable && isTimeSuitable) {
            viewModelScope.launch {
                _taskItem.value?.let {
                    val taskItem =
                        it.copy(
                            name = parsedName,
                            timeFrom = timeFromMain,
                            timeTo = timeToMain,
                            description = parsedDesc
                        )
                    changeTaskItemUseCase.invoke(taskItem)
                    finishWork()
                }
            }
        }
    }

    private fun finishWork() {
        _shouldWindowBeClosed.postValue(Any())
    }

    fun resetErrors() {
        _errorTime.value = null
        _errorText.value = null
    }

    private fun parseName(name: String?): String {
        return name?.trim() ?: ""
    }

    private fun parseDesc(desc: String?): String {
        return desc?.trim() ?: ""
    }

    private fun checkTime(timeFrom: String?, timeTo: String?): Boolean {
        if (timeFrom.isNullOrBlank() && timeTo.isNullOrBlank()) {
            _errorTime.value = Any()
            return false
        }
        return true
    }

    private fun checkText(name: String?, desc: String?): Boolean {
        if (name.isNullOrBlank() && desc.isNullOrBlank()) {
            _errorText.value = Any()
            return false
        }
        return true
    }

}