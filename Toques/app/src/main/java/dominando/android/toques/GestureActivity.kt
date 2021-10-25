package dominando.android.toques

import android.gesture.Gesture
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.core.view.GestureDetectorCompat

class GestureActivity : AppCompatActivity() {
    private lateinit var gestureDetector: GestureDetectorCompat
    private val doubleTabListener: GestureDetector.OnDoubleTapListener = object: GestureDetector.OnDoubleTapListener {
        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            Log.d(TAG, "onSingleTapConfirmed")
            return true
        }

        override fun onDoubleTap(motionEvent: MotionEvent): Boolean {
            Log.d(TAG, "onDoubleTap")
            return true
        }

        override fun onDoubleTapEvent(motionEvent: MotionEvent): Boolean {
            Log.d(TAG, "onDoubleTapEvent")
            return true
        }
    }
    private val gestureListener: GestureDetector.OnGestureListener = object: GestureDetector.OnGestureListener {
        override fun onDown(motionEvent: MotionEvent): Boolean {
            Log.d(TAG, "onDown")
            return true
        }

        override fun onShowPress(motionEvent: MotionEvent) {
            Log.d(TAG, "onShowPress")
        }

        override fun onSingleTapUp(motionEvent: MotionEvent): Boolean {
            Log.d(TAG, "onSingleTapUp")
            return true
        }

        override fun onScroll(motionEvent: MotionEvent, motionEvent2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
            Log.d(TAG, "onScroll")
            return true
        }

        override fun onLongPress(motionEvent: MotionEvent) {
            Log.d(TAG, "onLongPress")
        }

        override fun onFling(motionEvent: MotionEvent, motionEvent2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            Log.d(TAG, "onFling")
            return true
        }
    }

    val simpleListener = object: GestureDetector.SimpleOnGestureListener() {
        override fun onDoubleTap(e: MotionEvent?): Boolean {
            Log.d(TAG, "onDoubleTap")
            return super.onDoubleTap(e)
        }

        override fun onLongPress(e: MotionEvent?) {
            super.onLongPress(e)
            Log.d(TAG, "onLongPress")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gesture)
        gestureDetector = GestureDetectorCompat(this, simpleListener)
        gestureDetector.setOnDoubleTapListener(doubleTabListener)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    companion object {
        private val TAG = "DominandoAndroid"
    }
}