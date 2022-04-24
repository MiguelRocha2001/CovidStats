package app.covidstats

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import app.covidstats.ui.MainWindow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO -> uncomment this line to format search bar
        /*
        window.setFlags(
            WindowManager.LayoutParams.TYPE_STATUS_BAR,
            WindowManager.LayoutParams.TYPE_STATUS_BAR
        )

         */
        setContent {
            MainWindow(this)
        }
    }
}