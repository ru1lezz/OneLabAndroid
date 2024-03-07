package kz.onelab.third_lesson

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class DividerItemDecorator(
    context: Context,
    @DrawableRes dividerRes: Int,
    startMargin: Float = 0F,
    endMargin: Float = 0F
) : RecyclerView.ItemDecoration() {

    private val divider: Drawable? = ContextCompat.getDrawable(context, dividerRes)

    private val startMarginPx: Int = startMargin.dpToPx(context)

    private val endMarginPx: Int = endMargin.dpToPx(context)

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val dividerLeft = parent.paddingLeft + startMarginPx
        val dividerRight = parent.width - parent.paddingRight - endMarginPx
        val childCount = parent.childCount
        for (i in 0..childCount - 2) {
            val child: View = parent.getChildAt(i)
            val viewHolder = parent.getChildViewHolder(child)
            val nextViewHolder = parent.findViewHolderForAdapterPosition(viewHolder.bindingAdapterPosition + 1)
            if (viewHolder.itemViewType == (nextViewHolder?.itemViewType ?: -1)) {
                val params = child.layoutParams as RecyclerView.LayoutParams
                val dividerTop: Int = child.bottom + params.bottomMargin
                val dividerBottom = dividerTop + (divider?.intrinsicHeight ?: 0)
                divider?.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
                divider?.draw(canvas)
            }
        }
    }
}
