package com.example.wordsfactory.ui.introduction_screens.sign_up

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.wordsfactory.R
import com.example.wordsfactory.databinding.FragmentSignUpBinding
import com.example.wordsfactory.dictionary_logic.database.UserEntity
import com.example.wordsfactory.dictionary_logic.repository.Injection
import com.example.wordsfactory.dictionary_logic.repository.app_viewmodel.AppViewModel
import com.example.wordsfactory.ui.alert_fragment.AlertFragment
import com.example.wordsfactory.ui.navigation_fragments.PlaceHolderFragment


class SignUpFragment : Fragment() {
    private lateinit var appViewModel: AppViewModel
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appViewModel = ViewModelProvider(this, Injection.provideFactory(requireContext()))
            .get(AppViewModel::class.java)
        initBinding()
    }

    private fun initBinding() {
        /* if user fields are not empty -> start DictionaryFragment()
        *   else ->start alertFragment
        * */
        binding.botButton.setOnClickListener {
            with(binding) {
                if (editName.text?.isNotEmpty() == true && editEmail.text?.isNotEmpty() == true && editPassword.text?.isNotEmpty() == true) {
                    appViewModel.saveUser(
                        UserEntity(
                            editName.text.toString(),
                            editEmail.text.toString(),
                            editPassword.text.toString()
                        )
                    )
                    startDictionaryFragment()
                } else {
                    AlertFragment.newInstance(
                        getString(R.string.warning),
                        findEmptyFields()
                    ).show(
                        childFragmentManager, ""
                    )
                }
            }
        }
    }

    private fun findEmptyFields(): String {
        var fields = getString(R.string.pleaseEnter) + "\n"
        if (binding.editName.text?.isEmpty() == true)
            fields += getString(R.string.nameAdd)
        if (binding.editEmail.text?.isEmpty() == true)
            fields += getString(R.string.emailAdd)
        if (binding.editPassword.text?.isEmpty() == true)
            fields += getString(R.string.passAdd)
        return fields
    }

    private fun startDictionaryFragment() {
        hideKeyboard()

        parentFragmentManager.beginTransaction()
            .add(R.id.fragmentHolder, PlaceHolderFragment.newInstance()).commit()
    }

    private fun hideKeyboard() {
        val inputManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            requireActivity().currentFocus?.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignUpFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}