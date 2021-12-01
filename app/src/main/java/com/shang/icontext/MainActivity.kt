package com.shang.icontext

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private var count = 0
    private val mHandler = object:Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            mIconText.setText((count++).toString())
            sendEmptyMessageDelayed(0,50)
        }
    }

    private lateinit var mIconText: IconText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mIconText=findViewById(R.id.iconText)
        findViewById<Button>(R.id.button).apply {
            this.setOnClickListener {
                mHandler.sendEmptyMessageDelayed(0,50)
            }
        }

    }

}