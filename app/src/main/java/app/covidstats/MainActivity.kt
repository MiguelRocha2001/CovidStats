package app.covidstats

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import app.covidstats.Ui.MainWindow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainWindow(this)
        }
    }
}

/* TODO ->
    add support for multiple countries;
    Format stat numbers like covid cases so the number is easier to read.
 */