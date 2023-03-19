package com.bitwisearts.android.quizapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bitwisearts.android.quizapp.QuizViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bitwisearts.android.quizapp.R
import com.bitwisearts.android.quizapp.model.QuizSession
import com.bitwisearts.android.quizapp.ui.components.AnswerCard

/**
 * The view that represents the [QuizSession] currently in progress.
 *
 * @param windowHeightSizeClass
 *   The [WindowHeightSizeClass] of the current device.
 * @param model
 *   The [QuizViewModel] that manages the state of the [QuizSession].
 */
@Composable
fun QuizView (
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

/**
 * This interface declares state that is used to adjust the text size of various
 * components in [QuizView] based on the [WindowHeightSizeClass].
 */
interface RelativeTextSizesGroup
{
	/** The [TextUnit] size of the unicode medal character if one is awarded. */
	val awardSize: TextUnit

	/** The [TextUnit] size of the final score as a percent. */
	val resultPercentSize: TextUnit

	/** The [TextUnit] size of the final score as a ratio of correct answers. */
	val resultCorrectCountSize: TextUnit

	/** The [TextUnit] size of the answer option text. */
	val answerSize: TextUnit

	/** The [TextUnit] size of the question text. */
	val questionSize: TextUnit

	/** The [TextUnit] size of the question line height. */
	val questionLineHeight: TextUnit

	/** The [TextUnit] size of the test name header text. */
	val headerSize: TextUnit

	/** The [TextUnit] size of the button text. */
	val buttonTextSize: TextUnit

	companion object
	{
		/**
		 * Answer the appropriate [RelativeTextSizesGroup] for the given
		 * [WindowHeightSizeClass].
		 *
		 * @param windowHeightSizeClass
		 *   The [WindowHeightSizeClass] of the current device.
		 * @return
		 *   The appropriate [RelativeTextSizesGroup] used to size the text.
		 */
		fun getAppropriateSizes (
			windowHeightSizeClass: WindowHeightSizeClass
		): RelativeTextSizesGroup =
			when (windowHeightSizeClass)
			{
				WindowHeightSizeClass.Compact -> CompactRelativeTextSizesGroup
				WindowHeightSizeClass.Medium -> MediumRelativeTextSizesGroup
				else -> ExpandedRelativeTextSizesGroup
			}
	}
}

/**
 * The [RelativeTextSizesGroup] for
 * [compact devices][WindowWidthSizeClass.Compact].
 */
object CompactRelativeTextSizesGroup: RelativeTextSizesGroup
{
	override val awardSize: TextUnit = 35.sp
	override val resultPercentSize: TextUnit = 30.sp
	override val resultCorrectCountSize: TextUnit = 20.sp
	override val answerSize: TextUnit = 12.sp
	override val questionSize: TextUnit = 16.sp
	override val questionLineHeight: TextUnit = 20.sp
	override val headerSize: TextUnit = 12.sp
	override val buttonTextSize: TextUnit = 14.sp
}

/**
 * The [RelativeTextSizesGroup] for
 * [medium devices][WindowWidthSizeClass.Medium].
 */
object MediumRelativeTextSizesGroup: RelativeTextSizesGroup
{
	override val awardSize: TextUnit = 40.sp
	override val resultPercentSize: TextUnit = 35.sp
	override val resultCorrectCountSize: TextUnit = 26.sp
	override val answerSize: TextUnit = 14.sp
	override val questionSize: TextUnit = 20.sp
	override val questionLineHeight: TextUnit = 24.sp
	override val headerSize: TextUnit = 14.sp
	override val buttonTextSize: TextUnit = 16.sp
}

/**
 * The [RelativeTextSizesGroup] for
 * [expanded devices[WindowWidthSizeClass.Expanded].
 */
object ExpandedRelativeTextSizesGroup: RelativeTextSizesGroup
{
	override val awardSize: TextUnit = 60.sp
	override val resultPercentSize: TextUnit = 45.sp
	override val resultCorrectCountSize: TextUnit = 36.sp
	override val answerSize: TextUnit = 18.sp
	override val questionSize: TextUnit = 30.sp
	override val questionLineHeight: TextUnit = 34.sp
	override val headerSize: TextUnit = 18.sp
	override val buttonTextSize: TextUnit = 22.sp
}

