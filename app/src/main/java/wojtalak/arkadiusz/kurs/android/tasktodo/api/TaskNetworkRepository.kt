package wojtalak.arkadiusz.kurs.android.tasktodo.api

import wojtalak.arkadiusz.kurs.android.tasktodo.model.Task
import wojtalak.arkadiusz.kurs.android.tasktodo.view.HomeActivity

class TaskNetworkRepository(private val taskService: TaskService)  {

   suspend fun addTask(task: HomeActivity){
       taskService.add(task)
   }
    suspend fun getAllTasks(): List<Task> {
    return taskService.getAll().values.toList()
    }

}