package dominando.android.livros.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlin.jvm.Throws

@Throws(Throwable::class)
fun <T> LiveData<T>.observeOnce(observer: Observer<T>) {
    observeForever(object: Observer<T> {
        override fun onChanged(t: T) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}