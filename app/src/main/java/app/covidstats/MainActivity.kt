package app.covidstats

import android.R
import android.os.Bundle
import android.widget.Toolbar
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.Color
import app.covidstats.ui.MainWindow


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO -> uncomment this line to format search bar
        setContent {
            MainWindow(this)
        }
    }
}