package com.canhbbaochayrung

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.canhbbaochayrung.services.NasaService
import com.canhbbaochayrung.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val nasaService: NasaService
) : ViewModel() {
    val fireState = mutableStateOf<State<List<FirePoint>>>(State.Loading())
    val fireLiveData = MutableLiveData<State<List<FirePoint>>>()
    var range = 1
    var date = Calendar.getInstance().time.toString("yyyy-MM-dd")

    fun changeDate(date: Date) {
        this.date = date.toString("yyyy-MM-dd")
        Timber.d(">> date = $date")
    }

    fun loadDataFromNasa() {
        viewModelScope.launch {
            try {
//                fireState.value = State.Loading()
                fireLiveData.postValue(State.Loading())
                val data = nasaService.getData(range, date).get("data").asString
                val rows = data.split("\n")
                val fires = arrayListOf<FirePoint>()
                rows.forEachIndexed { index, row ->
                    if (index == 0) return@forEachIndexed
                    val rowValues = row.split(",")
                    val fire = FirePoint(rowValues)
                    fires.add(fire)
                }
//                fireState.value = State.Success(fires)
                fireLiveData.postValue(State.Success(fires))
            } catch (e: Exception) {
//                fireState.value = State.Failed(e)
                fireLiveData.postValue(State.Failed(e))
            }
        }
    }
}