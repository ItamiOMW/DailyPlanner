package com.example.dailyplanner.presentation.edit_screen

import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dailyplanner.databinding.FragmentEditTaskBinding
import com.example.dailyplanner.domain.model.TaskItem
import java.util.*

class FragmentEdit : Fragment() {

    private var _binding: FragmentEditTaskBinding? = null
    private val binding: FragmentEditTaskBinding
        get() = _binding ?: throw RuntimeException("FragmentEditTaskBinding is null")

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditTaskBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private lateinit var selectedDate: String
    private lateinit var mode: String
    private var taskItemId: Int = 0

    private val calendarTimeFrom = Calendar.getInstance()
    private val calendarTimeTo = Calendar.getInstance()

    private val viewModel by lazy {
        ViewModelProvider(this)[EditViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        parseArgs()
        super.onViewCreated(view, savedInstanceState)
        launchRightMode()
        observeVM()
        setOnFromTimeSetterClickListener()
        setOnToTimeSetterClickListener()
    }

    private fun setFragmentResult() {
        requireActivity().supportFragmentManager.setFragmentResult(
            TaskItem.ADD_NEW_ELEMENT,
            Bundle().apply { putBoolean(null, true) })
    }

    private fun launchRightMode() {
        when (mode) {
            EXTRA_ADD_MODE -> launchAddMode()
            EXTRA_EDIT_MODE -> launchEditMode()
        }
    }

    private fun observeVM() {
        viewModel.shouldWindowBeClosed.observe(viewLifecycleOwner) {
            if (it != null) {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
        viewModel.errorText.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(
                    requireContext(),
                    "Проверьте, что заполнили описание и наименование",
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.resetErrors()
            }
        }
        viewModel.errorTime.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(
                    requireContext(),
                    "Проверьте, что указали время",
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.resetErrors()
            }
        }
    }

    private fun launchEditMode() {
        viewModel.getTaskItem(taskItemId)
        viewModel.taskItem.observe(viewLifecycleOwner) {
            binding.etDesc.setText(it.description)
            binding.etTaskName.setText(it.name)
            binding.tvTimeFrom.text = it.timeFrom
            binding.tvTimeTo.text = it.timeTo
        }
        binding.buttonSave.setOnClickListener {
            viewModel.editTaskItem(
                binding.etTaskName.text.toString(),
                binding.etDesc.text.toString(),
                binding.tvTimeFrom.text.toString(),
                binding.tvTimeTo.text.toString()
            )
            setFragmentResult()
        }
    }

    private fun launchAddMode() {
        binding.buttonSave.setOnClickListener {
            viewModel.addTaskItem(
                selectedDate,
                binding.etTaskName.text.toString(),
                binding.etDesc.text.toString(),
                binding.tvTimeFrom.text.toString(),
                binding.tvTimeTo.text.toString()
            )
            setFragmentResult()
        }
    }

    private fun setOnFromTimeSetterClickListener() {
        binding.tvTimeFrom.setOnClickListener {
            var hour = 0
            var minute = 0
            val timePickerDialog = TimePickerDialog(
                requireContext(),
                TimePickerDialog.OnTimeSetListener { v, h, m ->
                    hour = h
                    minute = m
                    calendarTimeFrom.set(0, 0, 0, hour, minute)
                    binding.tvTimeFrom.text = DateFormat.format("hh:mm aa", calendarTimeFrom)
                },
                24,
                0,
                true
            )
            timePickerDialog.updateTime(hour, minute)
            timePickerDialog.show()
        }
    }

    private fun setOnToTimeSetterClickListener() {
        binding.tvTimeTo.setOnClickListener {
            var hour = 0
            var minute = 0
            val timePickerDialog = TimePickerDialog(
                requireContext(),
                TimePickerDialog.OnTimeSetListener { v, h, m ->
                    hour = h
                    minute = m
                    calendarTimeTo.set(0, 0, 0, hour, minute)
                    binding.tvTimeTo.text = DateFormat.format("hh:mm aa", calendarTimeTo)
                },
                24,
                0,
                true
            )
            timePickerDialog.updateTime(hour, minute)
            timePickerDialog.show()
        }
    }

    private fun parseArgs() {
        val arguments = requireArguments()
        if (arguments.getString(EXTRA_MODE) == null) {
            throw RuntimeException("EXTRA MODE IS MISSING")
        }
        mode = arguments.getString(EXTRA_MODE, UNKNOWN_MODE)
        if (mode != EXTRA_ADD_MODE && mode != EXTRA_EDIT_MODE) {
            throw RuntimeException("$UNKNOWN_MODE: $mode")
        }
        selectedDate = arguments.getString(EXTRA_LOCAL_DATE).toString()
        if (mode == EXTRA_EDIT_MODE && arguments.getInt(EXTRA_ID) != TaskItem.UNKNOWN_ID) {
            taskItemId = arguments.getInt(EXTRA_ID)
        }
    }

    companion object {

        private const val UNKNOWN_MODE = "unknown mode"
        private const val EXTRA_MODE = "extra mode"
        private const val EXTRA_EDIT_MODE = "edit_mode"
        private const val EXTRA_ADD_MODE = "add_mode"
        private const val EXTRA_ID = "extra_id"
        private const val EXTRA_LOCAL_DATE = "extra_local_date"

        fun getInstanceAddMode(date: String): FragmentEdit {
            return FragmentEdit().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_MODE, EXTRA_ADD_MODE)
                    putString(EXTRA_LOCAL_DATE, date)
                }
            }
        }

        fun getInstanceEditMode(id: Int, date: String): FragmentEdit {
            return FragmentEdit().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_MODE, EXTRA_EDIT_MODE)
                    putInt(EXTRA_ID, id)
                    putString(EXTRA_LOCAL_DATE, date)
                }
            }
        }
    }
}