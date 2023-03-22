package com.bitwisearts.android.quizapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bitwisearts.android.quizapp.model.Question
import com.bitwisearts.android.quizapp.ui.theme.QuizAppTheme

/**
 * Draw an answer option on the board.
 *
 * @param key
 *   The [Question]'s answer key for this answer.
 * @param answer
 *   The answer text.
 * @param selected
 *   The presently selected question key that reflects which answer is selected.
 * @param displayResult
 *   `true` indicates the result of the question being answered was submitted
 *   and should be displayed; `false` otherwise.
 * @param correctAnswer
 *   The key to the correct answer to the question.
 * @param fontSize
 *   The size in [TextUnit] of the answer text to display.
 * @param onClickAction
 *   The action to perform when this [AnswerCard] is clicked.
 */
@Composable
fun RowScope.AnswerCard(
	key: String,
	answer: String,
	selected: String,
	displayResult: Boolean,
	correctAnswer: String,
	fontSize: TextUnit,
	onClickAction: () -> Unit)
{
	val border =
		when
		{
			selected == key && displayResult  && correctAnswer != key ->
				Color.Red
			displayResult && correctAnswer == key -> Color.Green
			selected == key -> Color.Blue
			else -> Color.Transparent
		}
	Box(
		modifier = Modifier
			.padding(16.dp)
			.weight(4f)
			.aspectRatio(1f)
			.fillMaxSize()
			.background(Color(0xFFD9D8D9))
			.clickable { onClickAction() }
			.border(2.dp, border),
		contentAlignment = Alignment.Center)
	{
		Text(
			text = answer,
			modifier = Modifier.padding(8.dp),
			fontSize = fontSize,
			color = Color(0xFF498B74),
			textAlign = TextAlign.Center)
	}
}

/**
 * Development [Composable] preview screen for the [AnswerCard].
 */
@Preview(showBackground = true)
@Composable
fun DefaultPreview()
{
	QuizAppTheme {
		Row {
			AnswerCard(
				"a",
				"This is the answer",
				"a",
				false,
				"a",
				18.sp
			) {}
		}
	}
}