package kz.onelab.eighth_lesson.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kz.onelab.eighth_lesson.core.functional.Resource
import kz.onelab.eighth_lesson.core.functional.State
import kz.onelab.eighth_lesson.data.repository.PopularMoviesRepositoryImpl
import kz.onelab.eighth_lesson.presentation.model.Movie
import javax.inject.Inject

@HiltViewModel
class PopularMovieViewModel @Inject constructor(
    private val repository: PopularMoviesRepositoryImpl
) : ViewModel() {

    private val _popularMoviesLiveData = repository.observeMoviesStateFlow
        .map(::mapToUiState)
        .asLiveData()

    val popularMoviesLiveData: LiveData<Resource<List<Movie>>>
        get() = _popularMoviesLiveData

    fun getPopularMovies() {
        viewModelScope.launch {
            repository.fetchPopularMovies()
        }
    }

    private fun mapToUiState(moviesState: State<List<Movie>>) =
        when (moviesState) {
            State.Initial -> Resource.Empty
            State.Loading -> Resource.Loading
            is State.Failure -> Resource.Error(moviesState.exception)
            is State.Data -> Resource.Success(moviesState.data)
        }

//    companion object {
//        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
//            @Suppress("UNCHECKED_CAST")
//            override fun <T : ViewModel> create(
//                modelClass: Class<T>,
//                extras: CreationExtras
//            ): T {
//                // Get the Application object from extras
//                val application = checkNotNull(extras[APPLICATION_KEY])
//                // Create a SavedStateHandle for this ViewModel from extras
//                val savedStateHandle = extras.createSavedStateHandle()
//
//                return PopularMovieViewModel(
//                    (application as MoviesApplication).moviesRepository,
//                    savedStateHandle
//                ) as T
//            }
//        }
//    }
}