package com.dominando.android.hotel.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dominando.android.hotel.repository.sqlite.COLUMN_ID
import com.dominando.android.hotel.repository.sqlite.TABLE_HOTEL

@Entity(tableName = TABLE_HOTEL)
data class Hotel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    var id: Long = 0,
    var name: String = "",
    var address: String = "",
    var rating: Float = 0.0f
) {
    override fun toString(): String = name
}