package dominando.android.hotel.fcm

import android.content.Context
import android.preference.PreferenceManager
import dominando.android.hotel.repository.http.HotelHttp

class TokenManager(private val context: Context, private val hotelHttp: HotelHttp) {
    fun updateToken(refreshedToken: String) {
        setRegistationId(refreshedToken)
        setSentToServer(false)
        sendRegistrationToServer()
    }

    private fun sendRegistrationToServer() {
        TODO()
    }

    fun getRegistationId(): String? {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(REGISTRATION_ID, null)
    }

    private fun setRegistationId(value: String?) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(REGISTRATION_ID, value).apply()
    }

    private fun isSentToServer(): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(SENT_TO_SERVER, false)
    }

    private fun setSentToServer(value: Boolean) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(SENT_TO_SERVER, value).apply()
    }

    companion object {
        private const val REGISTRATION_ID = "registrationId"
        private const val SENT_TO_SERVER = "hasSentToServer"
    }
}