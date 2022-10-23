package com.h_alqatawi.mansourepoultrycompany.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.h_alqatawi.mansourepoultrycompany.R
import com.h_alqatawi.mansourepoultrycompany.model.Customers
import com.h_alqatawi.mansourepoultrycompany.model.Dates
import com.h_alqatawi.mansourepoultrycompany.databinding.ActivityAddBinding
import com.h_alqatawi.mansourepoultrycompany.db.AppDatabase
import com.h_alqatawi.mansourepoultrycompany.db.DaoDatabase
import com.h_alqatawi.mansourepoultrycompany.repository.RepositoryApp
import com.h_alqatawi.mansourepoultrycompany.viewModel.ViewModelApp


class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
    private lateinit var daoDatabase: DaoDatabase
    private lateinit var viewModelApp: ViewModelApp
    private lateinit var repository: RepositoryApp
    private lateinit var customers: Customers
    lateinit var name: String
    private lateinit var newDate: String
    lateinit var date: String
    lateinit var cup: String
    lateinit var aryw: String
    lateinit var afyn: String
    lateinit var rws: String
    lateinit var hibard: String
    private lateinit var ayar: String
    lateinit var note1: String
    lateinit var note2: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add)
        setContentView(binding.root)

        // this is caret database
        daoDatabase = AppDatabase.getInstance(this).dao
        repository = RepositoryApp(daoDatabase)
        viewModelApp = ViewModelApp(repository)
        // this valuable data take value come in MainActivity
        date = intent.getStringExtra("date").toString()
        binding.addEdDate.setText(date)
        // this is toast show is start Activity
        val toast = Toast.makeText(this, "صلي علي النبي و تبسم", Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()

        viewModelApp.message.observe(this, androidx.lifecycle.Observer {
            it.getContentIfNotHandled()?.let { it ->
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()

            }
        })


        // this button to add data in database
        binding.addButAdd.setOnClickListener {
            insertCustomer()
            /*
            *this is to test get chang in date or not
            * if chang date by user add newDate in database than add data in database
            * if not chang add data in database
            */
            if (date != newDate) {

                viewModelApp.insertNewDate(Dates(newDate),customers)
                /*
                GlobalScope.launch(Dispatchers.IO) {
                    val i = async { daoDatabase.insertDate(Dates(newDate)) }
                    if (i.await() > -1) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@AddActivity,
                                "تم اضافة تاريخ جديد",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    val ic = async { daoDatabase.insertCustomer(customers) }
                    if (ic.await() > -1) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@AddActivity, "تم اضافة العميل", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                }*/

                clear()

            } else {

                viewModelApp.insertCustomer(customers)
                /*
                GlobalScope.launch(Dispatchers.IO) {

                    val i = async { daoDatabase.insertCustomer(customers) }
                    if (i.await() > -1) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@AddActivity, "تم اضافة العميل", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }*/

                clear()
            }

        }

    }

    /*
    * this fun to get value in editText
    * and test value if empty put value=0
    * */
    private fun insertCustomer() {
        name = binding.addEdCustomerName.text.toString()
        if (name.isEmpty()) {
            binding.addEdCustomerName.error = resources.getString(R.string.masg)

        }
        newDate = binding.addEdDate.text.toString().trim()
        if (newDate.contains("/") || newDate.contains("_") || newDate.contains(" ")) {
            binding.addEdDate.error = "من فضلك ادخل التاريخ بطريقة صحيح (1-1-2022)"

        }
        cup = when {
            binding.addEdCup.text.toString().isEmpty() -> {
                "0"
            }
            else -> {
                binding.addEdCup.text.toString()
            }
        }
        aryw = when {
            binding.addEdAryw.text.toString().isEmpty() -> {
                "0"
            }
            else -> {
                binding.addEdAryw.text.toString()
            }
        }
        afyn = when {
            binding.addEdAfyn.text.toString().isEmpty() -> {
                "0"
            }
            else -> {
                binding.addEdAfyn.text.toString()
            }
        }
        rws = when {
            binding.addEdRws.text.toString().isEmpty() -> {
                "0"
            }
            else -> {
                binding.addEdRws.text.toString()
            }
        }
        hibard = when {
            binding.addEdHibard.text.toString().isEmpty() -> {
                "0"
            }
            else -> {
                binding.addEdHibard.text.toString()
            }
        }
        ayar = when {
            binding.addEdAyar.text.toString().isEmpty() -> {
                "0"
            }
            else -> {
                binding.addEdAyar.text.toString()
            }
        }
        note1 = binding.addEdNote1.text.toString()
        note2 = binding.addEdNote2.text.toString()
        customers = Customers(
            0,
            name,
            newDate,
            cup,
            aryw,
            afyn,
            rws,
            hibard,
            ayar,
            note1,
            note2
        )


    }

    // this fun to clear textView
    private fun clear() {
        binding.addEdCustomerName.setText("")
        binding.addEdCup.setText("")
        binding.addEdAryw.setText("")
        binding.addEdAfyn.setText("")
        binding.addEdRws.setText("")
        binding.addEdHibard.setText("")
        binding.addEdAyar.setText("")
        binding.addEdNote1.setText("")
        binding.addEdNote2.setText("")
    }


    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        overridePendingTransition(R.anim.out_lift, R.anim.in_right)
    }
}