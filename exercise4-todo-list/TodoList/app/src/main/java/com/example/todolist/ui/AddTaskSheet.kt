package com.example.todolist.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todolist.data.TaskItem
import com.example.todolist.data.TaskRepository
import com.example.todolist.databinding.FragmentAddTaskSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Date

class AddTaskSheet(var taskItem: TaskItem?) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAddTaskSheetBinding
    private lateinit var taskRepository: TaskRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun saveAction() {
        val name = binding.name.text.toString()
        val desc = binding.desc.text.toString()
        val newTask = TaskItem(name, desc, Date(), false)
        taskRepository.addTask(newTask)
    }

}







