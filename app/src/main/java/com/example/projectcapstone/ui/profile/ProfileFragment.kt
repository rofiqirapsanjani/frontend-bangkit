package com.example.projectcapstone.ui.profile

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.projectcapstone.R
import com.example.projectcapstone.authentication.login.LoginActivity
import com.example.projectcapstone.databinding.FragmentProfileBinding
import com.example.projectcapstone.model.UserPreference
import com.example.projectcapstone.ui.mystore.MyStoreActivity
import com.example.projectcapstone.viewmodel.MainViewModel
import com.example.projectcapstone.viewmodel.ViewModelUserFactory
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var emailUser : String
    private lateinit var nameuser : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val profileViewModel =
//            ViewModelProvider(this).get(ProfileViewModel::class.java)
        mainViewModel = ViewModelProvider(this,
            ViewModelUserFactory(UserPreference.getInstance(requireActivity().dataStore))
        )[MainViewModel::class.java]
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val userPref = UserPreference.getInstance(requireActivity().dataStore)
        lifecycleScope.launch {
            emailUser = userPref.getPreferences()[UserPreference.EMAIL_KEY].toString()
            nameuser = userPref.getPreferences()[UserPreference.NAME_KEY].toString()
        }
        binding.apply {
            binding.tvDetailName.text = nameuser
            binding.tvDetailLocation.text = emailUser
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

