package kz.onelab.third_lesson

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kz.onelab.third_lesson.databinding.ItemContentBinding
import kz.onelab.third_lesson.databinding.ItemHeaderBinding

class MyAdapter(
    private val clickListener: (ListItem) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<ListItem>()

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_CONTENT = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ListItem.Header -> TYPE_HEADER
            is ListItem.Content -> TYPE_CONTENT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return getViewHolderByViewType(viewType, layoutInflater, parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.bind(items[position] as ListItem.Header)
            is ContentViewHolder -> holder.bind(items[position] as ListItem.Content)
        }
    }

    override fun getItemCount(): Int = items.size

    fun setItems(newItems: List<ListItem>) {
        val difResult = DiffUtil.calculateDiff(DiffUtilCallback(
            oldList = items,
            newList = newItems
        ))
        items.clear()
        items.addAll(newItems)
        difResult.dispatchUpdatesTo(this)
    }

    private fun getViewHolderByViewType(
        viewType: Int,
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> HeaderViewHolder(ItemHeaderBinding.inflate(layoutInflater, parent, false))
            TYPE_CONTENT-> {
                val viewHolder = ContentViewHolder(
                    ItemContentBinding.inflate(layoutInflater, parent, false)
                )
                viewHolder.itemView.setOnClickListener {
                    clickListener.invoke(items[viewHolder.bindingAdapterPosition])
                }
                return viewHolder
            }
            else -> throw IllegalArgumentException("illegal view type")
        }
    }

    class HeaderViewHolder(private val binding: ItemHeaderBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(header: ListItem.Header) {
            binding.title.text = header.title
        }
    }

    class ContentViewHolder(private val binding: ItemContentBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(content: ListItem.Content) {
            binding.contentTitle.text = content.title
            binding.contentSubtitle.text = content.subtitle
        }
    }
}

class DiffUtilCallback(
    private val oldList: List<ListItem>,
    private val newList: List<ListItem>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}