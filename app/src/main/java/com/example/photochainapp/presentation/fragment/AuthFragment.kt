package com.example.photochainapp.presentation.fragment

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.photochainapp.R
import com.example.photochainapp.Resource
import com.example.photochainapp.presentation.language.LanguageHelper
import com.example.photochainapp.databinding.FragmentAuthBinding
import com.example.photochainapp.presentation.viewmodel.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthFragment : BaseFragment<FragmentAuthBinding>(FragmentAuthBinding::inflate) {

    private val viewModel: AuthViewModel by activityViewModels()
    private lateinit var googleSignInClient: GoogleSignInClient
    private val signInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task)
        }

    override fun setUp() {
        val googleSignInOptions = googleSignInOptions()
        viewModel.setGoogleSignInOptions(googleSignInOptions)
        Log.d("AuthFragment", "GoogleSignInOptions emitted: $googleSignInOptions")
        googleSignInClient = GoogleSignIn.getClient(requireContext(), googleSignInOptions)
        binding.signInButton.setOnClickListener {

            signInWithGoogle()
            observeAuthState()
        }


        if (viewModel.getCurrentUser() != null) {

            if (findNavController().currentDestination?.id == R.id.authFragment)
                view?.post { findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToPhotoFragment()) }
        }

        binding.languageBtn.setOnClickListener {
            val currentLanguage = LanguageHelper.getSavedLanguage(requireContext())

            val newLanguage = if (currentLanguage == "en") "ka" else "en"

            LanguageHelper.setLocale(requireActivity(), newLanguage)
        }
    }

    private fun signInWithGoogle() {
        signInLauncher.launch(googleSignInClient.signInIntent)
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.getResult(ApiException::class.java)
            account?.idToken?.let { idToken ->
                viewModel.signInWithGoogle(idToken)
                view?.findNavController()
                    ?.navigate(AuthFragmentDirections.actionAuthFragmentToPhotoFragment())
            }
        } catch (e: ApiException) {
            Toast.makeText(requireContext(), "Google sign-in failed", Toast.LENGTH_SHORT).show()
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun observeAuthState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.authState.collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val user = result.data
                        Toast.makeText(
                            requireContext(),
                            "Welcome ${user?.displayName}",
                            Toast.LENGTH_LONG
                        ).show()


                    }

                    is Resource.Error -> {
                        Toast.makeText(
                            requireContext(),
                            result.message,
                            Toast.LENGTH_LONG
                        ).show()

                    }
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE

                }

            }
        }
    }

    private fun googleSignInOptions(): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }


}