package kz.onelab.lesson1

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import androidx.activity.result.contract.ActivityResultContract
import kotlinx.parcelize.Parcelize

const val EXTRA_INPUT_DATA = "EXTRA_INPUT_DATA"
const val EXTRA_SEND_BACK_DATA = "EXTRA_SEND_BACK_DATA"

class CustomContract : ActivityResultContract<InputData, SendBackData?>() {
    override fun createIntent(context: Context, input: InputData): Intent {
        return Intent(context, ActivityThird::class.java).apply {
            putExtra(EXTRA_INPUT_DATA, input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): SendBackData? {
        if (resultCode != Activity.RESULT_OK && intent == null) {
            return null
        }
        return intent?.getParcelableExtra(EXTRA_SEND_BACK_DATA)
    }
}

@Parcelize
data class InputData(
    val stringData: String,
    val number: Int
) : Parcelable

@Parcelize
data class SendBackData(
    val stringData: String,
    val number: Int
) : Parcelable