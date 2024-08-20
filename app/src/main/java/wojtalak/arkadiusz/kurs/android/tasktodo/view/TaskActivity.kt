package wojtalak.arkadiusz.kurs.android.tasktodo.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import wojtalak.arkadiusz.kurs.android.tasktodo.model.ColorType
import wojtalak.arkadiusz.kurs.android.tasktodo.model.Task


class TaskActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskView()
        }
    }

    @Composable
    fun TaskView() {
        val context = LocalContext.current
        val taskColors = ColorType.values()
        var currentColor by remember { mutableStateOf(taskColors.random()) }
        var selectedColor by remember { mutableStateOf<Color?>(null) }
        var titleText by remember { mutableStateOf("") }
        var descriptionText by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
                .padding(20.dp)

        ) {
            Text(
                text = "Task:",
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .background(Color.LightGray, CircleShape)
                    .border(3.dp, shape = CircleShape, color = Color.DarkGray)
                    .align(Alignment.CenterHorizontally)

                    .wrapContentSize(),
                textAlign = TextAlign.Center,
                textDecoration = TextDecoration.Underline,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = currentColor.color),
                elevation = CardDefaults.cardElevation(defaultElevation = 20.dp)
            ) {
                OutlinedTextField(
                    value = titleText,
                    onValueChange = { titleText = it },
                    label = {
                        Column(verticalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Title")
                            Icon(
                                imageVector = Icons.Default.AccountBox,
                                contentDescription = "account box icon"
                            )
                        }
                    },
                    textStyle = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(9.dp)
                )

                OutlinedTextField(
                    value = descriptionText,
                    onValueChange = { descriptionText = it },
                    label = {
                        Column(verticalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Description")
                            Icon(
                                imageVector = Icons.Default.Create,
                                contentDescription = "create icon"
                            )
                        }
                    },
                    textStyle = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(9.dp)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            LazyRow {
                items(items = taskColors) { colorType ->
                    Button(
                        onClick = {
                            currentColor = colorType
                            selectedColor = colorType.color
                        },
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .size(40.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorType.color,
                            contentColor = colorType.color
                        ),
                        shape = CircleShape,
                        elevation = ButtonDefaults.elevatedButtonElevation(10.dp),
                        border = BorderStroke(
                            2.dp,
                            if (currentColor == colorType) Color.DarkGray else colorType.color
                        ),

                        ) {

                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
            Button(
                onClick = {
                    val task = Task(
                        title = titleText,
                        description = descriptionText,
                        colorType = currentColor,

                        )
                    val intent = Intent(context, HomeActivity::class.java)
                    intent.putExtra("task", task)
                    startActivity(intent)
                    finish()
                    Toast.makeText(context,"Task created!", Toast.LENGTH_LONG).show()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Save")
            }
        }
    }
}


