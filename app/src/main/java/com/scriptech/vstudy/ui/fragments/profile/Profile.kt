package com.scriptech.vstudy.ui.fragments.profile

//import com.scriptech.vstudy.repository.MyCallback
//import com.scriptech.vstudy.repository.trendingBooksList
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.firebase.auth.FirebaseAuth
import com.scriptech.vstudy.R
import com.scriptech.vstudy.adapters.BooksAdapter
import com.scriptech.vstudy.adapters.VideosAdapter
import com.scriptech.vstudy.adapters.sliderAdapter
import com.scriptech.vstudy.database.BooksDatabase
import com.scriptech.vstudy.database.VideosDatabase
import com.scriptech.vstudy.databinding.FragProfileBinding
import com.scriptech.vstudy.model.sliderModel
import com.scriptech.vstudy.repository.BooksRepository
import com.scriptech.vstudy.repository.VideosRepository
import com.scriptech.vstudy.ui.activities.mainActivity1.MainActivity
import kotlinx.coroutines.launch
import kotlin.math.abs

class Profile : Fragment() {
    private var _binding: FragProfileBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requireActivity().getWindow().statusBarColor = Color.parseColor("#20111111")
                requireActivity().getWindow().decorView.systemUiVisibility = (
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getUserInfo(FirebaseAuth.getInstance().currentUser!!.uid)
            }
        }

        viewModel.userInfo?.observe(viewLifecycleOwner){
            binding.ProfileName.text = it.name
            binding.ProfileEmail.text = it.email
            binding.ProfilePhoneno.text = it.number
        }

        binding.btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        return root
    }
}