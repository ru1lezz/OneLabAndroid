package kz.onelab.fifth_lesson.observer

class ObserverExample {

    fun main() {

    }
}

// наблюдаемый (Subject)
class ShymbulakFestival {
    private val observers = mutableListOf<Observer>()
    private val _artistList = mutableListOf<String>()
    val artistList: List<String>
        get() = _artistList

    fun addArtist(name: String) {
        _artistList.add(name)
        notifyAllObservers()
    }

    fun attach(observer: Observer) {
        observers.add(observer)
    }

    fun detach(observer: Observer) {
        observers.remove(observer)
    }

    private fun notifyAllObservers() {
        observers.forEach { it.update() }
    }
}

// Интерфейс наблюдателя
interface Observer {
    fun update()
}

// Конкретный наблюдатель
class ConcreteObserver(
    private val shymbulakFestival: ShymbulakFestival
) : Observer {
    override fun update() {
        println("Хэй у нас пополнение у артистов")
        val artistList = shymbulakFestival.artistList
        for (artist in artistList) {
            println(artist)
        }
    }
}
