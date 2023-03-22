package com.bitwisearts.android.quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import com.bitwisearts.android.quizapp.ui.QuizViewGrid
import com.bitwisearts.android.quizapp.ui.theme.QuizAppTheme

/**
 * The [ComponentActivity] that represents the main screen of the quiz app.
 */
class MainActivity : ComponentActivity()
{
	@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContent {
			QuizAppTheme {
				Surface(
					modifier = Modifier.fillMaxSize(),
					color = MaterialTheme.colorScheme.background)
				{
					QuizViewGrid(
						calculateWindowSizeClass(this).heightSizeClass)
				}
			}
		}
	}
}