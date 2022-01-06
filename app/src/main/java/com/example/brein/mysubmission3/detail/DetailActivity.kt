package com.example.brein.mysubmission3.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.brein.mysubmission3.adapter.ViewPagerAdapter
import com.example.brein.mysubmission3.databinding.ActivityDetailBinding
import com.example.brein.mysubmission3.helper.ViewModelFactory
import com.example.brein.mysubmission3.viewmodel.DetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    companion object{
        const val USERNAME = "extra_username"
        const val ID = "extra_id"
        const val AVATAR = "extra_avatar"
    }

    private lateinit var detailBinding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val username = intent.getStringExtra(USERNAME)
        val id = intent.getIntExtra(ID, 0)
        val avatarUrl = intent.getStringExtra(AVATAR)

        val bundle = Bundle()
        bundle.putString(USERNAME, username)
        loading(true)

        viewModel = detailViewModel(this as AppCompatActivity)
        viewModel.setDetail(username!!)
        viewModel.detailUser.observe(this, {
            if( it != null){
                loading(false)
                with(detailBinding){
                    Glide.with(this@DetailActivity).load(it.avatar_url).into(imageUser)
                    nameUser.text = it.name
                    usernameUser.text = it.login
                    followerUser.text = if(it.followers == null) "not found" else {it.followers + "Follower"}
                    followingUser.text = if(it.following == null) "not found" else {it.following + "Following"}
                    companyUser.text = if(it.company == null) "not found" else it.company
                    locationUser.text = if(it.location == null) "not found" else it.location
                    repositoryUser.text = if(it.public_repos == null) "not found" else {it.public_repos + "repository"}
                }
            }
        })

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if(count != null){
                    if(count > 0){
                        detailBinding.toggleFavorite.isChecked = true
                        _isChecked = true
                    }else{
                        detailBinding.toggleFavorite.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        detailBinding.toggleFavorite.setOnClickListener{
            _isChecked = !_isChecked
            if(_isChecked){
                viewModel.addFavorite(username,id,avatarUrl!!)
            }else{
                viewModel.removeFavorite(id)
            }
            detailBinding.toggleFavorite.isChecked = _isChecked
        }

        val viewPagerAdapter = ViewPagerAdapter(this, supportFragmentManager, bundle)
        with(detailBinding){
            viewPager.adapter = viewPagerAdapter
            tab.setupWithViewPager(viewPager)
        }
    }

    private fun detailViewModel(activity: AppCompatActivity) : DetailViewModel{
        val detail = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, detail)[DetailViewModel::class.java]
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun loading(state : Boolean){
        if(state){
            detailBinding.progressDetail.visibility = View.VISIBLE
            detailBinding.imageUser.visibility = View.INVISIBLE
            detailBinding.nameUser.visibility = View.INVISIBLE
            detailBinding.usernameUser.visibility = View.INVISIBLE
        }else{
            detailBinding.progressDetail.visibility = View.GONE
            detailBinding.imageUser.visibility = View.VISIBLE
            detailBinding.nameUser.visibility = View.VISIBLE
            detailBinding.usernameUser.visibility = View.VISIBLE
        }
    }
}



