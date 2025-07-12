//package com.jaeckel.androidportal
//
//import android.widget.Toast
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.jaeckel.trueblocks.BloomProgressCallback
//import com.jaeckel.trueblocks.IndexProgressCallback
//import com.jaeckel.trueblocks.ManifestResponse
//import com.jaeckel.trueblocks.Trueblocks
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//
//class TrueblocksViewModel : ViewModel() {
//    private val trueblocks = Trueblocks()
//    private val _initializationState = MutableStateFlow<InitializationState>(InitializationState.NotStarted)
//    val initializationState: StateFlow<InitializationState> = _initializationState.asStateFlow()
//
//    fun initializeTrueblocks() {
//        viewModelScope.launch {
//            val result = trueblocks.initialize(
//                progressCallback = object : BloomProgressCallback {
//                    override fun onManifestFetched(manifestCID: String, manifestResponse: ManifestResponse) {
//                        _initializationState.value = InitializationState.ManifestLoaded(manifestResponse)
//                    }
//
//                    override fun onProgressBloomUpdate(blockRangeFetched: IntRange) {
//                        _initializationState.value = InitializationState.LoadingBloomFilters(blockRangeFetched)
//                    }
//
//                    override fun onCompletion(x: Int, ofy: Int) {
//                        _initializationState.value = InitializationState.Progress(x, ofy)
//                    }
//
//                    override fun onError(error: String) {
//                        _initializationState.value = InitializationState.Error(error, isRetryable = true)
//                    }
//
//                    override fun onFailure(message: String) {
//                        _initializationState.value = InitializationState.Error(message, isRetryable = false)
//                    }
//                }
//            )
//        }
//    }
//
//    fun queryAddress(address: String) {
//        viewModelScope.launch {
//            trueblocks.queryByAddress(
//                address = address,
//                progressCallback = object : IndexProgressCallback {
//                    override fun onProgressIndexUpdate(blockRangeFetched: IntRange) {
//                        // Update UI with progress
//
//
//                    }
//
//                    override fun onComplete() {
//                        // Query complete
//                    }
//
//                    override fun onError(error: String) {
//                        // Handle error
//                    }
//                }
//            ).collect { record ->
//                // Handle each appearance record as it comes
//            }
//        }
//    }
//}
//
//sealed class InitializationState {
//    object NotStarted : InitializationState()
//    data class ManifestLoaded(val manifest: ManifestResponse) : InitializationState()
//    data class LoadingBloomFilters(val currentRange: IntRange) : InitializationState()
//    data class Progress(val current: Int, val total: Int) : InitializationState()
//    data class Error(val message: String, val isRetryable: Boolean) : InitializationState()
//    object Complete : InitializationState()
//}
