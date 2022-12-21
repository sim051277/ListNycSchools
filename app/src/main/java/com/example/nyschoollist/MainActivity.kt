package com.example.nyschoollist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.nyschoollist.ui.theme.NYSchoolListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NYSchoolListTheme {
                AppNavigation()
            }
        }
    }
}
