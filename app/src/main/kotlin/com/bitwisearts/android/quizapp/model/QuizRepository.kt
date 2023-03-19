package com.bitwisearts.android.quizapp.model

/**
 * The repository for storing quizzes.
 */
object QuizRepository
{
	/**
	 * The map of currently available quiz names to the respective quiz.
	 */
	val quizzes = mutableMapOf<String, Quiz>()

	/**
	 * Answer the [Quiz] for the given quiz name.
	 *
	 * @param quizName
	 *   The name of the [Quiz] to retrieve.
	 * @return
	 *   The associated [Quiz] or `null` if not found.
	 */
	operator fun get(quizName: String): Quiz? = quizzes[quizName]

	/**
	 * The name of the only quiz currently available.
	 */
	const val ANDROID_QUIZ = "Android Quiz"
}