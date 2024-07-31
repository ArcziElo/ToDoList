package wojtalak.arkadiusz.kurs.android.tasktodo.model

import androidx.compose.ui.graphics.Color
import java.io.Serializable
import java.util.UUID

data class Task(
    val title: String = "",
    val description: String = "",
    val colorType: ColorType,
    val id: String = UUID.randomUUID().toString()

) : Serializable
enum class ColorType(val color: Color){
   YELLOW(Color.Yellow),
   CYAN(Color.Cyan),
   LIGHT_GRAY(Color.LightGray),
   GREEN(Color.Green)


}