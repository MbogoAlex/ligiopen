package com.jabulani.ligiopen

import android.window.SplashScreenView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jabulani.ligiopen.ui.auth.login.LoginViewModel
import com.jabulani.ligiopen.ui.auth.registration.RegistrationViewModel
import com.jabulani.ligiopen.ui.start.SplashViewModel

object AppViewModelFactory {
    val Factory = viewModelFactory {
        initializer {
            SplashViewModel(
                dbRepository = ligiopenApplication().container.dbRepository
            )
        }

        initializer {
            RegistrationViewModel(
                apiRepository = ligiopenApplication().container.apiRepository,
                dbRepository = ligiopenApplication().container.dbRepository
            )
        }

        initializer {
            LoginViewModel(
                apiRepository = ligiopenApplication().container.apiRepository,
                dbRepository = ligiopenApplication().container.dbRepository,
                savedStateHandle = this.createSavedStateHandle()
            )
        }
    }
}

fun CreationExtras.ligiopenApplication(): Ligiopen =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Ligiopen)