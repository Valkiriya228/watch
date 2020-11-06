package com.example.watch

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var secondsElapsed: Int = 0
    var TAG: String = "States"
    var flag1: Boolean = true
    var flag2: Boolean = true
    lateinit var sPref: SharedPreferences


    val backgroundThread = Thread {
        while (flag1) {
            if (flag2) {
                try {
                    Thread.sleep(1000)
                    textSecondsElapsed.post {
                        textSecondsElapsed.setText("Seconds elapsed: " + secondsElapsed++)
                    }
                } catch (e: Exception) {
                    println("exception")
                }
            }

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sPref = getPreferences(MODE_PRIVATE);
        secondsElapsed = sPref.getInt("secondsElapsed", 0)
        backgroundThread.start()

        Log.d(TAG, "MainActivity: onCreate()")
    }


    override fun onResume() {
        super.onResume()
        flag1 = true
        flag2 = true
        Log.d(TAG, "MainActivity: onResume")

    }

    override fun onPause() {
        super.onPause()
        flag2 = false
        Log.d(TAG, "MainActivity: onPause")
    }

    override fun onStop() {
        super.onStop()
        sPref = getPreferences(MODE_PRIVATE)
        val editor = sPref.edit()
        editor.putInt("secondsElapsed", secondsElapsed)
        editor.apply()

        Log.d(TAG, "MainActivity: onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        flag1 = false
        Log.d(TAG, "MainActivity: onPause")
    }

}