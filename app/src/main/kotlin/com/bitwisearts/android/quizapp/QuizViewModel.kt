package com.bitwisearts.android.quizapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bitwisearts.android.quizapp.model.Quiz
import com.bitwisearts.android.quizapp.model.QuizRepository
import com.bitwisearts.android.quizapp.model.QuizRepository.ANDROID_QUIZ
import com.bitwisearts.android.quizapp.model.QuizSession

/**
 * The [ViewModel] that manages the state for the current [QuizSession].
 *
 * @author Richard Arriaga
 *
 * @property quizSession
 *   The [quizSession] for the [Quiz] being taken by the user.
 */
class QuizViewModel constructor(
	val quizSession: QuizSession =
		QuizSession(QuizRepository[ANDROID_QUIZ]
			?: throw IllegalStateException(
				"Quiz, $ANDROID_QUIZ should exist!").apply {
					Log.e(
						"QuizViewModel",
						"Could not locate quiz, $ANDROID_QUIZ",
						this)
			})
): ViewModel()
{
	/**
	 * `true` indicates the test is complete; `false` otherwise.
	 */
	val isComplete: LiveData<Boolean> get() = quizSession.isComplete

	/**
	 * Restart the [QuizSession].
	 */
	fun retryQuiz ()
	{
		quizSession.reset()
	}
}