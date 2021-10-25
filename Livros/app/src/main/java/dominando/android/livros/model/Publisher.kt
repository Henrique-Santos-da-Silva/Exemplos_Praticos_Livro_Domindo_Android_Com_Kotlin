package dominando.android.livros.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Publisher(
    var id: String = "",
    var name: String = ""
): Parcelable {
    override fun toString(): String = "$id - $name"
}

