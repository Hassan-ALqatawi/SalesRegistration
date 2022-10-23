package com.h_alqatawi.mansourepoultrycompany.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.KeyEvent
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.h_alqatawi.mansourepoultrycompany.R
import com.h_alqatawi.mansourepoultrycompany.databinding.ActivityMainBinding
import com.h_alqatawi.mansourepoultrycompany.db.AppDatabase
import com.h_alqatawi.mansourepoultrycompany.db.DaoDatabase
import com.h_alqatawi.mansourepoultrycompany.model.*
import com.h_alqatawi.mansourepoultrycompany.repository.RepositoryApp
import com.h_alqatawi.mansourepoultrycompany.viewModel.ViewModelApp
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModelApp: ViewModelApp
    private lateinit var daoDatabase: DaoDatabase
    private lateinit var repository: RepositoryApp
    private lateinit var adapter: CustomerAdapter
    private var customerList = ArrayList<Customers>()
    private var date: String = ""
    var cup: Int = 0
    var aryw: Int = 0
    var afyn: Int = 0
    var rws: Int = 0
    var hibard: Int = 0
    private var ayar: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // this is caret database
        daoDatabase = AppDatabase.getInstance(this.applicationContext).dao
        repository = RepositoryApp(daoDatabase)
        viewModelApp = ViewModelApp(repository)
        // this dateArchive is Valuable  take value  whine send value from ArchiveActivity
        val dateArchive = intent.getStringExtra("dateArchive")
        // this is toast show is start in app
        val toast = Toast.makeText(this, "صلي علي النبي و تبسم", Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
        viewModelApp.message.observe(this, androidx.lifecycle.Observer {
            it.getContentIfNotHandled()?.let { s ->
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
            }
        })
        // this test dataArchive if isNull or empty coll method getDate()
        if (dateArchive.isNullOrEmpty()) {
            getData()
        } else {
            date = dateArchive
            initRecyclerView(dateArchive)
        }

        //  getData()
        // this button for add dateDay in database
        binding.mainButDay.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val month = calendar.get(Calendar.MONTH) + 1
            val year = calendar.get(Calendar.YEAR)
            date = "$year-$month-$day".trim()
            binding.mainTvDate.text = date

            viewModelApp.insertDate(Dates(date))
        }
        //This is button addCustomer to go addActivity
        binding.mainImgButAddCustomer.setOnClickListener {
            if (date.isEmpty()) {
                val t =
                    Toast.makeText(this, "انقر علي زار اليوم لاضافة التاريخ", Toast.LENGTH_SHORT)
                t.setGravity(Gravity.CENTER, 0, 0)
                t.show()
            } else {

                val intent = Intent(this, AddActivity::class.java)
                intent.putExtra("date", date)
                startActivity(intent)
                overridePendingTransition(R.anim.in_right, R.anim.out_lift)
            }
        }
        //This is button to go ArchiveActivity
        binding.mainImgButArchive.setOnClickListener {
            val intent = Intent(this, ArchiveActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.in_right, R.anim.out_lift)
        }
        //This is button share img
        binding.mainImgButShare.setOnClickListener {
            takeScreenshot()
        }
        //This is button  to go AboutActivity
        binding.mainImgButAbout.setOnClickListener {

            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.in_top, R.anim.out_buton)

        }
    }

    // This fun to takeScreenshot to data and share
    @SuppressLint("SimpleDateFormat")
    private fun takeScreenshot() {
        val calendar: Calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("EEEE  dd-MMM-yyyy hh:mm:ss")
        val dateTime = simpleDateFormat.format(calendar.time)
        val path = getExternalFilesDir(null)!!.absolutePath + "/" + dateTime + ".jpg"
        val bitmap = Bitmap.createBitmap(
            binding.mainScV.getChildAt(0).width,
            binding.mainScV.getChildAt(0).height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        binding.mainScV.draw(canvas)
        val imageFile = File(path)
        val outputString = FileOutputStream(imageFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputString)
        outputString.flush()
        outputString.close()
        val uri = FileProvider.getUriForFile(
            applicationContext,
            "com.h_alqatawi.mansourepoultrycompany.android.fileProvider",
            imageFile
        )

        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, resources.getString(R.string.app_name) + "/n" + dateTime)
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.type = "text/plain"
        startActivity(intent)

    }

    // this fun to get data from database with date and show data
    private fun getData() {
        val calendar: Calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)
        val dat = "$year-$month-$day".trim()
        viewModelApp.getDates.observe(this, androidx.lifecycle.Observer {
            for (d in it) {
                date = d.date
            }
            if (date == dat) {

                initRecyclerView(date)
            } else {

                initRecyclerView(dat)
            }
        })
    }

    // this fun to preppier recycleView and coll fun displayProductList()
    private fun initRecyclerView(date: String) {
        binding.mainTvDate.text = date
        adapter = CustomerAdapter(this)
        binding.mainRec.layoutManager = LinearLayoutManager(this)
        displayProductList(date)
    }

    // this fun to show data in recyclerView and coll fun sam , fillData
    private fun displayProductList(da: String) {
        cup = 0
        aryw = 0
        afyn = 0
        rws = 0
        hibard = 0
        ayar = 0
        customerList.clear()
        viewModelApp.getCustomer(da).observe(this, androidx.lifecycle.Observer {
            for (c in it) {
                customerList.add(c)
                sam(c)

            }
            this.adapter.setList(list = customerList)
            binding.mainRec.adapter = adapter
            fillData()
        })
    }

    // this fun take object from Customers to sam anything to gather than but in valuable
    private fun sam(customer: Customers) {
        cup += customer.cup!!.toInt()
        aryw += customer.aryw!!.toInt()
        afyn += customer.afyn!!.toInt()
        rws += customer.rws!!.toInt()
        hibard += customer.hibard!!.toInt()
        ayar += customer.ay_ar!!.toInt()
    }

    // this fun to clear textView
    private fun clear() {
        binding.mainTvCup.text = ""
        binding.mainTvAryw.text = ""
        binding.mainTvAfyn.text = ""
        binding.mainTvRws.text = ""
        binding.mainTvHibard.text = ""
        binding.mainTvAyar.text = ""
    }

    // this fun to to put data in textView
    private fun fillData() {
        clear()
        binding.mainTvCup.text = cup.toString()
        binding.mainTvAryw.text = aryw.toString()
        binding.mainTvAfyn.text = afyn.toString()
        binding.mainTvRws.text = rws.toString()
        binding.mainTvHibard.text = hibard.toString()
        binding.mainTvAyar.text = ayar.toString()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (event!!.action == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                finishAffinity();
            }
        }
        return super.onKeyDown(keyCode, event)
    }

}
