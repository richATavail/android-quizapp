package com.bitwisearts.android.quizapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

/**
 * Represents a single question in a quiz.
 *
 * @author Richard Arriaga
 *
 * @property question
 *   The text question to be asked.
 * @property correctAnswer
 *   The key into into [answers] that represents the correct answer.
 * @property answers
 *   The [LinkedHashMap] from answer key option to answer option. Using
 *   [LinkedHashMap] preserves the order of the questions as presented in the
 *   backing JSON file.
 */
@Suppress("OPT_IN_USAGE")
@Serializable
@Parcelize
data class Question constructor(
	val question: String,
	@JsonNames("correct_answer") val correctAnswer: String,
	val answers: LinkedHashMap<String, String>) : Parcelable
{
	/**
	 * Check the proposed answer against the [correctAnswer].
	 *
	 * @param proposedAnswer
	 *   The [answers] key to check if the answer is correct.
	 * @return
	 *   `true` if the answer is correct; `false` otherwise.
	 */
	fun checkAnswer (proposedAnswer: String): Boolean =
		correctAnswer == proposedAnswer
}

/**
 * Represents an entire quiz that can be taken.
 *
 * @property questions
 *   The list of [Question]s to be asked.
 */
@Serializable
data class Quiz constructor(val questions: List<Question>)
{
	/**
	 * The number of [Question]s in this [Quiz].
	 */
	val questionCount: Int get() = questions.size

	/**
	 * Answer the [Question] at the given index.
	 *
	 * @param index
	 *   The index into [questions] for the [Question] to get.
	 * @return
	 *   The corresponding question.
	 */
	operator fun get (index: Int): Question = questions[index]
}

