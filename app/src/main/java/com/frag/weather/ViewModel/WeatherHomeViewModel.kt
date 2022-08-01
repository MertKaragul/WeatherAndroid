package com.frag.weather.ViewModel

import android.Manifest
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.frag.weather.Interface.RetrofitInterfaces
import com.frag.weather.Model.WeatherModel
import com.frag.weather.Util.UtilObj.API_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Inject


@HiltViewModel
class WeatherHomeViewModel @Inject constructor(private val retrofit : Retrofit)  : ViewModel() {
    private val TAG = "WeatherHomeViewModel"
    private val getWeatherStatus = MutableLiveData<WeatherModel>()
    private var disposable = CompositeDisposable()
    val weatherStatus : LiveData<WeatherModel>
     get() = getWeatherStatus
    var errorStatus = MutableLiveData<Boolean>(false)
    var loadingStatus = MutableLiveData<Boolean>(true)

    fun getWeather(latitude : String , longitude : String){
        disposable.add(
            retrofit.create<Retr   ofitInterfaces>()
                .getOneCall(latitude.toString() , longitude.toString(), "3c7c7fa5da2ec76d599bae0fca61d1af" , "metric")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<WeatherModel>(){
                    override fun onSuccess(t: WeatherModel) {
                        getWeatherStatus.value = t
                        loadingStatus.postValue(false)
                        errorStatus.postValue(false)
                    }
                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        errorStatus.postValue(true)
                    }
            })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}