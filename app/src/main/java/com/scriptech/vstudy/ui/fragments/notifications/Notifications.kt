package com.scriptech.vstudy.ui.fragments.notifications

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.scriptech.vstudy.adapters.NotificationsAdapter
import com.scriptech.vstudy.databinding.FragNotificationsBinding
import com.scriptech.vstudy.repository.UserRepository
import kotlinx.coroutines.launch

class Notifications : Fragment() {
    private var _binding: FragNotificationsBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: NotificationsViewModel

    private lateinit var notificationsAdapter: NotificationsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requireActivity().window.statusBarColor = Color.parseColor("#20111111")
                requireActivity().window.decorView.systemUiVisibility = (
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
            }
        }

        val userRepository = UserRepository()

        val viewModelFactory = NotificationsViewModelFactory(userRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[NotificationsViewModel::class.java]

        notificationsAdapter = NotificationsAdapter()

        binding.rvNotification.apply {
            adapter = notificationsAdapter
            hasFixedSize()
            layoutManager = LinearLayoutManager(activity)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                notificationsAdapter.differ.submitList(viewModel.getAllNotifications())
            }
        }

        return root
    }
}