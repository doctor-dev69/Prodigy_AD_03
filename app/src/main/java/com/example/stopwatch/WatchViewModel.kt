package com.example.stopwatch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WatchViewModel: ViewModel() {
//    private var _time = 0L
//    val time: Long
//        get() = _time
//
//    private var _isRunning = false
//    val isRunning: Boolean
//        get() = _isRunning
//
//    private var lastTime = 0L
//
//    fun start() {
//        if (!_isRunning) {
//            _isRunning = true
//            lastTime = System.currentTimeMillis()
//            viewModelScope.launch {
//                while (_isRunning) {
//                    delay(10)
//                    val currentTime = System.currentTimeMillis()
//                    _time += currentTime - lastTime
//                    lastTime = currentTime
//                }
//            }
//        }
//    }
//
//    fun pause() {
//        _isRunning = false
//    }
//
//    fun reset() {
//        _isRunning = false
//        _time = 0L
//    }
}
