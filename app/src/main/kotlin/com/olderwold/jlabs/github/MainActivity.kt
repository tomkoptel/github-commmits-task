package com.olderwold.jlabs.github

import android.view.View
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun setContentView(view: View?) {
        super.setContentView(view)
        setContent { App() }
    }
}
