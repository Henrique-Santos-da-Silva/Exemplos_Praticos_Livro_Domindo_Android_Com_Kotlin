package dominando.android.enghaw.db

import android.content.Context
import androidx.lifecycle.LiveData
import dominando.android.enghaw.model.Album

class AlbumRepository(context: Context) {
    private val db: AppDb by lazy {
        AppDb.getInstance(context) as AppDb
    }

    private val dao: AlbumDao by lazy {
        db.albumDao()
    }

    fun save(album: Album) {
        val id = dao.save(album)
        album.id = id
    }

    fun delete(album: Album) {
        dao.delete(album)
    }

    fun loadFavorites(): LiveData<List<Album>> {
        return dao.allAlbums()
    }

    fun isFavorite(album: Album): Boolean {
        return dao.albumByTitle(album.title).isNotEmpty()
    }
}