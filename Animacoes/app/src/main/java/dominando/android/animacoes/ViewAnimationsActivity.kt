package dominando.android.animacoes

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.*
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import kotlinx.android.synthetic.main.activity_view_animations.*

class ViewAnimationsActivity : BaseActivity() {
    private val animations: Array<Animation> by lazy {
        initAnimations()
    }

    private val interpolators: Array<Interpolator> by lazy {
        initInterpolators()
    }

    private val animationListener = object: Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation?) {
        }

        override fun onAnimationEnd(animation: Animation?) {
            btnPlay.isEnabled = true
            animation?.setAnimationListener(null)
        }

        override fun onAnimationRepeat(animation: Animation?) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_animations)

        btnPlay.setOnClickListener { executeAnimation() }

        imgBazinga.setOnClickListener { view ->
            val animation = ActivityOptions.makeScaleUpAnimation(view, 0, 0, view.width, view.height).toBundle()
            startActivity(Intent(this, LayoutChangesActivity::class.java), animation)
        }
    }

    private fun initAnimations(): Array<Animation> {
        // region

//        val animationDuration = 1000L
//        val alphaAnim = AlphaAnimation(1f, 0f)
//        val rotateAnim = RotateAnimation(
//            0f, 360f,
//            Animation.RELATIVE_TO_SELF, 0.5f,
//            Animation.RELATIVE_TO_SELF, 0.5f)
//        val scaleAnim = ScaleAnimation(
//            1f, 3f, 1f, 3f,
//            Animation.RELATIVE_TO_SELF, 0.5f,
//            Animation.RELATIVE_TO_SELF, 0.5f)
//        val translateAnim = TranslateAnimation(
//            Animation.RELATIVE_TO_SELF, 0f,
//            Animation.RELATIVE_TO_SELF, 1.0f,
//            Animation.RELATIVE_TO_SELF, 0f,
//            Animation.RELATIVE_TO_SELF, 2.0f)
//        val set = AnimationSet(true)
//
//        set.addAnimation(alphaAnim)
//        set.addAnimation(rotateAnim)
//        set.addAnimation(scaleAnim)
//        set.addAnimation(translateAnim)
//
//        val animations = arrayOf(alphaAnim, rotateAnim, scaleAnim, translateAnim, set)
//        for (i in 0..animations.size - 2) {
//            animations[i].duration = animationDuration
//            animations[i].repeatMode = Animation.REVERSE
//            animations[i].repeatCount = 1
//        }
//
//        return animations

        // endregion

        // Usando arquivos XML para animações
        return arrayOf(
            AnimationUtils.loadAnimation(this, R.anim.transparency),
            AnimationUtils.loadAnimation(this, R.anim.rotation),
            AnimationUtils.loadAnimation(this, R.anim.expand),
            AnimationUtils.loadAnimation(this, R.anim.move),
            AnimationUtils.loadAnimation(this, R.anim.all_together)
        )

    }

    private fun initInterpolators(): Array<Interpolator> {
        return arrayOf(
            AccelerateDecelerateInterpolator(),
            AccelerateInterpolator(1.0f), // <- fator(opcional)
            AnticipateInterpolator(2.0f), // <-tensão(opcional)
            AnticipateOvershootInterpolator(2.0f, 1.5f), // <-tensão, tensão extra
            BounceInterpolator(), // <-tensão(opcional)
            CycleInterpolator(2f), // <-ciclos
            DecelerateInterpolator(1.0f), // <-fator (optional)
            FastOutLinearInInterpolator(),
            FastOutSlowInInterpolator(),
            LinearInterpolator(),
            OvershootInterpolator(2.0f) // <-tensão(opcional)
        )
    }

    private fun executeAnimation() {
        val interpolator = interpolators[spnInterpolators.selectedItemPosition]
        val animation = animations[spnAnimations.selectedItemPosition]
        animation.interpolator = interpolator
        animation.setAnimationListener(animationListener)
        imgBazinga.startAnimation(animation)
        btnPlay.isEnabled = false
    }
}