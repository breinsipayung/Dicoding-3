package com.example.brein.mysubmission3.main

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brein.mysubmission3.R
import com.example.brein.mysubmission3.adapter.UserAdapter
import com.example.brein.mysubmission3.databinding.ActivityMainBinding
import com.example.brein.mysubmission3.detail.DetailActivity
import com.example.brein.mysubmission3.favorite.FavoriteActivity
import com.example.brein.mysubmission3.helper.ViewModelFactory
import com.example.brein.mysubmission3.model.User
import com.example.brein.mysubmission3.setting.SettingActivity
import com.example.brein.mysubmission3.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val listMain: ArrayList<User> = arrayListOf()
    private lateinit var viewModel : MainViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        adapter = UserAdapter(listMain)
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(user: User) {
                Intent(this@MainActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.USERNAME, user.login)
                    it.putExtra(DetailActivity.ID, user.id)
                    it.putExtra(DetailActivity.AVATAR, user.avatar_url)
                    startActivity(it)
                }
            }
        })

        viewModel = mainViewModel(this as AppCompatActivity)
        with(mainBinding){
            userMain.layoutManager = LinearLayoutManager(this@MainActivity)
            userMain.setHasFixedSize(true)
            userMain.adapter = adapter
        }

        viewModel.getSearch().observe(this, {
            adapter.setUserList(it)
            loading(state = false)
        })
        darkTheme()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                loading(true)
                viewModel.setSearch(query)
                return true
            }
            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {
                listMain.clear()
                adapter.notifyDataSetChanged()
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite -> {
                Intent(this, FavoriteActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.theme -> {
                Intent(this, SettingActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.translate -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun mainViewModel(activity: AppCompatActivity): MainViewModel{
        val main = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, main)[MainViewModel::class.java]
    }

    private fun darkTheme(){
        viewModel.getTheme().observe(this, {
            if(it){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                clearData(listMain, adapter)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                clearData(listMain, adapter)
            }
        })
    }

    private fun loading(state: Boolean){
        with(mainBinding){
            if(state){
                progressBar.visibility = View.VISIBLE
            }else{
                progressBar.visibility = View.GONE
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun clearData(list: ArrayList<User>, adapter: UserAdapter){
        list.clear()
        adapter.notifyDataSetChanged()
    }
}