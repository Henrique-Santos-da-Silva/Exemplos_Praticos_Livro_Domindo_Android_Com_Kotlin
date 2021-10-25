package com.dominando.android.hotel.di

import com.dominando.android.hotel.details.HotelDetailsPresenter
import com.dominando.android.hotel.details.HotelDetailsView
import com.dominando.android.hotel.form.HotelFormPresenter
import com.dominando.android.hotel.form.HotelFormView
import com.dominando.android.hotel.list.HotelListPresenter
import com.dominando.android.hotel.list.HotelListView
import com.dominando.android.hotel.repository.HotelRepository
import com.dominando.android.hotel.repository.sqlite.SQLiteRepository
import org.koin.dsl.module

val androidModule = module {
    single { this }
    single {
        SQLiteRepository(ctx = get()) as HotelRepository
    }

    factory { (view: HotelListView) ->
        HotelListPresenter(
            view,
            repository = get()
        )
    }

    factory { (view: HotelDetailsView) ->
        HotelDetailsPresenter(
            view,
            repository = get()
        )
    }

    factory { (view: HotelFormView) ->
        HotelFormPresenter(
            view,
            repository = get()
        )
    }

}