package kz.onelab.eighth_lesson.utils

import android.Manifest
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import kz.onelab.eighth_lesson.core.notification.MovieNotification

fun Context.getPendingIntent(activity: Class<*>): PendingIntent {
    return Intent(this, activity).let { notificationIntent ->
        PendingIntent.getActivity(
            this, 1, notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }
}

fun Context.createNotification(
    movieNotification: MovieNotification,
    pendingIntent: PendingIntent?,
) = Notification.Builder(this, movieNotification.channelId)
    .setContentTitle(movieNotification.title)
    .setContentText(movieNotification.text)
    .setSmallIcon(movieNotification.icon)
    .setContentIntent(pendingIntent)
    .build()


fun String.isPermissionGranted(context: Context): Boolean {
    val permissionCheckResult = ContextCompat.checkSelfPermission(context, this)
    return permissionCheckResult == PackageManager.PERMISSION_GRANTED
}

fun isTiramisuOrUp(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU

fun String.checkSelfPermission(
    context: FragmentActivity,
    permissionLauncher: ActivityResultLauncher<String>,
    onPermissionResult: (PermissionResult) -> Unit
) {
    when {
        !isTiramisuOrUp() && this == Manifest.permission.POST_NOTIFICATIONS -> {}
        ContextCompat.checkSelfPermission(context, this) == PackageManager.PERMISSION_GRANTED -> {
            onPermissionResult(PermissionResult.GRANTED)
        }
        ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.POST_NOTIFICATIONS) -> {
            onPermissionResult(PermissionResult.SHOW_PERMISSION_RATIONALE)
        }
        else -> permissionLauncher.launch(this)
    }
}

enum class PermissionResult {
    NOT_GRANTED,
    GRANTED,
    SHOW_PERMISSION_RATIONALE
}