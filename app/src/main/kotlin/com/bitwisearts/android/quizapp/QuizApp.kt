package com.bitwisearts.android.quizapp

import android.app.Application
import com.bitwisearts.android.quizapp.model.Quiz
import com.bitwisearts.android.quizapp.model.QuizRepository
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString

/**
 * The [Application] that represents the Quiz app.
 *
 * @author Richard Arriaga
 */
class QuizApp: Application()
{
	override fun onCreate()
	{
		super.onCreate()
		// Here we simulate populating the QuizRepository with Quizzes only
		// once. Quiz retrieval whether it be from the cloud or from the local
		// SQLite DB would determine the manner in which the repository is
		// populated.

		// Extract the raw JSON quiz file from the raw resources folder.
		val quizFile = String(
			resources.openRawResource(R.raw.android_quiz).readBytes())
		val quiz = Json.decodeFromString<Quiz>(quizFile)

		// Store the quiz in the repository
		QuizRepository.quizzes[QuizRepository.ANDROID_QUIZ] = quiz
	}
}