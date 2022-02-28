package io.mishkav.fieldofmiracles.layoutManager

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 *
 *
 * @author Ворожцов Михаил on 28.02.2022
 */
class MainLayoutManager : RecyclerView.LayoutManager() {

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.MATCH_PARENT
        )
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        val viewWidth = (width * 0.075).toInt()
        val viewHeight = (width * 0.075).toInt()
        val centralX = width / 2
        val centralY = height / 2
        val radius = width / 4

        for (pos in 0 until itemCount) {
            val view = recycler?.getViewForPosition(pos)
            view?.let {
                addView(view)
                measureChildWithDecorationsAndMargin(view, 0, 0)

                val viewX: Int
                val viewY: Int
                val angle = ((360 / itemCount) * pos) % 360
                viewX = (centralX + cos(angle * TO_RADIANS) * radius).toInt()
                viewY = (centralY + sin(angle * TO_RADIANS) * radius).toInt()

                layoutDecorated(
                    view,
                    viewX - viewWidth,
                    viewY - viewHeight,
                    viewX + viewWidth,
                    viewY + viewHeight
                )
            }
        }
    }

    override fun canScrollVertically(): Boolean {
        return true
    }

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        scrollVertically(dy, recycler)
        return 0
    }

    private fun scrollVertically(dy: Int, recycler: RecyclerView.Recycler?) {
        val viewWidth = (width * 0.075).toInt()
        val viewHeight = (width * 0.075).toInt()
        val centralX = width / 2
        val centralY = height / 2
        val radius = width / 4
        val moveAngle =  (-dy) * TO_RADIANS

        removeAllViews()
        for (pos in 0 until itemCount) {
            val view = recycler?.getViewForPosition(pos)
            view?.let {
                addView(view)
                measureChildWithDecorationsAndMargin(view, 0, 0)

                val viewX: Int
                val viewY: Int
                val angle = ((360 / itemCount) * pos) % 360
                viewX = (centralX + cos(angle * TO_RADIANS + moveAngle) * radius).toInt()
                viewY = (centralY + sin(angle * TO_RADIANS + moveAngle) * radius).toInt()

                layoutDecorated(
                    view,
                    viewX - viewWidth,
                    viewY - viewHeight,
                    viewX + viewWidth,
                    viewY + viewHeight
                )
            }
        }
    }

    private fun measureChildWithDecorationsAndMargin(child: View, widthSpec: Int, heightSpec: Int) {
        var widthSpec = widthSpec
        var heightSpec = heightSpec
        val decorRect = Rect()

        calculateItemDecorationsForChild(child, decorRect)
        val lp = child.getLayoutParams() as RecyclerView.LayoutParams
        widthSpec = updateSpecWithExtra(
            widthSpec, lp.leftMargin + decorRect.left,
            lp.rightMargin + decorRect.right
        )
        heightSpec = updateSpecWithExtra(
            heightSpec, lp.topMargin + decorRect.top,
            lp.bottomMargin + decorRect.bottom
        )
        child.measure(widthSpec, heightSpec)
    }

    private fun updateSpecWithExtra(spec: Int, startInset: Int, endInset: Int): Int {
        if (startInset == 0 && endInset == 0) {
            return spec
        }
        val mode: Int = View.MeasureSpec.getMode(spec)
        return if (mode == View.MeasureSpec.AT_MOST || mode == View.MeasureSpec.EXACTLY) {
            View.MeasureSpec.makeMeasureSpec(
                View.MeasureSpec.getSize(spec) - startInset - endInset, mode
            )
        } else spec
    }

    companion object {
        private const val TO_RADIANS: Double = PI / 180
    }
}