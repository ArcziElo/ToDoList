package wojtalak.arkadiusz.kurs.android.tasktodo.util

import android.annotation.SuppressLint
import android.content.Context
import wojtalak.arkadiusz.kurs.android.tasktodo.model.Task

object StorageOperations {
    @SuppressLint("CommitPrefEdits")
    fun writeTaskList(context: Context, taskList: List<Task>) {

        val json = JsonConverter.taskListToJson(wojtalak.arkadiusz.kurs.android.tasktodo.view.taskList)
        val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE).edit()
        sharedPreferences.putString("TASK_LIST_KEY",json)
        sharedPreferences.apply()
    }
    fun readTaskList(context: Context):List<Task>{

        val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("TASK_LIST_KEY",null)
        return if(json != null){
            JsonConverter.taskListFromJson(json) ?: emptyList()
        }
        else{
            emptyList()
        }
    }

    private const val SHARED_PREFERENCES_KEY = "TASK_SHARED_PREFS"
    private const val TASK_LIST_KEY = "TASK_LIST_KEY"
}