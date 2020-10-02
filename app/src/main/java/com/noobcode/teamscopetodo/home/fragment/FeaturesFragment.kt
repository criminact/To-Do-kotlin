package com.noobcode.teamscopetodo.home.fragment

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.noobcode.teamscopetodo.R
import com.noobcode.teamscopetodo.adapters.ImageAdapter
import com.noobcode.teamscopetodo.databinding.FragmentFeaturesBinding
import com.noobcode.teamscopetodo.home.viewmodel.FeaturesViewModel
import com.noobcode.teamscopetodo.model.User

class FeaturesFragment : Fragment() {

    private val imageArray: Array<Int> = arrayOf(R.mipmap.create_tasks, R.mipmap.see_progress)
    private lateinit var binding: FragmentFeaturesBinding
    private lateinit var viewModel: FeaturesViewModel
    private lateinit var adapter: ImageAdapter
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_features, container, false)
        viewModel = ViewModelProvider(this).get(FeaturesViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()

        adapter = ImageAdapter(imageArray, context)
        binding.viewPagerFeatures.offscreenPageLimit = 1
        binding.viewPagerFeatures.adapter = adapter

        binding.viewPagerFeatures.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                /*empty*/
            }

            override fun onPageSelected(position: Int) {
                binding.pageIndicatorView.selection = position
            }

            override fun onPageScrollStateChanged(state: Int) {
                /*empty*/
            }
        })

        binding.exploreButton.setOnClickListener {
            hideLayout()
            createUser()
        }
    }

    private fun hideLayout() {
        binding.mainLayoutFeatures.visibility = View.GONE
        binding.progressFeatures.visibility = View.VISIBLE
    }

    private fun showLayout() {
        binding.mainLayoutFeatures.visibility = View.VISIBLE
        binding.progressFeatures.visibility = View.GONE
    }

    override fun onStart() {
        super.onStart()
        var user: FirebaseUser? = mAuth.currentUser
        if (user != null) {
            var tempUser = User()
            tempUser.firebaseUid = user.uid
            updateUI(tempUser)
        }
    }

    private fun updateUI(user: User) {
        val actions: NavDirections =
            FeaturesFragmentDirections.actionFeaturesFragmentToAllTaskFragment(user)
        Navigation.findNavController(binding.root).navigate(actions)
    }

    private fun createUser() {
        activity?.let {
            mAuth.signInAnonymously()
                .addOnCompleteListener(it) { task ->
                    if (task.isSuccessful) {
                        val user = mAuth.currentUser
                        var tempUser: User = User(ArrayList(), user!!.uid)
                        viewModel.setUserData(tempUser).observe(viewLifecycleOwner, Observer {
                            if (it != null && it) {
                                updateUI(tempUser)
                            }
                        })
                    } else {
                        showLayout()
                        var toast: Toast = Toast.makeText(
                            context,
                            "The application couldn't go through, Check your Internet Connection",
                            Toast.LENGTH_LONG
                        )
                        with(toast) {
                            setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM, 0, 0)
                            show()
                        }
                    }
                }
        }
    }
}