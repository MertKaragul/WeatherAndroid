package com.frag.weather.View

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.frag.weather.R
import com.frag.weather.SearchAdapter
import com.frag.weather.Util.UtilObj
import com.frag.weather.Util.UtilObj.API_KEY
import com.frag.weather.ViewModel.SearchWeatherViewModel
import com.frag.weather.ViewModel.WeatherHomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import retrofit2.Retrofit
import javax.inject.Inject

@AndroidEntryPoint
class SearchWeather : Fragment(R.layout.fragment_search_weather) {
    private val TAG = "SearchWeather"
    var job : Job?=null
    lateinit var viewModel: SearchWeatherViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(SearchWeatherViewModel::class.java)

        view.findViewById<LinearLayout>(R.id.weather_search_linearLayout).visibility = View.GONE
        view.findViewById<EditText>(R.id.search_view_edit_text).addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch{
                delay(1000)
                it?.let {
                    if(it.toString().isNotEmpty()){
                        viewModel.getSearch(it.toString())
                    }
                }
            }
        }
        viewModel.liveDataWeather.observe(viewLifecycleOwner , Observer {
            if(it != null){
                view.findViewById<LinearLayout>(R.id.weather_search_linearLayout).visibility = View.VISIBLE
                view.findViewById<TextView>(R.id.weather_search_province).text = it.name
                view.findViewById<TextView>(R.id.weather_search_date).text = UtilObj.getCurrentDate.toString()
                view.findViewById<TextView>(R.id.weather_search_weather_status).text = it.weather.get(0).main
                view.findViewById<TextView>(R.id.weather_search_country).text = it.sys.country
                view.findViewById<TextView>(R.id.weather_search_weather_status_name).text = it.weather.get(0).main
                view.findViewById<TextView>(R.id.weather_search_weather_status_temp).text = "${it.main.feels_like.toString()}°C"
                view.findViewById<TextView>(R.id.weather_search_weather_wind).text = it.wind.speed.toString()
                view.findViewById<ImageView>(R.id.weather_search_weather_wind_icon).rotation = it.wind.deg?.toFloat() ?: 0F
                val imageView = view.findViewById<ImageView>(R.id.weather_search_weather_image)
                Glide.with(requireContext())
                    .load("https://openweathermap.org/img/wn/${it.weather.get(0).icon}.png")
                    .into(imageView)
            }else{
                view.findViewById<LinearLayout>(R.id.weather_search_linearLayout).visibility = View.GONE
            }
        })
    }
}