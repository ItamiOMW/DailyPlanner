package com.example.dailyplanner.presentation.main_screen

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.dailyplanner.R
import com.example.dailyplanner.databinding.FragmentMainBinding
import com.example.dailyplanner.di.DailyMainApp
import com.example.dailyplanner.domain.model.TaskItem
import com.example.dailyplanner.presentation.edit_screen.EditViewModel
import com.example.dailyplanner.presentation.edit_screen.FragmentEdit
import com.example.dailyplanner.presentation.viewmodel_factory.ViewModelFactory
import javax.inject.Inject

class FragmentMain : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding is null")

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: MainViewModel

    private val component by lazy {
        (requireActivity().application as DailyMainApp).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        super.onAttach(context)
    }

    private val calendarAdapter by lazy {
        CalendarAdapter(viewModel)
    }

    private val mainAdapter by lazy {
        MainAdapter()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setUpRvCalendar()
        setUpRvTasks()
        setOnClickListeners()
        setUpTaskListener()
        setUpDateListener()
        setAddButtonListener()
        setOnSwapListener()
        setFragmentResultListener()
    }

    private fun setFragmentResultListener() {
        requireActivity().supportFragmentManager.setFragmentResultListener(
            TaskItem.ADD_NEW_ELEMENT,
            viewLifecycleOwner
        ) { requestKey, result ->
            viewModel.updateList()
        }
    }

    private fun observeViewModel() {
        viewModel.selectedDate.observe(viewLifecycleOwner) {
            binding.tvCurrentYearAndMonth.text = "${it.year}  ${it.month}"
            calendarAdapter.submitList(viewModel.daysInMonthArray())
        }
    }

    private fun setOnClickListeners() {
        binding.buttonNextMonth.setOnClickListener {
            viewModel.plusMonth()
        }
        binding.buttonPreviousMonth.setOnClickListener {
            viewModel.minusMonth()
        }
    }

    private fun setUpRvTasks() {
        binding.rvTasks.adapter = mainAdapter
        viewModel.list.observe(viewLifecycleOwner) {
            mainAdapter.submitList(it)
        }
    }

    private fun setUpRvCalendar() {
        calendarAdapter.submitList(viewModel.daysInMonthArray())
        val layoutManger = GridLayoutManager(requireActivity().application, 7)
        binding.rvCalendar.layoutManager = layoutManger
        binding.rvCalendar.adapter = calendarAdapter
    }

    private fun launchEditFragment(fragmentEdit: FragmentEdit) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragmentEdit)
            .addToBackStack(null)
            .commit()
    }

    private fun setAddButtonListener() {
        binding.buttonAddNewTask.setOnClickListener {
            launchEditFragment(
                FragmentEdit.getInstanceAddMode(
                    viewModel.selectedDate.value.toString()
                )
            )
        }
    }

    private fun setUpTaskListener() {
        mainAdapter.onTaskItemClickListener = {
            launchEditFragment(FragmentEdit.getInstanceEditMode(it.id, it.date))
        }
        mainAdapter.onTaskItemLongClickListener = {
            viewModel.changeTaskItem(it)
        }
    }

    private fun setOnSwapListener() {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.deleteTaskItem(mainAdapter.currentList[viewHolder.adapterPosition])
            }
        }
        ItemTouchHelper(callback).attachToRecyclerView(binding.rvTasks)
    }

    private fun setUpDateListener() {
        calendarAdapter.onNumberItemClickListener = { position, date ->
            viewModel.setNewLocalDate(date)
            setUpRvCalendar()
        }
    }
}