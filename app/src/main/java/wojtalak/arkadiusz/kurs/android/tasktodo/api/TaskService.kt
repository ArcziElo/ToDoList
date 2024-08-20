package wojtalak.arkadiusz.kurs.android.tasktodo.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import wojtalak.arkadiusz.kurs.android.tasktodo.model.Task
import wojtalak.arkadiusz.kurs.android.tasktodo.view.HomeActivity

interface TaskService {

    @POST("tasks.json")
    suspend fun add(@Body task: HomeActivity)
    @GET("tasks.json")
    suspend fun getAll(): Map<String, Task>

}