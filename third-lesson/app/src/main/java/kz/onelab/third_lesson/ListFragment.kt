package kz.onelab.third_lesson

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kz.onelab.third_lesson.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding
        get() = _binding!!

    private val adapter by lazy {
        MyAdapter(
            clickListener = { item -> onClickItem(item) }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            list.adapter = adapter
            list.layoutManager = LinearLayoutManager(context)
            list.addItemDecoration(DividerItemDecorator(
                context = requireContext(),
                dividerRes = R.drawable.divider_item,
                startMargin = 16.dp.toFloat()
            ))
        }
        adapter.setItems(DataGenerator.generateItems())
    }

    private fun onClickItem(listItem: ListItem) {
        // do some action
    }
}