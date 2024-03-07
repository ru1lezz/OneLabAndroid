package kz.onelab.third_lesson

object DataGenerator {

    private val items = mutableListOf<ListItem>()

    fun generateItems(): List<ListItem> {

        for (i in 1..16) {
            if (i % 4 == 0) {
                items.add(ListItem.Header(title = "Header $i"))
            }
            items.add(ListItem.Content(
                title = "Title $i",
                subtitle = "Subtitle $i",
            ))
        }

        return items
    }
}