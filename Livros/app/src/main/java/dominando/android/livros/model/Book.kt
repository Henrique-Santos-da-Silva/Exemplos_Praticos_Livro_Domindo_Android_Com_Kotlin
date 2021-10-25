package dominando.android.livros.model

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import dominando.android.livros.BR
import kotlinx.android.parcel.Parcelize

@Parcelize
class Book(): Parcelable, BaseObservable() {
    @get:Bindable
    var id: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.id)
        }

    @get:Bindable
    var title: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.title)
        }

    @get:Bindable
    var author: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.author)
        }

    @get:Bindable
    var coverUrl: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.coverUrl)
        }

    @get:Bindable
    var pages: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.pages)
        }

    @get:Bindable
    var year: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.year)
        }

    @get:Bindable
    var publisher: Publisher? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.publisher)
        }

    @get:Bindable
    var available: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.available)
        }

    @get:Bindable
    var mediaType: MediaType = MediaType.PAPER
        set(value) {
            field = value
            notifyPropertyChanged(BR.mediaType)
        }

    @get:Bindable
    var rating: Float = 0.0f
        set(value) {
            field = value
            notifyPropertyChanged(BR.rating)
        }

    var userId: String = ""

}


