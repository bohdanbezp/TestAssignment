package dev.simplx.testassignment.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import dev.simplx.testassignment.BaseFragment
import dev.simplx.testassignment.databinding.FragmentFirstBinding
import dev.simplx.testassignment.extensions.value
import java.util.Locale

@AndroidEntryPoint
class DashboardFragment : BaseFragment() {

    private val viewModel: DashboardViewModel by viewModels()

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        // set layout manager
        binding.bootsRecycler.layoutManager = mLayoutManager
        // set default animator
        binding.bootsRecycler.itemAnimator = DefaultItemAnimator()

        viewModel.collectState(DashboardViewState::bootEvents) { bootEvents ->
            binding.bootsRecycler.isVisible = bootEvents.value.isNullOrEmpty().not()
            binding.emptyView.isVisible = bootEvents.value.isNullOrEmpty()

            val adapter = BootEventsAdapter(emptyList())
            binding.bootsRecycler.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}