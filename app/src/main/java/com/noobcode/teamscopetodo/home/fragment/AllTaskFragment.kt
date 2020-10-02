package com.noobcode.teamscopetodo.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.noobcode.teamscopetodo.R
import com.noobcode.teamscopetodo.adapters.TaskAdapter
import com.noobcode.teamscopetodo.databinding.FragmentAllTaskBinding
import com.noobcode.teamscopetodo.home.viewmodel.AllTaskViewModel
import com.noobcode.teamscopetodo.interfaces.TaskGestureControl
import com.noobcode.teamscopetodo.model.Task
import com.noobcode.teamscopetodo.model.User
import com.noobcode.teamscopetodo.utils.DateUtil

class AllTaskFragment : Fragment(), TaskGestureControl {

    private lateinit var binding: FragmentAllTaskBinding
    private lateinit var viewModel: AllTaskViewModel

    companion object {
        lateinit var user: User
    }

    private lateinit var adapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_task, container, false)
        viewModel = ViewModelProvider(this).get(AllTaskViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            user = AllTaskFragmentArgs.fromBundle(requireArguments()).user
        }

        adapter = TaskAdapter(ArrayList(), this)

        binding.allTaskView.layoutManager = LinearLayoutManager(context)
        binding.allTaskView.adapter = adapter
        binding.allTaskView.setHasFixedSize(true)

        binding.dateText.text = DateUtil.getFormattedDate(System.currentTimeMillis())

        viewModel.getUserData(user.firebaseUid).observe(viewLifecycleOwner, Observer {
            if (it != null) {
                setUser(it)
            }
        })


        binding.addTask.setOnClickListener {
            var task: Task = Task()
            val actions: NavDirections =
                AllTaskFragmentDirections.actionAllTaskFragmentToAddTaskFragment(task)
            Navigation.findNavController(it).navigate(actions)
        }

    }

    private fun updateTaskStatus() {
        binding.statusTask.text =
            "${adapter.completedTasks} completed, ${adapter.itemCount - adapter.completedTasks} incomplete"
    }

    private fun setUser(user: User) {
        updateTaskStatus()
        adapter.updateList(user.tasks)
    }

    override fun taskSwitch(oldTask: Task, newTask: Task) {
        updateTaskStatus()
        viewModel.changeTaskData(user.firebaseUid, oldTask, newTask)
            .observe(viewLifecycleOwner, Observer {
                if (it != null && it) {
                    /*nothing*/
                }
            })
    }

    override fun longPressed(task: Task) {
        updateTaskStatus()
        viewModel.deleteTask(user.firebaseUid, task).observe(viewLifecycleOwner, Observer {
            if (it != null && it) {
                /*nothing*/
            }
        })
    }

    override fun clicked(task: Task) {
        val actions: NavDirections =
            AllTaskFragmentDirections.actionAllTaskFragmentToAddTaskFragment(task)
        Navigation.findNavController(binding.root).navigate(actions)
    }

    override fun loadedData() {
        updateTaskStatus()
    }

}