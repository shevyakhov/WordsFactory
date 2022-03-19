package com.example.wordsfactory.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.wordsfactory.R
import com.example.wordsfactory.databinding.FragmentDictionaryPlaceholderBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class DictionaryPlaceholderFragment : Fragment() {
    private lateinit var binding: FragmentDictionaryPlaceholderBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDictionaryPlaceholderBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigationView =
            view.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController =
            (childFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController
        bottomNavigationView.setupWithNavController(navController)

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DictionaryPlaceholderFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}