package com.example.todolist

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.data.TaskDatasource
import com.example.todolist.data.TaskItem
import com.example.todolist.data.TaskRepository
import com.example.todolist.ui.TaskAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val datasource = TaskDatasource()
    private val repository = TaskRepository(datasource)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        setupBottomSheet()
    }


    private fun setupRecyclerView() {
        val adapter = TaskAdapter(repository.getAllTasks())
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = adapter

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val deletedTask: TaskItem = repository.getAllTasks()[position]

                repository.removeTask(deletedTask.id)
                adapter.notifyItemRemoved(position)
            }
        }).attachToRecyclerView(recyclerView)
    }

    private fun setupBottomSheet() {
        val openBottomSheetButton =
            findViewById<FloatingActionButton>(R.id.open_bottom_sheet_button)
        openBottomSheetButton.setOnClickListener {
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.fragment_add_task_sheet, null)

            val closeBottomSheetButton = view.findViewById<Button>(R.id.close_bottom_sheet_button)
            closeBottomSheetButton.setOnClickListener {
                dialog.dismiss()
            }

            dialog.setCancelable(true)
            dialog.setContentView(view)
            dialog.show()
        }
    }

    fun getTaskCount(): Int {
        return repository.getAllTasks().size
    }

    fun getTaskList(): List<TaskItem> {
        return repository.getAllTasks()
    }

}