package com.jabulani.ligiopen

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jabulani.ligiopen.ui.auth.login.LoginViewModel
import com.jabulani.ligiopen.ui.auth.registration.RegistrationViewModel
import com.jabulani.ligiopen.ui.inapp.clubs.ClubDetailsViewModel
import com.jabulani.ligiopen.ui.inapp.clubs.ClubsViewModel
import com.jabulani.ligiopen.ui.inapp.clubs.PlayerDetailsViewModel
import com.jabulani.ligiopen.ui.inapp.home.HomeViewModel
import com.jabulani.ligiopen.ui.inapp.profile.ProfileViewModel
import com.jabulani.ligiopen.ui.start.SplashViewModel

object AppViewModelFactory {
    val Factory = viewModelFactory {

        initializer {
            MainActivityViewModel(
                dbRepository = ligiopenApplication().container.dbRepository
            )
        }

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

        initializer {
            ProfileViewModel(
                dbRepository = ligiopenApplication().container.dbRepository
            )
        }

        initializer {
            HomeViewModel(
                dbRepository = ligiopenApplication().container.dbRepository
            )
        }

        initializer {
            ClubsViewModel(
                apiRepository = ligiopenApplication().container.apiRepository,
                dbRepository = ligiopenApplication().container.dbRepository
            )
        }

        initializer {
            ClubDetailsViewModel(
                apiRepository = ligiopenApplication().container.apiRepository,
                dbRepository = ligiopenApplication().container.dbRepository,
                savedStateHandle = this.createSavedStateHandle()
            )
        }

        initializer {
            PlayerDetailsViewModel(
                apiRepository = ligiopenApplication().container.apiRepository,
                dbRepository = ligiopenApplication().container.dbRepository,
                savedStateHandle = this.createSavedStateHandle()
            )
        }
    }
}

fun CreationExtras.ligiopenApplication(): Ligiopen =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Ligiopen)