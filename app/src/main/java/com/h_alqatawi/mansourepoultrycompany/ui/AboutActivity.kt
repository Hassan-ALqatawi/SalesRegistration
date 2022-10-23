package com.h_alqatawi.mansourepoultrycompany.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import com.h_alqatawi.mansourepoultrycompany.R

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val toast =Toast.makeText(this,"صلي علي النبي و تبسم",Toast.LENGTH_SHORT)
        toast .setGravity(Gravity.CENTER,0,0)
        toast.show()
    }
}