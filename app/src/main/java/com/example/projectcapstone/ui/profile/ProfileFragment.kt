package com.example.projectcapstone.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.projectcapstone.databinding.FragmentProfileBinding
import com.example.projectcapstone.ui.mystore.MyStoreActivity
import com.example.projectcapstone.ui.orderhistory.OrderHistoryActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val hrofileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnOrderHistory.setOnClickListener {
            val intent = Intent(activity, OrderHistoryActivity::class.java)
            startActivity(intent)
        }

        binding.btnMyStore.setOnClickListener {
            val i = Intent(activity, MyStoreActivity::class.java)
            startActivity(i)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
