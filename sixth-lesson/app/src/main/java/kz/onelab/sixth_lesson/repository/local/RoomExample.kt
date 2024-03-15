package kz.onelab.sixth_lesson.repository.local

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    fun insert(user: User): Long

    @Query("SELECT * FROM User")
    fun getAllUsers(): List<User>
}

@Dao
interface BookDao {
    @Insert
    fun insert(book: Book): Long

    @Query("SELECT * FROM Book WHERE userOwnerId = :userId")
    fun findBooksForUser(userId: Long): List<Book>
}

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Long,
    val name: String,
)

@Entity(foreignKeys = [
    ForeignKey(
        entity = User::class,
        parentColumns = arrayOf("userId"),
        childColumns = arrayOf("userOwnerId"),
        onDelete = ForeignKey.CASCADE
    )
])
data class Book(
    @PrimaryKey(autoGenerate = true) val bookId: Long,
    val title: String,
    val userOwnerId: Long
)