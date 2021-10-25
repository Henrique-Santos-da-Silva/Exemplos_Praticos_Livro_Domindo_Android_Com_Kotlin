package dominando.android.enghaw.db

import androidx.lifecycle.LiveData
import androidx.room.*
import dominando.android.enghaw.model.Album

@Dao
interface AlbumDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(album: Album): Long

    @Delete
    fun delete(album: Album)

    @Query("SELECT * FROM Album ORDER BY year")
    fun allAlbums(): LiveData<List<Album>>

    @Query("SELECT * FROM ALBUM WHERE title LIKE :title")
    fun albumByTitle(title: String): List<Album>
}