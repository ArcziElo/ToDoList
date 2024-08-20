package wojtalak.arkadiusz.kurs.android.tasktodo.api


import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import wojtalak.arkadiusz.kurs.android.tasktodo.util.JsonConverter

object ServiceConfiguration {
 private val retrofit = Retrofit.Builder()
            .baseUrl("https://todoappbyaw-default-rtdb.europe-west1.firebasedatabase.app/")
            .addConverterFactory(MoshiConverterFactory.create(JsonConverter.moshi))
            .build()

        val taskService: TaskService = retrofit.create(TaskService::class.java)
    }

