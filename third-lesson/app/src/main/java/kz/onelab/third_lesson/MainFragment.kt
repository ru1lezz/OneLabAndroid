package kz.onelab.third_lesson

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import kz.onelab.third_lesson.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            listsButton.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_listFragment)
            }
            nextButton.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_fragmentOne)
            }
            pagerButton.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_viewPagerFragment)
            }
            resultButton.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_withReturnResultFragment)
            }
            setFragmentResultListener(RETURN_RESULT_REQUEST) { _, bundle ->
                val data = bundle.getParcelable<DummyData>(DUMMY_DATA_ARGS)
                someTextView.text = data?.text
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
    }
}