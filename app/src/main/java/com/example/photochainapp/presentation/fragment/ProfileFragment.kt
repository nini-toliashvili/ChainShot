package com.example.photochainapp.presentation.fragment

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.example.photochainapp.presentation.language.LanguageHelper
import com.example.photochainapp.databinding.FragmentProfileBinding
import com.example.photochainapp.presentation.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment :BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private val authViewModel: AuthViewModel by activityViewModels()
    override fun setUp() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                authViewModel.googleSignInOptions.collectLatest { options ->
                    options?.let {
                        Log.d("AuthFragment", "Collected options: $it")
                    } ?: Log.d("AuthFragment", "No options received yet.")

                    Log.d("HomeFragment", "Received GoogleSignInOptions: $options")

                }
            }


        }

        setUpProfileInfo()
        binding.logOutBtn.setOnClickListener {
            Log.d("HomeFragment", "Sign out button clicked.")
            signOut()
        }

        binding.languageBtn.setOnClickListener {
            val currentLanguage = LanguageHelper.getSavedLanguage(requireContext())

            val newLanguage = if (currentLanguage == "en") "ka" else "en"

            LanguageHelper.setLocale(requireActivity(), newLanguage)
        }

    }


    private fun setUpProfileInfo() {
        val user = authViewModel.userData.value
        binding.emailAddress.text = user?.email
        binding.name.text = user?.name

    }


    private fun signOut() {

        viewLifecycleOwner.lifecycleScope.launch {

            Log.d("HomeFragment", "Sign out clicked.")
            authViewModel.googleSignInOptions.firstOrNull()?.let { googleSignInOptions ->
                authViewModel.signOut(requireContext(), googleSignInOptions)
            }

        }

        view?.findNavController()?.navigate(ProfileFragmentDirections.actionProfileFragmentToAuthFragment())

    }

}