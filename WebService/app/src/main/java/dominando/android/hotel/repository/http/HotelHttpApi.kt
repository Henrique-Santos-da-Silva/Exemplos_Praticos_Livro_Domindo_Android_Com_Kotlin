package dominando.android.hotel.repository.http

import dominando.android.hotel.model.Hotel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface HotelHttpApi {
    @GET("")
    fun listHotels(): Call<List<Hotel>>

    @GET("$WEB_SERVICE/{hotelId}")
    fun hotelById(@Path("hotelId") id: Long): Call<Hotel>

    @POST("$WEB_SERVICE")
    fun insert(@Body hotel: Hotel): Call<IdResult>

    @PUT("$WEB_SERVICE/{hotelId}")
    fun update(@Path("hotelId") id: Long, @Body hotel: Hotel): Call<IdResult>

    @DELETE("$WEB_SERVICE/{hotelId}")
    fun delete(@Path("hoteleId") id: Long): Call<IdResult>

    @Multipart
    @POST(UPLOAD)
    fun uploadPhoto(@Part("id") hotelId: RequestBody,
                    @Part file: MultipartBody.Part): Call<UploadResult>


    companion object{
        const val WEB_SERVICE = "http://localhost:3333/hotel_service"
        const val UPLOAD = ""
    }
}