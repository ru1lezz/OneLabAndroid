package kz.onelab.lesson1

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kz.onelab.myapplication.R

class ActivityThird : AppCompatActivity() {

    private lateinit var title: TextView
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)
        initViews()
        initListeners()
    }

    private fun initViews() {
        title = findViewById(R.id.title)
        button = findViewById(R.id.send_back_button)
    }

    private fun initListeners() {
        button.setOnClickListener {
            processData()
            finish()
        }
    }

    private fun processData() {
        val data = SendBackData(
            stringData = title.text.toString(),
            number = 321
        )
        intent.putExtra(EXTRA_SEND_BACK_DATA, data)
        setResult(Activity.RESULT_OK, intent)
    }
}