package com.example.projectcapstone.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.projectcapstone.R
import com.example.projectcapstone.authentication.login.LoginActivity
import com.example.projectcapstone.databinding.FragmentProfileBinding
import com.example.projectcapstone.viewmodel.MainViewModel

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var mainViewModel: MainViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val hrofileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        binding.toLogout.setOnClickListener{
//            mainViewModel.logout()
//            val i = Intent (this@ProfileFragment.requireContext(), LoginActivity::class.java)
//            startActivity(i)
//        }

        binding.toLogout.setOnClickListener {
            mainViewModel.logout()
            AlertDialog.Builder(this.requireContext()).apply {
                setTitle(getString(R.string.information))
                setMessage(getString(R.string.logout))
                setPositiveButton(getString(R.string.lanjut)) { _, _ ->
                    startActivity(Intent(this@ProfileFragment.requireContext(), LoginActivity::class.java))
                }
                create()
                show()
            }
        }

        return root


    }


//    private fun buttonListener() {
//        binding.toLogout.setOnClickListener {
//            mainViewModel.logout()
//            val moveToListStoryActivity = Intent(this@ProfileFragment, LoginActivity::class.java)
//            startActivity(moveToListStoryActivity)
//        }
//    }



    override fun onDestroyView() {
        super.onDestroyView()
        binding
    }
}
