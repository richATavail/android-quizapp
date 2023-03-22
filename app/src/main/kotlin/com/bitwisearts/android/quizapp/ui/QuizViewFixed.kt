package com.bitwisearts.android.quizapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bitwisearts.android.quizapp.QuizViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bitwisearts.android.quizapp.R
import com.bitwisearts.android.quizapp.model.QuizSession
import com.bitwisearts.android.quizapp.ui.components.AnswerCard

/**
 * The view that represents the [QuizSession] currently in progress. This is a
 * straight forward implementation simply using two [Row]s, each containing two
 * [AnswerCard]s. This approach is inflexible as it always expects four answer
 * options for each question.
 *
 * @param windowHeightSizeClass
 *   The [WindowHeightSizeClass] of the current device.
 * @param model
 *   The [QuizViewModel] that manages the state of the [QuizSession].
 */
@Composable
fun QuizViewFixed (
	windowHeightSizeClass: WindowHeightSizeClass,
	model: QuizViewModel = viewModel())
{
	// Indicates whether the quiz session is complete.
	val isComplete by model.isComplete.observeAsState(false)

	// The RelativeTextSizesGroup for the device used to take the quiz. We
	// use remember to avoid re-creating relative size on recomposition. We
	// do want this to be re-run if the configuration changes.
	val relativeSize by remember {
		mutableStateOf(RelativeTextSizesGroup.getAppropriateSizes(
			windowHeightSizeClass))
	}

	Column(
		modifier = Modifier.padding(30.dp),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally)
	{
		if (isComplete)
		{
			val wrongAnswers =
				model.quizSession.quiz.questionCount - model.quizSession.correct
			if (wrongAnswers < 4)
			{
				val medal =
					when (wrongAnswers)
					{
						0 -> "\uD83E\uDD47"
						1 -> "\uD83E\uDD48"
						else -> "\uD83E\uDD49"
					}
				Row {
					Text(text = medal, fontSize = relativeSize.awardSize)
				}
			}
			Row {
				Text(
					text = "${model.quizSession.score}%",
					fontSize = relativeSize.resultPercentSize)
			}
			Row (Modifier.padding(vertical = 15.dp))
			{
				Text(
					text =
						stringResource(id = R.string.result_fmt).format(
							model.quizSession.correct,
							model.quizSession.quiz.questionCount,
							model.quizSession.score),
					fontSize = relativeSize.resultCorrectCountSize)
			}
			Button(
				colors = ButtonDefaults.buttonColors(
					containerColor = Color(0xFF1A444F),
					contentColor = Color(0xFF72DF89),
					disabledContainerColor = Color.Gray,
					disabledContentColor = Color.LightGray),
				onClick = { model.retryQuiz() })
			{
				Text(
					text = stringResource(id = R.string.retry),
					modifier = Modifier.padding(
						vertical = 5.dp, horizontal = 8.dp),
					fontSize = relativeSize.buttonTextSize)
			}
		}
		else
		{
			var currentQuestion by rememberSaveable {
				mutableStateOf(model.quizSession.next())
			}
			var selectedAnswer by rememberSaveable { mutableStateOf("") }
			var displayResult by rememberSaveable { mutableStateOf(false) }
			val answers = currentQuestion.answers.toList()

			Row (Modifier.weight(1f))
			{
				Text(
					text = stringResource(id = R.string.android_quiz),
					fontSize = relativeSize.headerSize,
					fontWeight = FontWeight.Bold,
					color = Color(0xFF27515B))
			}
			Row (
				modifier = Modifier.weight(3f).padding(bottom = 15.dp),
				verticalAlignment = Alignment.Bottom)
			{
				Spacer(modifier = Modifier.weight(1f))
				Text(
					text = currentQuestion.question,
					modifier = Modifier.weight(4f),
					fontSize = relativeSize.questionSize,
					lineHeight = relativeSize.questionLineHeight,
					color = Color(0xFF4C7780),
					textAlign = TextAlign.Center)
				Spacer(modifier = Modifier.weight(1f))
			}
			Row (Modifier.weight(5f))
			{
				val answerA = answers[0]
				val answerB = answers[1]
				Spacer(modifier = Modifier.weight(1f))
				AnswerCard(
					key = answerA.first,
					answer = answerA.second,
					selected = selectedAnswer,
					displayResult = displayResult,
					correctAnswer = currentQuestion.correctAnswer,
					fontSize = relativeSize.answerSize)
				{
					selectedAnswer = answerA.first
				}
				AnswerCard(
					key = answerB.first,
					answer = answerB.second,
					selected = selectedAnswer,
					displayResult = displayResult,
					correctAnswer = currentQuestion.correctAnswer,
					fontSize = relativeSize.answerSize)
				{
					selectedAnswer = answerB.first
				}
				Spacer(modifier = Modifier.weight(1f))
			}
			Row (Modifier.weight(5f))
			{
				val answerC = answers[2]
				val answerD = answers[3]
				Spacer(modifier = Modifier.weight(1f))
				AnswerCard(
					key = answerC.first,
					answer = answerC.second,
					selected = selectedAnswer,
					displayResult = displayResult,
					correctAnswer = currentQuestion.correctAnswer,
					fontSize = relativeSize.answerSize)
				{
					selectedAnswer = answerC.first
				}
				AnswerCard(
					key = answerD.first,
					answer = answerD.second,
					selected = selectedAnswer,
					displayResult = displayResult,
					correctAnswer = currentQuestion.correctAnswer,
					fontSize = relativeSize.answerSize)
				{
					selectedAnswer = answerD.first
				}
				Spacer(modifier = Modifier.weight(1f))
			}
			Row (Modifier.weight(2f).padding(vertical = 15.dp))
			{
				val clickAction =
					if (displayResult)
					{

						{
							displayResult = false
							selectedAnswer = ""
							if (model.quizSession.hasNext())
							{
								currentQuestion = model.quizSession.next()
							}
						}
					}
					else
					{
						{
							displayResult = true
							model.quizSession.updateScoreCard(selectedAnswer)
						}
					}
				Button(
					colors = ButtonDefaults.buttonColors(
						containerColor = Color(0xFF1A444F),
						contentColor = Color(0xFF72DF89),
						disabledContainerColor = Color.Gray,
						disabledContentColor = Color.LightGray),
					onClick = { clickAction() },
					enabled = displayResult || selectedAnswer.isNotBlank())
				{
					Text(
						text =
							if (displayResult)
							{
								stringResource(id = R.string.next)
							}
							else
							{
								stringResource(id = R.string.submit)
							},
						modifier = Modifier.padding(
							vertical = 5.dp, horizontal = 8.dp),
						fontSize = relativeSize.buttonTextSize)
				}
			}
		}
	}
}