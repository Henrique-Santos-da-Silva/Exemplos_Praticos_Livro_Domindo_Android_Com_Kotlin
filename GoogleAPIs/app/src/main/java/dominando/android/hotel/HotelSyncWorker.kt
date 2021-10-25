package dominando.android.hotel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.work.*
import dominando.android.hotel.repository.http.HotelHttp
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import java.lang.Exception

class HotelSyncWorker(context: Context,
                      workerParams: WorkerParameters): Worker(context, workerParams), KoinComponent {

    override fun doWork(): Result {
        val hotelHttp: HotelHttp by inject()
        return try {
            hotelHttp.synchronizeWithServer()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    companion object {
       fun start(): LiveData<WorkInfo> {
           val workManager = WorkManager.getInstance()
           val constants = Constraints.Builder()
               .setRequiredNetworkType(NetworkType.CONNECTED)
               .build()
           val request = OneTimeWorkRequest.Builder(HotelSyncWorker::class.java)
               .setConstraints(constants)
               .build()
           workManager.enqueue(request)
           return workManager.getWorkInfoByIdLiveData(request.id)
       }
    }
}