package com.frag.weather.View

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import android.view.View.*
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.frag.weather.Location.Location
import com.frag.weather.R
import com.frag.weather.Util.UtilObj.getCurrentDate
import com.frag.weather.ViewModel.WeatherHomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WeatherHome: Fragment(R.layout.fragment_weather_home) {
    private val TAG = "WeatherHome"
    lateinit var viewModel : WeatherHomeViewModel
    @Inject
    lateinit var location: Location
    val action = WeatherHomeDirections.actionWeatherHomeToSearchWeather()
    val permissionRequest = WeatherHomeDirections.actionWeatherHomeToPermissionFragment()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(WeatherHomeViewModel::class.java)

        val launchPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ permissions ->
            when{
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION , false) ->{
                    location.getLocationUpdate(requireContext() , 0)
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION , false) -> {
                    location.getLocationUpdate(requireContext() , 1)
                }
                else -> {
                    view.findNavController().navigate(permissionRequest)
                }
            }
        }
        launchPermission.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION , Manifest.permission.ACCESS_COARSE_LOCATION))
        location.mutableLocations.observe(viewLifecycleOwner , Observer{
            if(it.get("0") != null && it.get("1") != null){
                viewModel.getWeather(it.get("0").toString() , it.get("1").toString())
                location.stopLocationListening()
            }
        })
        workLocation(view)
    }


    private fun workLocation(view : View){
        viewModel.loadingStatus.observe(viewLifecycleOwner , Observer{
            if(!it){
                view.findViewById<ProgressBar>(R.id.weather_home_loading_weather).visibility = INVISIBLE
                view.findViewById<LinearLayout>(R.id.weather_home_linearLayout).visibility = VISIBLE

            }else{
                view.findViewById<ProgressBar>(R.id.weather_home_loading_weather).visibility = VISIBLE
                view.findViewById<LinearLayout>(R.id.weather_home_linearLayout).visibility = GONE
            }
        })

        viewModel.weatherStatus.observe(viewLifecycleOwner){
            view.findViewById<TextView>(R.id.weather_home_country).text = it.sys.country
            view.findViewById<TextView>(R.id.weather_home_province).text = it.name
            view.findViewById<TextView>(R.id.weather_home_weather_status).text = it.weather.get(0).main.replaceFirstChar { c: Char -> c.titlecase() }
            view.findViewById<TextView>(R.id.weather_home_date).text = "${getCurrentDate}"
            view.findViewById<TextView>(R.id.weather_home_weather_status_name).text = it.weather.get(0).main
            view.findViewById<TextView>(R.id.weather_home_weather_status_temp).text = "${it.main.feels_like.toString()}°C"
            view.findViewById<TextView>(R.id.weather_home_weather_wind).text = it.wind.speed.toString()
            view.findViewById<ImageView>(R.id.weather_home_weather_wind_icon).rotation = it.wind.deg?.toFloat() ?: 0F
            val imageView = view.findViewById<ImageView>(R.id.weather_home_weather_image)
            Glide.with(requireContext())
                .load("https://openweathermap.org/img/wn/${it.weather.get(0).icon}.png")
                .into(imageView)
        }


        view.findViewById<BottomNavigationView>(R.id.weather_home_bottom_navigation_bar)
            .setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener{
                override fun onNavigationItemSelected(item: MenuItem): Boolean {
                    when(item.itemId){
                        R.id.search_button -> {
                            view.findNavController().navigate(action)
                        }
                    }
                    return true
                }
            })
    }
}
