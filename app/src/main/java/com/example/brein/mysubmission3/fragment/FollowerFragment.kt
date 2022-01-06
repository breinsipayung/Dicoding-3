package com.example.brein.mysubmission3.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brein.mysubmission3.R
import com.example.brein.mysubmission3.adapter.UserAdapter
import com.example.brein.mysubmission3.databinding.FragmentFollowerBinding
import com.example.brein.mysubmission3.databinding.FragmentFollowingBinding
import com.example.brein.mysubmission3.detail.DetailActivity
import com.example.brein.mysubmission3.helper.ViewModelFactory
import com.example.brein.mysubmission3.model.DetailUserResponse
import com.example.brein.mysubmission3.model.User
import com.example.brein.mysubmission3.viewmodel.FollowerViewModel

class FollowerFragment : Fragment() {

    private val listFollower : ArrayList<User> = arrayListOf()
    private var _followerBinding: FragmentFollowerBinding? = null
    private val binding get() = _followerBinding!!
    private lateinit var viewModel: FollowerViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var login: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = arguments
        _followerBinding = FragmentFollowerBinding.inflate(inflater, container, false)
        login = args?.getString(DetailActivity.USERNAME).toString()
        loading(state = true)
        showFollower(listFollower)
        viewModel = followerViewModel(context as AppCompatActivity)
        viewModel.setFollower(login)
        viewModel.getFOllower().observe(viewLifecycleOwner, {
            if(it != null){
                adapter.setUserList(it)
                noUser(it)
                loading(state = false)
            }
        })

        return binding.root
    }

    private fun followerViewModel(activity: AppCompatActivity) : FollowerViewModel{
        val follower = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, follower)[FollowerViewModel::class.java]
    }

    private fun showFollower(list : ArrayList<User>){
        adapter = UserAdapter(list)
        with(binding){
            follower.setHasFixedSize(true)
            follower.layoutManager = LinearLayoutManager(requireContext())
            follower.adapter = adapter
        }
    }

    private fun loading(state : Boolean){
        with(binding){
            if(state){
                progressFollower.visibility = View.VISIBLE
                follower.visibility = View.GONE
            }else{
                progressFollower.visibility = View.GONE
                follower.visibility = View.VISIBLE
            }
        }
    }

    private fun noUser(list : ArrayList<User>){
        with(binding){
            noFollower.visibility = if(list.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _followerBinding = null
    }

}