package com.frag.weather.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frag.weather.Interface.RetrofitInterfaces
import com.frag.weather.Model.SearchModel
import com.frag.weather.Model.WeatherModel
import com.frag.weather.Util.UtilObj.API_KEY
import com.frag.weather.View.SearchWeather
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import javax.inject.Inject

@HiltViewModel
class SearchWeatherViewModel @Inject constructor(private val retrofit: Retrofit) : ViewModel() {
    val mutableLiveDataWeather = MutableLiveData<SearchModel>()
    val liveDataWeather : LiveData<SearchModel>
    get() = mutableLiveDataWeather

    fun getSearch(search : String){
        viewModelScope.async {
            val getData = retrofit.create(RetrofitInterfaces::class.java)
                .getSearch(search , "3c7c7fa5da2ec76d599bae0fca61d1af" , "metric")
            mutableLiveDataWeather.postValue(getData)
        }
    }
}