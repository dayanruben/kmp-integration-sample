package com.jetbrains.simplelogin.androidapp.ui.login

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jetbrains.simplelogin.androidapp.R
import com.jetbrains.simplelogin.androidapp.data.LoginDataSource
import com.jetbrains.simplelogin.androidapp.data.LoginDataValidator
import com.jetbrains.simplelogin.androidapp.data.LoginRepository

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                val loginViewModel = viewModel {
                    LoginViewModel(
                        loginRepository = LoginRepository(
                            dataSource = LoginDataSource()
                        ),
                        dataValidator = LoginDataValidator()
                    )
                }

                Surface() {
                    LoginScreen(
                        viewModel = loginViewModel,
                        onLoginSuccess = {
                            // Show welcome message
                            val successResult = it.success
                            successResult?.let {
                                val welcome = getString(R.string.welcome)
                                Toast.makeText(
                                    applicationContext,
                                    "$welcome ${it.displayName}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                            // Complete the login process
                            setResult(Activity.RESULT_OK)
                            finish()
                        }
                    )
                }
            }
        }
    }
}
