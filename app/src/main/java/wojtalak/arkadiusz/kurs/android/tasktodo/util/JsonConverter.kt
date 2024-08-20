package wojtalak.arkadiusz.kurs.android.tasktodo.util

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import wojtalak.arkadiusz.kurs.android.tasktodo.model.Task


object JsonConverter {
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()



    fun taskListToJson(taskList: List<Task>): String {

        val type = Types.newParameterizedType(List::class.java, Task::class.java)
        val jsonAdapter: JsonAdapter<List<Task>> = moshi.adapter(type)

        return jsonAdapter.toJson(taskList)
    }
    fun taskFromJson(json: String): Task? {
        val type = Task::class.java
        return moshi.adapter<Task>(type).fromJson(json)
    }

    fun taskListJson(taskList: List<Task>): String {
        val type = Types.newParameterizedType(List::class.java, Task::class.java)
        return moshi.adapter<List<Task>>(type).toJson(taskList)
    }
    fun  taskListFromJson(json:String): List<Task>? {
        val type = Types.newParameterizedType(List::class.java, Task::class.java)
        return moshi.adapter<List<Task>>(type).fromJson(json) ?: emptyList()
    }

    }


