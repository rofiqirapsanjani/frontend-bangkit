package com.example.projectcapstone.authentication.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.ThemedSpinnerAdapter
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.projectcapstone.MainActivity
import com.example.projectcapstone.R
import com.example.projectcapstone.authentication.register.RegisterActivity
import com.example.projectcapstone.databinding.ActivityLoginBinding
import com.example.projectcapstone.helper.Helper
import com.example.projectcapstone.model.UserModel
import com.example.projectcapstone.model.UserPreference
import com.example.projectcapstone.network.response.ResultResponse
import com.example.projectcapstone.viewmodel.LoginViewModel
import com.example.projectcapstone.viewmodel.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editTextListener()
        buttonListener()
        setMyButtonEnable()
        playAnimation()
    }

    private fun playAnimation() {
        val image = ObjectAnimator.ofFloat(binding.imageView, View.ALPHA, 1f).setDuration(500)
        val emailtv = ObjectAnimator.ofFloat(binding.emailEditText, View.ALPHA, 1f).setDuration(500)
        val email = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val passtv = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
        val pass = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(500)
        val haveacc = ObjectAnimator.ofFloat(binding.tvAlreadyHaveAccount, View.ALPHA, 1f).setDuration(500)
        val toreg = ObjectAnimator.ofFloat(binding.toRegister, View.ALPHA, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(emailtv, email, passtv, pass, login, haveacc, toreg)
        }


        AnimatorSet().apply {
            playSequentially(image, together)
            start()
        }

    }

//    private fun setupViewModel() {
//        loginViewModel = ViewModelProvider(
//            this,
//            ViewModelFactory(UserPreference.getInstance(dataStore))
//        )[LoginViewModel::class.java]
//    }


    private fun editTextListener() {
        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(s: Editable) {
            }
        })

        binding.toRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            finish()
        }
    }

    private fun setMyButtonEnable() {
        val resultPassword = binding.passwordEditText.text
        val resultEmail = binding.emailEditText.text

        binding.loginButton.isEnabled = resultPassword != null && resultEmail != null &&
                binding.passwordEditText.text.toString().length >= 5
    }

    private fun showAlertDialog(param: Boolean, message: String) {
        if (param) {
            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.information))
                setMessage(getString(R.string.login_ok))
                setPositiveButton(getString(R.string.lanjut)) { _, _ ->
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)

                }
                create()
                show()
            }
        } else {
            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.information))
                setMessage(getString(R.string.login_fail) + ", $message")
                setPositiveButton(getString(R.string.lanjut)) { _, _ ->
                    binding.progressBar.visibility = View.GONE
                }
                create()
                show()

            }
        }
    }

    private fun buttonListener() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val pass = binding.passwordEditText.text.toString()

            loginViewModel.login(email, pass).observe(this) {
                when (it) {
                    is ResultResponse.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is ResultResponse.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val user = UserModel(
                            it.data.name,
                            email,
                            pass,
                            it.data.userId,
                            it.data.token,
                            true
                        )
                        showAlertDialog(true, getString(R.string.login_ok))

                        val userPref = UserPreference.getInstance(dataStore)
                        lifecycleScope.launchWhenStarted {
                            userPref.saveUser(user)
                        }
                    }
                    is ResultResponse.Error -> {
                        binding.progressBar.visibility = View.GONE
                        showAlertDialog(false, it.error)

                    }
                }
            }
        }
    }
}