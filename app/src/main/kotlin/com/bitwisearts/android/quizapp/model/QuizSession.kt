package com.bitwisearts.android.quizapp.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * An [Iterator] of [Question]s that represents instance of a user taking a
 * [Quiz].
 *
 * @author Richard Arriaga
 *
 * @property quiz
 *   The quiz being taken.
 */
data class QuizSession constructor(val quiz: Quiz): Iterator<Question>
{
	/**
	 * The array of booleans, each position corresponding to the ordered
	 * [quiz] [questions][Quiz.questions]. `true` indicates the questions was
	 * answered correctly; `false` otherwise.
	 *
	 * **NOTE** *The array is initialized to all false values.*
	 */
	private val scoreCard = Array(quiz.questionCount) { false }

	/**
	 * The index of [quiz]'s next [Quiz.questions] to be presented.
	 */
	private var nextQuestion = 0

	/** Provide the number of correct answers. */
	val correct: Int get() =
		scoreCard.filter { it }.map{ 1 }.sumOf { it }

	/** The percent correct with the decimal portion truncated. */
	val score: String get() = (correct * 100 / quiz.questionCount).toString()

	/**
	 * `true` indicates the test is complete; `false` otherwise.
	 */
	private val _isComplete = MutableLiveData(false)

	/**
	 * `true` indicates the test is complete; `false` otherwise.
	 */
	val isComplete: LiveData<Boolean> get() = _isComplete

	/**
	 * Update the [scoreCard] for the given question.
	 *
	 * @param proposedAnswer
	 *   The proposed answer to the current [Question].
	 * @return
	 *   The [Pair] of whether the answer was correct (`true` for yes; `false`
	 *   for no) and the [Question.correctAnswer] key.
	 */
	fun updateScoreCard (proposedAnswer: String): Pair<Boolean, String>
	{
		if (nextQuestion <= quiz.questionCount)
		{
			val index = nextQuestion - 1
			return quiz[index].let {
				Pair(it.checkAnswer(proposedAnswer), it.correctAnswer).apply {
					scoreCard[index] = first
					if (!hasNext())
					{
						_isComplete.value = true
					}
				}
			}
		}
		else
		{
			throw IllegalStateException(
				"Attempted to answer question but all questions have been " +
					"answered!").apply {
						Log.e(
							"QuizSession",
							"An attempt was made to update the score " +
								"card but the quiz is complete!",
							this)
			}
		}
	}

	/**
	 * Reset this [QuizSession] so that it can be retaken.
	 */
	fun reset ()
	{
		nextQuestion = 0
		scoreCard.fill(false)
		_isComplete.value = false
	}

	override fun hasNext(): Boolean = nextQuestion < quiz.questionCount

	override fun next(): Question =
		if (hasNext())
		{
			quiz[nextQuestion++]
		}
		else
		{
			throw NoSuchElementException(
				"QuizSession has no more questions!").apply {
					Log.e(
						"QuizSession",
						"Attempted to get another question from quiz " +
							"session but all questions answered!",
					this)
			}
		}
}