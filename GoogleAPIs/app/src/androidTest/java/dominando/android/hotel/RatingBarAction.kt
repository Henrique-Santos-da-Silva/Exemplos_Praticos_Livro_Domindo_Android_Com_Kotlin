package dominando.android.hotel

import android.view.View
import android.widget.RatingBar
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher

class RatingBarAction: ViewAction {
    private var rating: Float = 0f

    override fun getConstraints(): Matcher<View> {
        return ViewMatchers.isAssignableFrom(RatingBar::class.java)
    }

    override fun getDescription(): String {
        return "RatingBar wuth rating $rating"
    }

    override fun perform(uiController: UiController?, view: View?) {
        val ratingBar = view as RatingBar
        ratingBar.rating = rating.toFloat()
    }

    companion object {
        fun setRating(value: Float): RatingBarAction {
            val ratingBarAction = RatingBarAction()
            ratingBarAction.rating = value
            return ratingBarAction
        }
    }

}