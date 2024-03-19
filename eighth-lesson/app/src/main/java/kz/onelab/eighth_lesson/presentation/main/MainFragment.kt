package kz.onelab.eighth_lesson.presentation.main

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kz.onelab.eighth_lesson.CustomBroadcastReceiver
import kz.onelab.eighth_lesson.presentation.adapter.PopularMoviesAdapter
import kz.onelab.eighth_lesson.core.functional.Resource
import kz.onelab.eighth_lesson.core.notification.MovieNotificationManager
import kz.onelab.eighth_lesson.databinding.FragmentMainBinding
import kz.onelab.eighth_lesson.presentation.model.Movie
import kz.onelab.eighth_lesson.presentation.viewmodel.PopularMovieViewModel
import kz.onelab.eighth_lesson.utils.PermissionResult
import kz.onelab.eighth_lesson.utils.checkSelfPermission
import kz.onelab.eighth_lesson.utils.isPermissionGranted
import kz.onelab.eighth_lesson.utils.isTiramisuOrUp
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val broadcastReceiver: CustomBroadcastReceiver = CustomBroadcastReceiver()

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission())
        { granted ->
            if (granted) {

            }
        }

    private val settingsLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        // Версии Android ниже Tiramisu не требуют runtime разрешения для уведомлений
        if (isTiramisuOrUp().not()) {
            when {
                Manifest.permission.POST_NOTIFICATIONS.isPermissionGranted(requireContext()) -> {
                    // Разрешение предоставлено, продолжаем работу, связанную с уведомлениями
                }
                else -> {
                    // Разрешение не предоставлено, информируем пользователя
                }
            }
        }
    }

    @Inject
    lateinit var movieNotificationManager: MovieNotificationManager

    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = _binding!!

    private val popularMoviesAdapter by lazy(LazyThreadSafetyMode.NONE) {
        PopularMoviesAdapter()
    }

    private val viewModel: PopularMovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPostNotificationPermission()
        initViews()
        initObservers()
        viewModel.getPopularMovies()


//        movieNotificationManager.showNotification(
//            MovieNotification(
//                title = "Кэш обновился",
//                text = "Теперь будет в обновленном виде",
//                channelId = "cache_update",
//                channelName = R.string.movie_cache_update,
//                icon = R.mipmap.ic_launcher,
//                channelDescription = R.string.cache_update_time_descr
//            )
//        )
    }

    override fun onStart() {
        super.onStart()
        ContextCompat.registerReceiver(
            requireContext(),
            broadcastReceiver,
            IntentFilter("TEST_ACTION"),
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
        requireContext().sendBroadcast(Intent("TEST_ACTION"))
    }

    private fun checkPostNotificationPermission() {
        (context as? FragmentActivity)?.let {
            Manifest.permission.POST_NOTIFICATIONS.checkSelfPermission(
                it,
                permissionLauncher
            ) { permissionResult ->
                when (permissionResult) {
                    PermissionResult.GRANTED -> {

                    }
                    PermissionResult.SHOW_PERMISSION_RATIONALE -> {
                        showRationaleDialog()
                    }

                    PermissionResult.NOT_GRANTED -> {
                        Unit
                    }
                }
            }
        }
    }

    private fun initViews() {
        with(binding) {
            popularMovieRecyclerView.adapter = popularMoviesAdapter
            popularMovieRecyclerView.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initObservers() {
        viewModel.popularMoviesLiveData.observe(viewLifecycleOwner, ::onGetPopularMovies)
    }

    private fun onGetPopularMovies(popularMoviesResource: Resource<List<Movie>>) {
        val isLoading = popularMoviesResource is Resource.Loading
        binding.popularMovieRecyclerView.isVisible = isLoading.not()
        binding.progress.isVisible = isLoading
        when (popularMoviesResource) {
            is Resource.Success -> {
                popularMoviesAdapter.submitList(popularMoviesResource.data)
            }
            is Resource.Error -> {
                Log.e(
                    "MainFragmentTag",
                    "Failed to load popular movies",
                    popularMoviesResource.exception
                )
            }
            else -> Unit
        }
    }

    private fun showRationaleDialog() {
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setMessage("Чтобы выполнить это действие нужно предоставить разрешение в настройках")
            .setTitle("Внимание")
            .setPositiveButton("Настройки") { dialog, _ ->
                dialog.dismiss()
                redirectToSettings()
            }.setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
            }
        val alert = dialog.create()
        alert.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        requireContext().unregisterReceiver(broadcastReceiver)
    }

    private fun redirectToSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", requireContext().packageName, null)
        intent.data = uri
        settingsLauncher.launch(intent)
    }
}