package com.jabulani.ligiopen.ui.inapp.news

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jabulani.ligiopen.data.db.DBRepository
import com.jabulani.ligiopen.data.db.model.variables.userAccountDt
import com.jabulani.ligiopen.data.network.ApiRepository
import com.jabulani.ligiopen.ui.inapp.clubs.LoadingStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsViewModel(
    private val apiRepository: ApiRepository,
    private val dbRepository: DBRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(NewsScreenUiData())
    val uiState: StateFlow<NewsScreenUiData> = _uiState.asStateFlow()

    fun setClubId(clubId: Int?) {
        _uiState.update {
            it.copy(
                clubId = clubId
            )
        }
    }

    fun getNews() {
//        _uiState.update {
//            it.copy(
//                loadingStatus = LoadingStatus.LOADING
//            )
//        }
        viewModelScope.launch {
            try {
                val response = apiRepository.getAllNews(
                    token = uiState.value.userAccount.token,
                    clubId = uiState.value.clubId
                )

                if(response.isSuccessful) {
                    _uiState.update {
                        it.copy(
                            news = response.body()?.data!!,
                            loadingStatus = LoadingStatus.SUCCESS
                        )
                    }
                } else {
                    Log.e("fetchNews", "response: $response")
                }

            } catch (e: Exception) {
                Log.e("fetchNews", "e: $e")

            }
        }
    }

    private fun loadUserData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                dbRepository.getUsers().collect {users ->
                    _uiState.update {
                        it.copy(
                            userAccount = userAccountDt.takeIf { users.isEmpty() } ?: users[0]
                        )
                    }
                }
            }
        }
    }

    fun resetStatus() {
        _uiState.update {
            it.copy(
                loadingStatus = LoadingStatus.INITIAL,
            )
        }
    }

    fun getInitialData() {
        viewModelScope.launch {
            while (uiState.value.userAccount.id == 0) {
                delay(1000)
            }
            getNews()
        }
    }

    init {
        loadUserData()
        getInitialData()
    }

}