package com.canhbbaochayrung.utils

sealed class State<T> {
    class Loading<T>(val isRefresh: Boolean = false): State<T>()
    class Success<T>(val data: T): State<T>()
    class Failed<T>(val error: Exception): State<T>()
}