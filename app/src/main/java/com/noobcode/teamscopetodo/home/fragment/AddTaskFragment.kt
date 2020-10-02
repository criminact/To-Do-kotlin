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
import com.noobcode.teamscopetodo.R
import com.noobcode.teamscopetodo.databinding.FragmentAddTaskBinding
import com.noobcode.teamscopetodo.home.viewmodel.AddTaskViewModel
import com.noobcode.teamscopetodo.home.fragment.AddTaskFragmentDirections
import com.noobcode.teamscopetodo.model.Task


class AddTaskFragment : Fragment() {

    private lateinit var binding: FragmentAddTaskBinding
    private lateinit var viewModel: AddTaskViewModel
    private lateinit var task: Task

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_task, container, false)
        viewModel = ViewModelProvider(this).get(AddTaskViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(arguments != null){
            task = AddTaskFragmentArgs.fromBundle(requireArguments()).task
            if(!task.name.isEmpty()){
                setTaskForUpdating()
            }
        }

        binding.addTask.setOnClickListener(fun(view: View) {
            if (validateName()) {
                if(task != null){

                    var task: Task = Task()
                    task.name = binding.nameField.text.toString()
                    task.isCompleted = this.task.isCompleted
                    if (binding.highRadio.isChecked) {
                        task.priority = 1
                    } else {
                        task.priority = 0
                    }

                    viewModel.updateTask(AllTaskFragment.user.firebaseUid, this.task, task)
                        .observe(viewLifecycleOwner, Observer {
                            if (it != null && it) {
                                var actions: NavDirections =
                                    AddTaskFragmentDirections.actionAddTaskFragmentToAllTaskFragment(
                                        AllTaskFragment.user
                                    )
                                Navigation.findNavController(view).navigate(actions)
                            }
                        })

                }else{

                    var task: Task = Task()
                    task.name = binding.nameField.text.toString()
                    task.isCompleted = false
                    if (binding.highRadio.isChecked) {
                        task.priority = 1
                    } else {
                        task.priority = 0
                    }

                    viewModel.setTask(AllTaskFragment.user.firebaseUid, task)
                        .observe(viewLifecycleOwner, Observer {
                            if (it != null && it) {
                                var actions: NavDirections =
                                    AddTaskFragmentDirections.actionAddTaskFragmentToAllTaskFragment(
                                        AllTaskFragment.user
                                    )
                                Navigation.findNavController(view).navigate(actions)
                            }
                        })


                }
            }
        })

    }

    private fun setTaskForUpdating() {
        binding.addTask.text = "Update"
        binding.nameField.setText(task.name)
        if(task.priority == 0){
            binding.lowRadio.isChecked = true
        }else{
            binding.highRadio.isChecked = true
        }
    }

    private fun validateName(): Boolean {
        if (binding.nameField.text.toString().isEmpty()) {
            binding.nameField.error = "Name can't be empty";
            return false
        }
        return true;
    }
}