package kz.onelab.lesson2

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import kz.onelab.lesson2.databinding.CustomViewExampleBinding
import kotlin.math.roundToInt

class CustomViewExampleActivity : AppCompatActivity() {

    private lateinit var binding: CustomViewExampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CustomViewExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListeners()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListeners() {
        with(binding) {
            countButton.setOnClickListener {
                binding.counterView.increment()
            }
            resetButton.setOnClickListener {
                binding.counterView.reset()
            }
            counterView.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_MOVE) {
                    //
                }
                false
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }
}