package com.example.brein.mysubmission3.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brein.mysubmission3.adapter.UserAdapter
import com.example.brein.mysubmission3.databinding.FragmentFollowingBinding
import com.example.brein.mysubmission3.detail.DetailActivity
import com.example.brein.mysubmission3.helper.ViewModelFactory
import com.example.brein.mysubmission3.model.User
import com.example.brein.mysubmission3.viewmodel.FollowingViewModel

class FollowingFragment : Fragment() {
    private val listFollowing: ArrayList<User> = arrayListOf()
    private var _followingBinding: FragmentFollowingBinding? = null
    private val binding get() = _followingBinding!!
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FollowingViewModel
    private lateinit var login : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val args = arguments
        _followingBinding = FragmentFollowingBinding.inflate(inflater, container, false)
        login = args?.getString(DetailActivity.USERNAME).toString()
        loading(true)
        showFollowing(listFollowing)
        viewModel = followingViewModel(context as AppCompatActivity)
        viewModel.setFollowing(login)
        viewModel.getFollowing().observe(viewLifecycleOwner, {
            if(it != null){
                adapter.setUserList(it)
                noUser(it)
                loading(false)
            }
        })

        return binding.root
    }

    private fun followingViewModel(activity: AppCompatActivity) : FollowingViewModel {
        val following = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, following)[FollowingViewModel::class.java]
    }

    private fun showFollowing(list : ArrayList<User>){
        adapter = UserAdapter(list)
        with(binding){
            following.setHasFixedSize(true)
            following.layoutManager = LinearLayoutManager(requireContext())
            following.adapter = adapter
        }
    }

    private fun loading(state : Boolean){
        with(binding){
            if(state){
                progressFollowing.visibility = View.VISIBLE
                following.visibility = View.GONE
            }else{
                progressFollowing.visibility = View.GONE
                following.visibility = View.VISIBLE
            }
        }
    }

    private fun noUser(list : ArrayList<User>){
        with(binding){
            noFollowing.visibility = if(list.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _followingBinding = null
    }
}