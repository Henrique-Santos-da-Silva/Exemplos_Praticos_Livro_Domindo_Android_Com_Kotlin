package dominando.android.hotel.repository.http

import android.util.Log
import com.google.android.gms.auth.api.Auth
import dominando.android.hotel.model.Hotel
import dominando.android.hotel.repository.HotelRepository
import dominando.android.hotel.repository.imagefiles.FindHotelPicture
import okhttp3.MultipartBody
import okhttp3.RequestBody

class HotelHttp(private val service: HotelHttpApi,
                private val repository: HotelRepository,
                private val pictureFinder: FindHotelPicture) {

    fun synchronizeWithServer() {
            sendPendingData()
            updateLocal()
    }

    private fun sendPendingData() {
        val pendingHotels = repository.pending()
        pendingHotels.forEach { hotel ->
            when(hotel.status) {
                Status.INSERT -> {
                    val result = service.insert(hotel).execute()
                    if (result.isSuccessful) {
                        hotel.serverId = result.body()?.id ?: 0
                        hotel.status = Status.OK
                        uploadHotelPhoto(hotel)
                        repository.update(hotel)
                    }
                }
                Status.DELETE -> {
                    val serverId = hotel.serverId ?: 0L
                    if (serverId != 0L) {
                        val result = service.delete(serverId).execute()
                        if (result.isSuccessful) {
                            repository.remove(hotel)
                        } else {
                            repository.remove(hotel)
                        }
                    }
                }

                Status.UPDATE -> {
                    val result = if (hotel.serverId == 0L) {
                        service.insert(hotel).execute()
                    } else {
                        service.update(hotel.serverId ?: 0, hotel).execute()
                    }
                    if (result.isSuccessful) {
                        hotel.serverId = result.body()?.id ?: 0
                        hotel.status = Status.OK
                        uploadHotelPhoto(hotel)
                        repository.update(hotel)
                    }
                }
            }
        }
    }

    private fun uploadHotelPhoto(hotel: Hotel) {
        if (hotel.photoUrl.isNotEmpty() && hotel.photoUrl.startsWith("content:")) {
            val execution = updateFile(hotel)
            when(execution) {
                is UploadResult -> {
                    Log.d("NGVL", "Upload efetuado com sucesso")
                }
                is NoUploadPerformed -> {
                    Log.e("NGVL", "Erro ao efetuar upload")
                }
            }
        }
    }

    private fun updateFile(hotel: Hotel): UploadExecution? {
        return try {
            val (sourceFile, mediaType) = pictureFinder.pictureFile(hotel)
            val toUpload = RequestBody.create(mediaType, sourceFile)
            val body = MultipartBody.Part.createFormData("hotel_photo",
                sourceFile.name, toUpload)
            val description = RequestBody.create(MultipartBody.FORM, hotel.serverId.toString())
            val response = service.uploadPhoto(description, body).execute()
            if (response.isSuccessful) hotel.photoUrl = "$BASE_URL${response.body()?.photoUrl}"
            response.body().let { it } ?: throw Throwable("Error at upload")

        } catch (error: Throwable) {
            NoUploadPerformed
        }
    }

    private fun updateLocal() {
        val response = service.listHotels().execute()
        if (response.isSuccessful) {
            val hotelsInServer = response.body()?.map { hotel ->
                hotel.apply {
                    val id = hotel.id
                    hotel.serverId = id
                    hotel.id = 0
                }
            }
            hotelsInServer?.forEach { hotel ->
                hotel.status = Status.OK
                val localHotel = repository.hotelByServerId(hotel.serverId ?: 0)
                if (localHotel == null) {
                    repository.insert(hotel)
                } else {
                    hotel.id = localHotel.id
                    repository.update(hotel)
                }
            }
        }
    }

    companion object {
        const val BASE_URL = "http://localhost:3333/hotel_service"
    }

}