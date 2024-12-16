package com.katherinekurokawa.definitiveprojecy.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isEmpty
import androidx.core.view.isVisible
import com.katherinekurokawa.definitiveprojecy.databinding.FragmentSampleProjectsBinding


class SampleProjectsFragment : Fragment() {
    private lateinit var _binding: FragmentSampleProjectsBinding
    private val binding: FragmentSampleProjectsBinding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSampleProjectsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (binding.rcProjects.isEmpty()){
            binding.tvMessageProject.isVisible = true
        }
    }


}