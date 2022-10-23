package com.h_alqatawi.mansourepoultrycompany.model

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.h_alqatawi.mansourepoultrycompany.ui.MainActivity
import com.h_alqatawi.mansourepoultrycompany.R
import com.h_alqatawi.mansourepoultrycompany.databinding.ItemCustomerBinding
import com.h_alqatawi.mansourepoultrycompany.db.AppDatabase
import com.h_alqatawi.mansourepoultrycompany.db.DaoDatabase
import com.h_alqatawi.mansourepoultrycompany.repository.RepositoryApp
import com.h_alqatawi.mansourepoultrycompany.viewModel.ViewModelApp

class CustomerAdapter(activity: Context) :
    RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>() {
    private val cont = activity
     private var customerList = ArrayList<Customers>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val layout = LayoutInflater.from(parent.context)
        val binding: ItemCustomerBinding = DataBindingUtil.inflate(
            layout, R.layout.item_customer,
            parent, false
        )
        return CustomerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val index = customerList.indexOf(customerList[position])+1
        holder.band(customerList[position], index, cont)
        holder.binding.card.startAnimation(
            AnimationUtils.loadAnimation(
                holder.itemView.context,
                R.anim.anim_four
            )
        )

    }

    override fun getItemCount(): Int {
        return customerList.size
    }

    fun setList(list: ArrayList<Customers>) {

       this.customerList.addAll(list)
    }

    class CustomerViewHolder(val binding: ItemCustomerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private  lateinit var daoDatabase: DaoDatabase
        private lateinit var repository: RepositoryApp
        private lateinit var viewModelApp: ViewModelApp
        lateinit var name: String
        lateinit var cup: String
        lateinit var aryw: String
        lateinit var afyn: String
        lateinit var rws: String
        lateinit var hibard: String
        lateinit var ayar: String
        lateinit var note1: String
        lateinit var note2: String
        fun band(customers: Customers, index: Int,context: Context) {
         daoDatabase = AppDatabase.getInstance(context.applicationContext).dao
            repository = RepositoryApp(daoDatabase)
            viewModelApp = ViewModelApp(repository)
            binding.itemTvNumber.text=index.toString()
            binding.itemCustomerName.setText(customers.customerName)
            binding.itemEdCup.setText(customers.cup)
            binding.itemEdAryw.setText(customers.aryw)
            binding.itemEdAfyn.setText(customers.afyn)
            binding.itemEdRws.setText(customers.rws)
            binding.itemEdHibard.setText(customers.hibard)
            binding.itemEdAyar.setText(customers.ay_ar)
            binding.itemEdNote1.setText(customers.noteworthy)
            binding.itemEdNote2.setText(customers.noteworthy2)

            binding.itemButUpdate.setOnClickListener {
                getData(customers.date.toString(), customers.customerId)

                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)

            }

            binding.itemButDelete.setOnClickListener {

                viewModelApp.deleteCustomer(customers)


                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            }

        }

        private fun getData(date: String, id: Int) {
            name = binding.itemCustomerName.text.toString()
            if (name.isEmpty()) {
                binding.itemCustomerName.error = "ادخل اسم العميل"
            }

            cup = when {
                binding.itemEdCup.text.toString().isEmpty() -> {
                    "0"
                }
                else -> {
                    binding.itemEdCup.text.toString()
                }
            }
            aryw = when {
                binding.itemEdAryw.text.toString().isEmpty() -> {
                    "0"
                }
                else -> {
                    binding.itemEdAryw.text.toString()
                }
            }
            afyn = when {
                binding.itemEdAfyn.text.toString().isEmpty() -> {
                    "0"
                }
                else -> {
                    binding.itemEdAfyn.text.toString()
                }
            }
            rws = when {
                binding.itemEdRws.text.toString().isEmpty() -> {
                    "0"
                }
                else -> {
                    binding.itemEdRws.text.toString()
                }
            }
            hibard = when {
                binding.itemEdHibard.text.toString().isEmpty() -> {
                    "0"
                }
                else -> {
                    binding.itemEdHibard.text.toString()
                }
            }
            ayar = when {
                binding.itemEdAyar.text.toString().isEmpty() -> {
                    "0"
                }
                else -> {
                    binding.itemEdAyar.text.toString()
                }
            }
            note1 = binding.itemEdNote1.text.toString()
            note2 = binding.itemEdNote2.text.toString()

            viewModelApp.updateCustomer(
                Customers(
                    id,
                    name, date, cup, aryw, afyn, rws, hibard, ayar, note1, note2
                )
            )
        }
    }

}
