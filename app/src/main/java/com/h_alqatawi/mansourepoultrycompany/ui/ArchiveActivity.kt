package com.h_alqatawi.mansourepoultrycompany.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.KeyEvent
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.h_alqatawi.mansourepoultrycompany.R
import com.h_alqatawi.mansourepoultrycompany.databinding.ActivityArchiveBinding
import com.h_alqatawi.mansourepoultrycompany.db.AppDatabase
import com.h_alqatawi.mansourepoultrycompany.db.DaoDatabase
import com.h_alqatawi.mansourepoultrycompany.model.ArchiveAdapter
import com.h_alqatawi.mansourepoultrycompany.repository.RepositoryApp
import com.h_alqatawi.mansourepoultrycompany.viewModel.ViewModelApp

class ArchiveActivity : AppCompatActivity(){

    private lateinit var binding: ActivityArchiveBinding
    private lateinit var daoDatabase : DaoDatabase
    private lateinit var repository : RepositoryApp
    private lateinit var viewModelApp: ViewModelApp
    private lateinit var adapter: ArchiveAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_archive)

        // this is caret database
        daoDatabase = AppDatabase.getInstance(this.applicationContext).dao
        repository = RepositoryApp(daoDatabase)
        viewModelApp = ViewModelApp(repository)

        viewModelApp.message.observe(this, androidx.lifecycle.Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })

        // this is toast show is start Activity
        val toast =Toast.makeText(this,"صلي علي النبي و تبسم",Toast.LENGTH_SHORT)
        toast .setGravity(Gravity.CENTER,0,0)
        toast.show()
        initRecyclerView()

    }
    // this fun to preppier recycleView and coll fun displayProductList()
    private fun initRecyclerView(){
        binding.archiveRec.layoutManager = LinearLayoutManager(this)
        adapter = ArchiveAdapter(this)

        displayProductList()
    }

    // this fun to show data in recyclerView
    private fun displayProductList(){
        viewModelApp.getDates.observe(this, Observer {
            adapter.addList(it)
            binding.archiveRec.adapter = adapter
        })/*
        daoDatabase.getDates().observe(this, androidx.lifecycle.Observer {
            adapter.addList(it)

        })*/

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (event!!.action == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {

                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                overridePendingTransition(R.anim.in_right, R.anim.out_lift)

            }
        }
        return super.onKeyDown(keyCode, event)
    }


}