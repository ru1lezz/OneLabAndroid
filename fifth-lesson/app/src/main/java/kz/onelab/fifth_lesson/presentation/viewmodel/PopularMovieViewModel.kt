package kz.onelab.fifth_lesson.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.launch
import kz.onelab.fifth_lesson.MoviesApplication
import kz.onelab.fifth_lesson.core.functional.Resource
import kz.onelab.fifth_lesson.core.functional.onFailure
import kz.onelab.fifth_lesson.core.functional.onSuccess
import kz.onelab.fifth_lesson.data.repository.PopularMoviesRepository
import kz.onelab.fifth_lesson.presentation.model.Movie

// MVVM -> Model View -> ViewModel

class PopularMovieViewModel(
    private val repository: PopularMoviesRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _popularMoviesLiveData = MutableLiveData<Resource<List<Movie>>>()
    val popularMoviesLiveData: LiveData<Resource<List<Movie>>>
        get() = _popularMoviesLiveData

    fun getPopularMovies() {
        _popularMoviesLiveData.value = Resource.Loading
        viewModelScope.launch {
            repository.getPopularMovies()
                .onFailure { throwable ->
                    _popularMoviesLiveData.value = Resource.Error(throwable)
                }
                .onSuccess { popularMovies ->
                    _popularMoviesLiveData.value = Resource.Success(popularMovies.popularMovieList)
                }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])
                // Create a SavedStateHandle for this ViewModel from extras
                val savedStateHandle = extras.createSavedStateHandle()

                return PopularMovieViewModel(
                    (application as MoviesApplication).moviesRepository,
                    savedStateHandle
                ) as T
            }
        }
    }
}