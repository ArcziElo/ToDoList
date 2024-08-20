package wojtalak.arkadiusz.kurs.android.tasktodo.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import kotlinx.coroutines.runBlocking
import wojtalak.arkadiusz.kurs.android.tasktodo.api.ServiceConfiguration
import wojtalak.arkadiusz.kurs.android.tasktodo.api.TaskNetworkRepository
import wojtalak.arkadiusz.kurs.android.tasktodo.model.Task
import wojtalak.arkadiusz.kurs.android.tasktodo.util.StorageOperations

var taskList = mutableListOf<Task>()
val taskNetworkRepository = TaskNetworkRepository(ServiceConfiguration.taskService)


class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        getAllTasksViaNetwork()

        val task = intent.getSerializableExtra("task") as? Task
        task?.let {
             taskList.add(it)
            StorageOperations.writeTaskList(this, taskList)

            addTaskViaNetwork(task)
        }

        setContent {
        HomeView()
        }
    }
    private fun getAllTasksViaNetwork(){
        runBlocking {
            try {
                taskList = taskNetworkRepository.getAllTasks().toMutableList()
                StorageOperations.writeTaskList(this@HomeActivity, taskList)
            }catch (e: Exception)
            {
                Log.e("HomeActivity", "Error fetching tasks $e", e)
                taskList = StorageOperations.readTaskList(this@HomeActivity).toMutableList()
                Toast.makeText(this@HomeActivity, "Tasks loaded from local storage", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun addTaskViaNetwork(task: Task?){

        runBlocking {
            try {
                taskNetworkRepository.addTask(this@HomeActivity)
            }catch (e: Exception){
                Log.e("HomeActivity", "Error adding task $e", e)
                Toast.makeText(this@HomeActivity, "Network add tasks: $e", Toast.LENGTH_LONG).show()
            }
        }
    }

    @Composable
    fun TaskListView(){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
                .padding(horizontal = 10.dp)
        ) {
            Text(
                text = "Task List:",
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .border(3.dp, shape = CircleShape, color = Color.DarkGray)
                    .background(Color.Gray, CircleShape)
                    .align(Alignment.CenterHorizontally)
                    .wrapContentSize(),
                textAlign = TextAlign.Center,

                )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(items = taskList){ task ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = task.colorType.color),
                        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Column {
                            Text(
                                text = task.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                text = task.description,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun HomeView(){
        val context = LocalContext.current


        Box(
            modifier = Modifier.fillMaxSize()
        ){
            if(taskList.isEmpty()){
                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Text(
                        text = "Empty task list :( ",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.W300,
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                            .background(Color.Gray, RoundedCornerShape(10.dp))
                            .wrapContentSize(),

                        )

                }
            }else TaskListView()

            FloatingActionButton(
                onClick = {
                    val intent = Intent(context, TaskActivity::class.java)
                    startActivity(context, intent,null)

                    Toast.makeText(context,"Add new task..",Toast.LENGTH_LONG).show()
                },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomEnd)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "add icon")
            }
        }
    }


}


