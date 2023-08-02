package com.skid.fitnesskittestapp.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.postDelayed
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout
import com.skid.fitnesskittestapp.FitnessKitApp
import com.skid.fitnesskittestapp.databinding.CustomSnackbarLayoutBinding
import com.skid.fitnesskittestapp.databinding.FragmentScheduleBinding
import com.skid.fitnesskittestapp.ui.adapters.ScheduleAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ScheduleFragment : Fragment() {

    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!

    private val scheduleViewModel: ScheduleViewModel by viewModels {
        ScheduleViewModelFactory(activity?.application as FitnessKitApp)
    }

    private val scheduleAdapter by lazy { ScheduleAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewInit()
        collectingSchedule()
        collectingNetworkError()
        swipeToRefresh()
    }

    private fun recyclerViewInit() = with(binding.scheduleFragmentRecyclerView) {
        layoutManager = LinearLayoutManager(context)
        adapter = scheduleAdapter
    }

    private fun collectingSchedule() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                scheduleViewModel.schedule.collect { items ->
                    scheduleAdapter.submitList(items)

                    binding.scheduleFragmentSwipeRefreshLayout.apply {
                        if (isRefreshing) isRefreshing = false
                    }

                    binding.scheduleFragmentShimmerLayout.apply {
                        if (isShimmerVisible) {
                            delay(2000)
                            visibility = View.GONE
                            stopShimmer()
                        }
                    }
                }
            }
        }
    }

    private fun collectingNetworkError() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                scheduleViewModel.networkError.collect { error ->
                    if (error != null) {
                        delay(2000)
                        showSnackbar(error)
                        scheduleViewModel.setNetworkErrorToNull()
                    }
                }
            }
        }
    }

    private fun swipeToRefresh() = with(binding.scheduleFragmentSwipeRefreshLayout) {
        setOnRefreshListener {
            scheduleViewModel.refreshSchedule()

            postDelayed(3000) {
                if (isRefreshing) isRefreshing = false
            }
        }
    }

    private fun showSnackbar(text: String) {
        val snackbar = Snackbar.make(binding.root, "", Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundColor(Color.TRANSPARENT)
        snackbar.view.setPadding(0, 0, 0, 80)
        val snackbarLayout = snackbar.view as SnackbarLayout
        val snackbarLayoutBinding = CustomSnackbarLayoutBinding.inflate(layoutInflater)
        snackbarLayoutBinding.customSnackbarText.text = text
        snackbarLayout.addView(snackbarLayoutBinding.root, 0)
        snackbar.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}