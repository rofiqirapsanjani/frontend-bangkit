package com.example.projectcapstone.ui.profile

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.projectcapstone.R
import com.example.projectcapstone.authentication.login.LoginActivity
import com.example.projectcapstone.databinding.FragmentProfileBinding
import com.example.projectcapstone.ui.mystore.MyStoreActivity
import com.example.projectcapstone.ui.orderhistory.OrderHistoryActivity
import com.example.projectcapstone.viewmodel.MainViewModel

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var mainViewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.apply {
            btnOrderHistory.setOnClickListener {
                val intent = Intent(activity, OrderHistoryActivity::class.java)
                startActivity(intent)
            }

            btnMyStore.setOnClickListener {
                val i = Intent(activity, MyStoreActivity::class.java)
                startActivity(i)
            }
        }

        binding.btnLogout.setOnClickListener {
            mainViewModel.logout()
            AlertDialog.Builder(this@ProfileFragment.requireContext()).apply {
                setTitle(getString(R.string.information))
                setMessage(getString(R.string.logout_succes))
                setPositiveButton(getString(R.string.lanjut)) { _, _ ->
                    val i = Intent(this@ProfileFragment.requireContext(), LoginActivity::class.java)
                    startActivity(i)
                }
                create()
                show()
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding
    }
}
