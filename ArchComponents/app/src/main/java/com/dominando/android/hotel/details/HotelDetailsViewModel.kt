package com.dominando.android.hotel.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dominando.android.hotel.model.Hotel
import com.dominando.android.hotel.repository.HotelRepository

class HotelDetailsViewModel(private val repository: HotelRepository) : ViewModel() {
    fun loadHotelDetails(id: Long): LiveData<Hotel> {
        return repository.hotelById(id)
    }
}