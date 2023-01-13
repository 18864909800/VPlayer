package com.ashin.vplayer.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ashin.vplayer.R
import kotlinx.android.synthetic.main.activity_kotlin.*

class KotlinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        init("")
        kotlin_textview.text = "1111111"
    }

    private fun init(text: String) {
       // kotlin_textview.setTextColor(11)

    }
}