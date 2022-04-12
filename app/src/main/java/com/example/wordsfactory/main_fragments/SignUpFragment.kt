package com.example.wordsfactory.main_fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.example.wordsfactory.R
import com.example.wordsfactory.databinding.FragmentSignUpBinding


class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
    }

    private fun initBinding() {
        binding.botButton.setOnClickListener {
            startDictionaryFragment()
            //todo remove
            /*with(binding) {

                if (editName.text?.isNotEmpty() == true && editEmail.text?.isNotEmpty() == true && editPassword.text?.isNotEmpty() == true) {
                    startDictionaryFragment()
                } else {
                    AlertFragment.newInstance(
                        getString(R.string.warning),
                        findEmptyFields()
                    ).show(
                        childFragmentManager, ""
                    )
                }
            }*/
        }
    }

    private fun findEmptyFields(): String {
        var fields = getString(R.string.pleaseEnter) +"\n"
        if (binding.editName.text?.isEmpty() == true)
            fields += "Name "
        if (binding.editEmail.text?.isEmpty() == true)
            fields += "Email "
        if (binding.editPassword.text?.isEmpty() == true)
            fields += "Password"
        return fields
    }

    private fun startDictionaryFragment() {
       hideKeyboard()

        parentFragmentManager.beginTransaction()
            .add(R.id.fragmentHolder, DictionaryPlaceholderFragment.newInstance()).commit()
    }
    private fun hideKeyboard(){
        val inputManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            requireActivity().currentFocus?.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
    companion object {
        @JvmStatic
        fun newInstance() = SignUpFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}