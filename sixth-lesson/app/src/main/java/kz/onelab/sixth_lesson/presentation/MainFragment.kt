package kz.onelab.sixth_lesson.presentation

import android.content.ComponentName
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.onelab.sixth_lesson.databinding.FragmentMainBinding
import kz.onelab.sixth_lesson.services.MyBackgroundService
import kz.onelab.sixth_lesson.services.MyBoundService
import kz.onelab.sixth_lesson.services.MyForegroundService
import kz.onelab.sixth_lesson.worker.MyWorker

class MainFragment : Fragment() {

    private var _binding : FragmentMainBinding? = null
    private val binding
        get() = _binding!!

    private val mainScope = CoroutineScope(Dispatchers.Main)

    private val connection: ServiceConnection = object: ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            (service as MyBoundService.UploadBinder).subscribeToProgress { progress ->
                mainScope.launch {
                    binding.progress.text = "$progress%"
                }
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) { }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            startBackground.setOnClickListener {
                Intent(context, MyBackgroundService::class.java).also { intent ->
                    context?.startService(intent)
                }
            }
            startForeground.setOnClickListener {
                Intent(context, MyForegroundService::class.java).also { intent ->
                    context?.startForegroundService(intent)
                }
            }
            startBound.setOnClickListener {
                Intent(context, MyBoundService::class.java).also { intent ->
                    context?.startService(intent)
                }
            }
            startWork.setOnClickListener {
                val uploadRequest = OneTimeWorkRequest.from(MyWorker::class.java)
                WorkManager.getInstance(requireContext())
                    .enqueue(uploadRequest)

//                val constraints = Constraints
//                    .Builder()
//                    .build()
//                val anotherRequest = OneTimeWorkRequestBuilder<MyWorker>()
//                    .setConstraints(constraints)
//                    .build()
//                val periodicRequest = PeriodicWorkRequestBuilder<MyWorker>(10, TimeUnit.MINUTES)
//
//                WorkManager.getInstance(requireContext())
//                    .enqueueUniqueWork(
//                        "someUniqueWorkName",
//                        ExistingWorkPolicy.REPLACE,
//                        anotherRequest
//                    )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(context, MyBoundService::class.java).also { intent ->
            context?.bindService(intent, connection, BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        context?.unbindService(connection)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}