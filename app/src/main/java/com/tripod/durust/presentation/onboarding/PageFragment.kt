import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.runtime.*
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.viewpager.widget.PagerAdapter
import com.cuberto.liquid_swipe.LiquidPager

@Composable
fun LiquidPagerScreen(context: Context) {
    val pagerAdapter = remember { LiquidPagerAdapter() }

    AndroidView(
        factory = { ctx ->
            CustomLiquidPagerViewGroup(ctx).apply {
                setAdapter(pagerAdapter)
            }
        }
    )
}

class LiquidPagerAdapter : PagerAdapter() {
    override fun getCount(): Int {
        return 3 // Number of pages
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = View(container.context).apply {
            setBackgroundColor(
                when (position) {
                    0 -> Color.RED
                    1 -> Color.GREEN
                    else -> Color.BLUE
                }
            )
        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}

class CustomLiquidPagerViewGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private val liquidPager = LiquidPager(context)

    init {
        addView(liquidPager)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return true
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        liquidPager.dispatchTouchEvent(event)
        return true
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        liquidPager.layout(0, 0, width, height)
    }

    fun setAdapter(adapter: PagerAdapter) {
        liquidPager.adapter = adapter
    }
}
