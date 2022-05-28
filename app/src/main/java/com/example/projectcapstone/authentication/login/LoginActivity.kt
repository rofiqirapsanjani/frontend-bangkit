package com.example.projectcapstone.authentication.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.ThemedSpinnerAdapter
import com.example.projectcapstone.R
import com.example.projectcapstone.authentication.register.RegisterActivity
import com.example.projectcapstone.databinding.ActivityLoginBinding
import com.example.projectcapstone.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            finish()
        }
        playAnimation()
    }

    private fun playAnimation() {
        val image = ObjectAnimator.ofFloat(binding.imageView, View.ALPHA, 1f).setDuration(500)
        val emailtv = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
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



    private fun showLoading() {
        loginViewModel.isLoading.observe(this) {
            binding.apply {
                if (it) progressBar.visibility = View.VISIBLE
                else progressBar.visibility = View.GONE
            }
        }
    }
}