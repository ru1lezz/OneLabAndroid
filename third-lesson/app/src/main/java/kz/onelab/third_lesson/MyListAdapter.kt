package kz.onelab.third_lesson

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kz.onelab.third_lesson.databinding.ItemContentBinding
import kz.onelab.third_lesson.databinding.ItemHeaderBinding

class MyListAdapter(
    private val clickListener: (ListItem) -> Unit
) : ListAdapter<ListItem, RecyclerView.ViewHolder>(DiffUtilCallback()) {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_CONTENT = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ListItem.Header -> TYPE_HEADER
            is ListItem.Content -> TYPE_CONTENT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_HEADER -> HeaderViewHolder(
                ItemHeaderBinding.inflate(layoutInflater, parent, false)
            )
            TYPE_CONTENT -> {
                val viewHolder = ContentViewHolder(
                    ItemContentBinding.inflate(layoutInflater, parent, false)
                )
                viewHolder.itemView.setOnClickListener {
                    val position = viewHolder.bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        clickListener.invoke(getItem(position))
                    }
                }
                return viewHolder
            }
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.bind(getItem(position) as ListItem.Header)
            is ContentViewHolder -> holder.bind(getItem(position) as ListItem.Content)
        }
    }

    class HeaderViewHolder(private val binding: ItemHeaderBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(header: ListItem.Header) {
            binding.title.text = header.title
        }
    }

    class ContentViewHolder(private val binding: ItemContentBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(content: ListItem.Content) {
            binding.contentTitle.text = content.title
            binding.contentSubtitle.text = content.subtitle
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<ListItem>() {

        override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return oldItem is ListItem.Header && newItem is ListItem.Header && oldItem.title == newItem.title ||
                    oldItem is ListItem.Content && newItem is ListItem.Content && oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return oldItem == newItem
        }
    }
}