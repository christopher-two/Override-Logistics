package org.override.docs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import org.override.docs.presentation.navigation.NavigationApp
import org.override.docs.presentation.theme.DocsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DocsTheme(
                content = {
                    NavigationApp(
                        navController = rememberNavController()
                    )
                }
            )
        }
    }
}