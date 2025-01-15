package com.jabulani.ligiopen.ui.inapp.clubs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jabulani.ligiopen.data.db.DBRepository
import com.jabulani.ligiopen.data.db.model.UserAccount
import com.jabulani.ligiopen.data.network.ApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ClubsViewModel(
    private val apiRepository: ApiRepository,
    private val dbRepository: DBRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(ClubsScreenUiData())
    val uiState: StateFlow<ClubsScreenUiData> = _uiState.asStateFlow()

    private fun getClubs() {
        _uiState.update {
            it.copy(
                loadingStatus = LoadingStatus.LOADING
            )
        }
        viewModelScope.launch {
            try {
               val response = apiRepository.getClubs(
                   token = uiState.value.userAccount.token
               )

                if(response.isSuccessful) {
                    _uiState.update {
                        it.copy(
                            clubs = response.body()?.data!!,
                            loadingStatus = LoadingStatus.SUCCESS
                        )
                    }
                }

            } catch (e: Exception) {

            }
        }
    }

    fun getClubsScreenUiData() {
        viewModelScope.launch {
            while (uiState.value.userAccount.id == 0) {
                delay(1000)
            }
            getClubs()
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                dbRepository.getUsers().collect { users ->
                    _uiState.update {
                        it.copy(
                            userAccount = if(users.isNotEmpty()) users[0] else UserAccount(0, "", "", "", "", "", "")
                        )
                    }
                }
            }
        }
    }

    init {
        getUser()
        getClubsScreenUiData()
    }
}