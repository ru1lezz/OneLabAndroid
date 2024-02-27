package kz.onelab.lesson1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kz.onelab.myapplication.R

class MainActivity : AppCompatActivity() {

    private lateinit var titleTextView: TextView
    private lateinit var dataFromThirdActivity: TextView
    private lateinit var openSecondActivityButton: Button
    private lateinit var openThirdActivityButton: Button

    private val customContract = registerForActivityResult(CustomContract()) {
        result -> onActivityResult(result)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUi()
        initListeners()
        Log.i("MainActivity", "onCreate is called")
    }

    override fun onStart() {
        super.onStart()
        Log.i("MainActivity", "onStart is called")
    }

    override fun onResume() {
        super.onResume()
        Log.i("MainActivity", "onResume is called")
    }

    override fun onPause() {
        super.onPause()
        Log.i("MainActivity", "onPause is called")
    }

    override fun onStop() {
        super.onStop()
        Log.i("MainActivity", "onStop is called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("MainActivity", "onDestroy is called")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("MainActivity", "onRestart is called")
    }

    private fun initUi() {
        titleTextView = findViewById(R.id.title_text_view)
        openSecondActivityButton = findViewById(R.id.continue_button)
        openThirdActivityButton = findViewById(R.id.open_third_activity_button)
        dataFromThirdActivity = findViewById(R.id.data_from_second_activity_text_view)
    }

    private fun initListeners() {
        openSecondActivityButton.setOnClickListener {
            startActivity(SecondActivity.newIntent(this, "someData"))
        }
        openThirdActivityButton.setOnClickListener {
            customContract.launch(
                InputData(
                    stringData = "data from first activity",
                    number = 123
                )
            )
        }
    }

    private fun onActivityResult(result: SendBackData?) {
        result?.let {
            dataFromThirdActivity.text = "Processed data: ${result.number} ${result.stringData}"
        }
    }
}