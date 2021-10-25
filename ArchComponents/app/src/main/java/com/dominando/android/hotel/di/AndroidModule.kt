package com.dominando.android.hotel.di

import com.dominando.android.hotel.details.HotelDetailsViewModel
import com.dominando.android.hotel.form.HotelFormViewModel
import com.dominando.android.hotel.list.HotelListViewModel
import com.dominando.android.hotel.repository.HotelRepository
import com.dominando.android.hotel.repository.room.HotelDatabase
import com.dominando.android.hotel.repository.room.RoomRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val androidModule = module {
    single { this }
    single {
        RoomRepository(HotelDatabase.getDatabase(context = get())) as HotelRepository
    }

    viewModel {
        HotelListViewModel(repository = get())
    }

    viewModel {
        HotelDetailsViewModel(repository = get())
    }

    viewModel {
        HotelFormViewModel(repository = get())
    }
}