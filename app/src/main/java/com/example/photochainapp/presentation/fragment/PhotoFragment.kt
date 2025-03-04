package com.example.photochainapp.presentation.fragment

import android.content.ContentValues
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.photochainapp.databinding.FragmentPhotoBinding
import com.example.photochainapp.presentation.viewmodel.PhotoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoFragment : BaseFragment<FragmentPhotoBinding>(FragmentPhotoBinding::inflate) {
    private val viewModel: PhotoViewModel by viewModels()
    private var photoUri: Uri? = null
    private val takePictureLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success && photoUri != null) {
                viewModel.saveImageAndUpdateWidget(requireContext(), photoUri.toString())
                Toast.makeText(
                    requireContext(),
                    "Photo saved & widget updated!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


    override fun setUp() {




        Log.d("fragment_created", "fragment got created")
        binding.takePhotoBtn.setOnClickListener {
            takePhoto()
        }

        binding.profileBtn.setOnClickListener {
            view?.findNavController()?.navigate(PhotoFragmentDirections.actionPhotoFragmentToProfileFragment())
        }


        binding.userFriendsBtn.setOnClickListener {
            view?.findNavController()?.navigate(PhotoFragmentDirections.actionPhotoFragmentToFriendsFragments())
        }

    }

    private fun takePhoto() {
        photoUri = createPhotoUri()
        photoUri?.let {
            takePictureLauncher.launch(it)
        }
    }



    private fun createPhotoUri(): Uri? {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "photo_${System.currentTimeMillis()}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(
                MediaStore.Images.Media.RELATIVE_PATH,
                "Pictures/MyAppPhotos"
            ) // Saves in Pictures/MyAppPhotos
        }

        return requireContext().contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )
    }
}