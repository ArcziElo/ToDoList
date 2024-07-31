package wojtalak.arkadiusz.kurs.android.tasktodo.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import wojtalak.arkadiusz.kurs.android.tasktodo.model.Task

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val task = intent.getSerializableExtra("task") as? Task
        task?.let {
            Toast.makeText(this, "Title: ${it.title}, Description: ${it.description}", Toast.LENGTH_SHORT).show()
        }

        setContent {
        HomeView()
        }
    }
}

@Composable
fun HomeView(){
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize()
    ){
FloatingActionButton(
    onClick = {
        val intent = Intent(context, TaskActivity::class.java)
        startActivity(context, intent, null)
    },
    modifier = Modifier
        .padding(16.dp)
        .align(Alignment.BottomEnd)
) {
Icon(imageVector = Icons.Default.Add, contentDescription = "add icon")
}
    }
}