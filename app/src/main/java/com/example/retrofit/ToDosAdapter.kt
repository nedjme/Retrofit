package com.example.retrofit

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.ToDos
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ToDosAdapter(private val activity: MainActivity, private val todos: ToDos, private val itemLayout : Int) :
    RecyclerView.Adapter<ToDosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(p0.context).inflate(itemLayout, p0, false))
    }

    override fun getItemCount(): Int {
        return todos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {

        holder.checkBox.text = todos[p1].title
        holder.taskTime.text = todos[p1].userId.toString()
        holder.checkBox.isChecked = todos[p1].completed

        holder.checkBox.setOnClickListener {
            todos[p1].completed = !todos[p1].completed
            MainActivity().updateToDo(todos[p1])

        }
        holder.deleteBtn.setOnClickListener {
            activity.deleteToDo(todos[p1])
            activity.fetchToDos()
        }

        holder.editBtn.setOnClickListener {

            val dialog = AlertDialog.Builder(activity)
            val view = LayoutInflater.from(activity).inflate(R.layout.edit_view, null)
            dialog.setView(view)

            val editBtn = view.findViewById<EditText>(R.id.editbtn)

            dialog.setPositiveButton("Update") { _: DialogInterface, _: Int ->
                todos[p1].title = editBtn.text.toString()
                activity.updateToDo(todos[p1])
                activity.fetchToDos()
            }
            dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->
            }
            dialog.show()
        }

    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val checkBox : CheckBox = v.findViewById(R.id.cb_task)
        val taskTime: TextView = v.findViewById(R.id.tv_task_time)
        val deleteBtn : FloatingActionButton = v.findViewById(R.id.rv_delete)
        val editBtn : FloatingActionButton = v.findViewById(R.id.rv_edit)


    }
}
