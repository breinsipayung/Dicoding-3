package com.example.brein.mysubmission3.favorite

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brein.mysubmission3.adapter.UserAdapter
import com.example.brein.mysubmission3.databinding.ActivityFavoriteBinding
import com.example.brein.mysubmission3.detail.DetailActivity
import com.example.brein.mysubmission3.helper.ViewModelFactory
import com.example.brein.mysubmission3.local.FavoriteUser
import com.example.brein.mysubmission3.model.User
import com.example.brein.mysubmission3.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var favoriteBinding: ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FavoriteViewModel
    private val listFavorite: ArrayList<User> = arrayListOf()


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(favoriteBinding.root)

        adapter = UserAdapter(listFavorite)
        adapter.notifyDataSetChanged()

        viewModel = favoriteViewModel(this as AppCompatActivity)

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(user: User) {
                Intent(this@FavoriteActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.USERNAME, user.login)
                    it.putExtra(DetailActivity.ID, user.id)
                    it.putExtra(DetailActivity.AVATAR, user.avatar_url)
                    startActivity(it)
                }
            }
        })

        with(favoriteBinding){
            favoriteUser.setHasFixedSize(true)
            favoriteUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            favoriteUser.adapter = adapter
        }

        viewModel.getFavorite()?.observe(this, {
            if(it != null){
                val fav = userMap(it)
                adapter.setUserList(fav)
            }
        })
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun userMap(favorite: List<FavoriteUser>):ArrayList<User>{
        val listFav = ArrayList<User>()
        for(favUser in favorite){
            val userMapped = User(
                favUser.login,
                favUser.id,
                favUser.avatar_url
            )
            listFav.add(userMapped)
        }
        return listFav
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun favoriteViewModel(activity: AppCompatActivity) : FavoriteViewModel{
        val favorite = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, favorite)[FavoriteViewModel::class.java]
    }

}