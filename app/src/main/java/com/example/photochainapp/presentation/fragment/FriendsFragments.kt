package com.example.photochainapp.presentation.fragment


import androidx.navigation.findNavController
import com.example.photochainapp.databinding.FragmentFriendsBinding

class FriendsFragments : BaseFragment<FragmentFriendsBinding>(FragmentFriendsBinding::inflate) {
//    private lateinit var friendsAdapter: FriendsAdapter
//    private val friendsViewModel: FriendsViewModel by activityViewModels()
//    private val authViewModel: AuthViewModel by activityViewModels()
    override fun setUp() {

        binding.requestsBtn.setOnClickListener{
            view?.findNavController()?.navigate(FriendsFragmentsDirections.actionFriendsFragmentsToRequestsFragment())
        }
//        binding.recyclerviewFriends.layoutManager = LinearLayoutManager(requireContext())
//        friendsAdapter = FriendsAdapter()
//        binding.recyclerviewFriends.adapter = friendsAdapter
//        val userId = authViewModel.getCurrentUser()?.uid
//        if (!userId.isNullOrEmpty()) friendsViewModel.loadFriends(userId)
//
//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                friendsViewModel.friendsList.collectLatest { friends ->
//                    friendsAdapter.submitList(friends)
//                }
//            }
//        }
    }
}