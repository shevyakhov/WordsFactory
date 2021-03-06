package com.example.wordsfactory.ui.navigation_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.wordsfactory.R
import com.example.wordsfactory.databinding.FragmentPlaceholderBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable


class PlaceHolderFragment : Fragment() {

    private var _binding: FragmentPlaceholderBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaceholderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* init bottomNavigationView*/
        val bottomNavigationView =
            view.findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        val bottomNavigationViewBackground =
            bottomNavigationView.background as MaterialShapeDrawable
        bottomNavigationViewBackground.shapeAppearanceModel =
            bottomNavigationViewBackground.shapeAppearanceModel.toBuilder()
                .setTopRightCorner(
                    CornerFamily.ROUNDED,
                    resources.getDimension(R.dimen.radius_small)
                )
                .setTopLeftCorner(
                    CornerFamily.ROUNDED,
                    resources.getDimension(R.dimen.radius_small)
                )
                .build()
        val navController =
            (childFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.questionsFragment || destination.id == R.id.resultFragment) {
                bottomNavigationView.visibility = View.GONE
            } else {
                bottomNavigationView.visibility = View.VISIBLE
            }

        }
        bottomNavigationView.setupWithNavController(navController)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = PlaceHolderFragment()
    }
}