package com.noobcode.teamscopetodo.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.noobcode.teamscopetodo.R
import com.noobcode.teamscopetodo.databinding.ItemTaskBinding
import com.noobcode.teamscopetodo.interfaces.TaskGestureControl
import com.noobcode.teamscopetodo.model.Task
import com.noobcode.teamscopetodo.utils.glide.GlideClass

class TaskAdapter(
    mTasks: ArrayList<Task>,
    var taskGestureControl: TaskGestureControl
) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    private var tasks: ArrayList<Task> = mTasks
    var completedTasks = 0

    fun updateList(tasks: ArrayList<Task>) {
        this.tasks.clear()
        this.tasks = tasks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding: ItemTaskBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_task,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.taskName.text = tasks[position].name
        if (tasks[position].isCompleted) {

            GlideClass.loadImage(
                holder.binding.taskStatus, R.drawable.checked_ic, GlideClass.getProgressDrawable(
                    holder.binding.taskStatus.context
                )
            )
            holder.binding.taskName.paintFlags =
                holder.binding.taskName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            holder.binding.taskName.setTextColor(
                holder.binding.taskName.context.resources.getColor(
                    R.color.textUnImportant
                )
            )

            completedTasks++

        } else {
            if (tasks[position].priority == 0) {
                GlideClass.loadImage(
                    holder.binding.taskStatus,
                    R.drawable.task_background_low_ic,
                    GlideClass.getProgressDrawable(
                        holder.binding.taskStatus.context
                    )
                )
            } else {
                GlideClass.loadImage(
                    holder.binding.taskStatus,
                    R.drawable.task_background_high_ic,
                    GlideClass.getProgressDrawable(
                        holder.binding.taskStatus.context
                    )
                )
            }

            holder.binding.taskName.paintFlags =
                holder.binding.taskName.paintFlags and (Paint.STRIKE_THRU_TEXT_FLAG.inv())

            holder.binding.taskName.setTextColor(
                holder.binding.taskName.context.resources.getColor(
                    R.color.textImportant
                )
            )

        }

        holder.binding.mainItemLayout.setOnClickListener {
            taskGestureControl.clicked(tasks[position])
        }

        holder.binding.mainItemLayout.setOnLongClickListener {
            var oldTask: Task =
                Task(tasks[position].name, tasks[position].priority, tasks[position].isCompleted)
            deleteTask(tasks[position])
            taskGestureControl.longPressed(oldTask)
            true
        }

        holder.binding.taskStatus.setOnClickListener {
            var oldTask: Task =
                Task(tasks[position].name, tasks[position].priority, tasks[position].isCompleted)
            taskStatusSwitch(holder.binding, tasks[position])
            taskGestureControl.taskSwitch(oldTask, tasks[position])
        }

        taskGestureControl.loadedData()

    }

    private fun deleteTask(task: Task) {
        var position: Int = tasks.indexOf(task)
        if (tasks[position].isCompleted) {
            completedTasks--
        }
        tasks.remove(task)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount);
    }

    private fun taskStatusSwitch(binding: ItemTaskBinding, task: Task): Task {
        if (!task.isCompleted) {
            GlideClass.loadImage(
                binding.taskStatus, R.drawable.checked_ic, GlideClass.getProgressDrawable(
                    binding.taskStatus.context
                )
            )
            binding.taskName.paintFlags =
                binding.taskName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            binding.taskName.setTextColor(binding.taskName.context.resources.getColor(R.color.textUnImportant))

            task.isCompleted = true
            completedTasks++
        } else {
            if (task.priority == 0) {
                GlideClass.loadImage(
                    binding.taskStatus,
                    R.drawable.task_background_low_ic,
                    GlideClass.getProgressDrawable(
                        binding.taskStatus.context
                    )
                )
            } else {
                GlideClass.loadImage(
                    binding.taskStatus,
                    R.drawable.task_background_high_ic,
                    GlideClass.getProgressDrawable(
                        binding.taskStatus.context
                    )
                )
            }
            binding.taskName.paintFlags =
                binding.taskName.paintFlags and (Paint.STRIKE_THRU_TEXT_FLAG.inv())

            binding.taskName.setTextColor(binding.taskName.context.resources.getColor(R.color.textImportant))

            task.isCompleted = false
            completedTasks--
        }

        return task
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    class ViewHolder(var binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
    }
}