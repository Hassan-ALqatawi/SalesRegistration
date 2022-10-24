package com.h_alqatawi.mansourepoultrycompany.model

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.h_alqatawi.mansourepoultrycompany.ui.ArchiveActivity
import com.h_alqatawi.mansourepoultrycompany.ui.MainActivity
import com.h_alqatawi.mansourepoultrycompany.R
import com.h_alqatawi.mansourepoultrycompany.databinding.ItemArchiveBinding
import com.h_alqatawi.mansourepoultrycompany.db.AppDatabase
import com.h_alqatawi.mansourepoultrycompany.db.DaoDatabase
import com.h_alqatawi.mansourepoultrycompany.repository.RepositoryApp
import com.h_alqatawi.mansourepoultrycompany.viewModel.ViewModelApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.ArrayList

class ArchiveAdapter(context: Context) : RecyclerView.Adapter<ArchiveAdapter.ArchiveViewHolder>() {
    private val activity = context
    private var list = ArrayList<Dates>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArchiveViewHolder {
        val layout = LayoutInflater.from(parent.context)
        val binding: ItemArchiveBinding = DataBindingUtil.inflate(
            layout,
            R.layout.item_archive, parent, false
        )
        return ArchiveViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArchiveViewHolder, position: Int) {
        holder.band(list[position], activity)
        holder.binding.cardView.startAnimation(
            AnimationUtils.loadAnimation(
                holder.itemView.context,
                R.anim.cecane_anim
            )
        )

    }

    override fun getItemCount(): Int {
        return list.size
    }


    fun addList(dateList: List<Dates>) {
        list.clear()
        list.addAll(dateList)


    }

    class ArchiveViewHolder(val binding: ItemArchiveBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var daoDatabase: DaoDatabase
        private lateinit var repository: RepositoryApp
        private lateinit var viewModelApp: ViewModelApp
        fun band(dates: Dates, context: Context) {
            daoDatabase = AppDatabase.getInstance(context.applicationContext).dao
            repository = RepositoryApp(daoDatabase)
            viewModelApp = ViewModelApp(repository)
            binding.itemArchiveTvDate.text = dates.date
            binding.itemArchiveTvDate.setOnClickListener {
                val i = Intent(context, MainActivity::class.java)
                i.putExtra("dateArchive", dates.date)
                context.startActivity(i)
            }
            binding.itemArchiveButDate.setOnClickListener {

                GlobalScope.launch(Dispatchers.IO) {
                    val cus = viewModelApp.getCustomerForDelete(dates.date)
                    for (c in cus) {
                        viewModelApp.deleteCustomer(c)
                    }
                    viewModelApp.deleteDate(dates)
                }

               val i = Intent(context, ArchiveActivity::class.java)
                context.startActivity(i)

            }
        }
    }
}

