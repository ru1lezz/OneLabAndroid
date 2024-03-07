package kz.onelab.third_lesson

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kz.onelab.third_lesson.databinding.FragmentOneBinding

class FragmentOne : Fragment() {

    private var _binding: FragmentOneBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            continueButton.setOnClickListener {
                val navigateToFragmentTwoAction = FragmentOneDirections.actionFragmentOneToFragmentSecond(
                    argSomeData = "Данные из первого фрагмента"
                )
                findNavController().navigate(navigateToFragmentTwoAction)
            }
        }
    }
}