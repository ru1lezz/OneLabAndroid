package kz.onelab.lesson1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kz.onelab.myapplication.R

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        Log.i("SecondActivity", "onCreate is called")
    }

    override fun onStart() {
        super.onStart()
        Log.i("SecondActivity", "onStart is called")
    }

    override fun onResume() {
        super.onResume()
        Log.i("SecondActivity", "onResume is called")
    }

    override fun onPause() {
        super.onPause()
        Log.i("SecondActivity", "onPause is called")
    }

    override fun onStop() {
        super.onStop()
        Log.i("SecondActivity", "onStop is called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("SecondActivity", "onDestroy is called")
    }

    companion object {

        private const val EXTRA_STRING_DATA = "EXTRA_STRING_DATA"

        fun newIntent(context: Context, stringData: String) = Intent(
            context, SecondActivity::class.java
        ).apply {
            putExtra(EXTRA_STRING_DATA, stringData)
        }
    }
}