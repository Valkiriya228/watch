package com.example.watch

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var secondsElapsed: Int = 0
    var TAG: String = "States"
    var flag: Boolean = false
    lateinit var sPref: SharedPreferences

    private val backgroundThread = Thread {
        while (flag) {
            try {
                Thread.sleep(1000)
                textSecondsElapsed.post {
                    textSecondsElapsed.setText("Seconds elapsed: " + secondsElapsed++)
                }
            } catch (e: InterruptedException) {
                println("Thread has been interrupted")
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sPref = getPreferences(MODE_PRIVATE);
        secondsElapsed = sPref.getInt("secondsElapsed", 0)
        flag = true
        backgroundThread.start()

        Log.d(TAG, "MainActivity: onCreate()")
    }


    override fun onResume() {
        super.onResume()
        flag = true
        Log.d(TAG, "MainActivity: onResume")
        if (!flag) {
            flag = true
            backgroundThread.start()
        }
    }

    override fun onPause() {
        super.onPause()
        flag = false
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

}