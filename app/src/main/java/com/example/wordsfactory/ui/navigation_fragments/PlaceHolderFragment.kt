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
    private lateinit var fragmentPlaceholderBinding: FragmentPlaceholderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentPlaceholderBinding = FragmentPlaceholderBinding.inflate(layoutInflater)
        return fragmentPlaceholderBinding.root
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
        bottomNavigationView.setupWithNavController(navController)


    }

    companion object {
        @JvmStatic
        fun newInstance() = PlaceHolderFragment()
    }
}