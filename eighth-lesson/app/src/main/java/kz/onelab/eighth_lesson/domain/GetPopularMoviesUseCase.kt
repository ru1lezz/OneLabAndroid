package kz.onelab.eighth_lesson.domain

class GetPopularMoviesUseCase(
    private val repository: PopularMoviesRepository
) {

    suspend operator fun invoke() {
        return repository.fetchPopularMovies()
    }
}