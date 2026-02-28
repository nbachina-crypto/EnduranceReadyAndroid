package com.narenbachina.enduranceready.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.viewModelFactory
import com.narenbachina.enduranceready.data.HealthConnectManager
import com.narenbachina.enduranceready.data.HealthRepository
import com.narenbachina.enduranceready.data.HealthRepositoryImplementation
import com.narenbachina.enduranceready.model.ReadinessUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ReadinessViewModel(private val repository: HealthRepository,
): ViewModel() {

    /**
     * Backing Property Pattern
     * ------------------------
     * _uiState is mutable and can only be modified inside ViewModel(private variable).
     * uiState is exposed as immutable StateFlow to UI.
     *
     * This ensures:
     * - UI can only observe state and cannot modify state
     *
     * This preserves unidirectional data flow(UDF)
     *
     * Repository → ViewModel → UI
     */
    private val _uiState = MutableStateFlow(ReadinessUiState())
    val uiState:StateFlow<ReadinessUiState> = _uiState.asStateFlow()

    /**
     * init block-It is Called when ViewModel is created.
     *
     * We immediately load health data so that:
     * - UI shows loading first then real data appears.
     * This avoids requiring the UI to manually trigger data loading.
     */

            init{
                loadData()
            }

    /**
     * loadData()-Fetches sleep and heart rate from repository.
     * Why viewModelScope?
     * --------------------
     * - Tied to ViewModel lifecycle
     * - Automatically cancelled when ViewModel is cleared
     * - Prevents memory leaks
     * A ViewModelScope is defined for each ViewModel in your app.
     * Any coroutine launched in this scope is automatically canceled if the ViewModel is cleared.
     * Coroutines are useful here for when you have work that needs to be done only if the ViewModel is active.
     */
    private fun loadData(){
        viewModelScope.launch  {

            try {


                val weight=repository.getLatestWeight()
                _uiState.value=ReadinessUiState(isLoading = true)

                val sleep=repository.getSleepHours()
                val heartRate=repository.getRestingHeartRate()

                _uiState.value=ReadinessUiState(sleepHours = sleep,
                    restingHeartRate = heartRate,
                    isLoading = false,
                    userWeight = weight
                )


            }catch (e: Exception){
                _uiState.value=ReadinessUiState(isLoading = false
                    , error = e.message)
            }

        }
    }
}

/**
 * ReadinessViewModelFactory
 *
 * WHY THIS EXISTS:Default viewModel() only works when ViewModel has an empty constructor.
 *
 * Our ViewModel requires HealthRepository as a parameter
 * So we must tell Android HOW to create it.Factory pattern allows dependency injection into ViewModel.
 *
 * This keeps ViewModel:
 * - Testable
 * - Loosely coupled
 * - Replaceable (Fake → HealthConnect)
 */
class ReadinessViewModelFactory(
    private val repository: HealthRepository
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ReadinessViewModel(repository) as T
    }
}

