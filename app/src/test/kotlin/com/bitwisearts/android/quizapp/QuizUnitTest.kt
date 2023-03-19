package com.bitwisearts.android.quizapp

import com.bitwisearts.android.quizapp.model.Question
import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests of quiz functionality
 */
class QuizUnitTest
{
	@Test
	fun checkAnswerCorrectTest()
	{
		assert(question1.checkAnswer("b"))
		{
			"Expected \"b\" to be the correct answer to question 1 but it " +
				"claims it is not"
		}
		assert(question2.checkAnswer("c"))
		{
			"Expected \"c\" to be the correct answer to question 2 but it " +
				"claims it is not"
		}
		assert(question3.checkAnswer("a"))
		{
			"Expected \"a\" to be the correct answer to question 3 but it " +
				"claims it is not"
		}
	}

	@Test
	fun checkAnswerIncorrectTest()
	{
		// Using `assertEquals` instead of `assertFalse` to get better failure
		// reporting
		assertEquals(
			"Question 1 indicated incorrect answer was correct",
			false,
			question1.checkAnswer("b"))
		assertEquals(
			"Question 2 indicated incorrect answer was correct",
			false,
			question2.checkAnswer("a"))
		assertEquals(
			"Question 3 indicated incorrect answer was correct",
			false,
			question3.checkAnswer("b"))
	}

	companion object
	{
		/** Test [Question] 1. */
		val question1 = Question(
			"This is question 1",
			"b",
			LinkedHashMap<String, String>().apply {
				this["a"] = "not the answer"
				this["b"] = "the answer"
				this["c"] = "also not the answer"
				this["d"] = "again, not the answer"
			})

		/** Test [Question] 2. */
		val question2 = Question(
			"This is question 1",
			"c",
			LinkedHashMap<String, String>().apply {
				this["a"] = "not the answer"
				this["b"] = "also not the answer"
				this["c"] = "the answer"
				this["d"] = "again, not the answer"
			})

		/** Test [Question] 3. */
		val question3 = Question(
			"This is question 1",
			"a",
			LinkedHashMap<String, String>().apply {
				this["a"] = "the answer"
				this["b"] = "not the answer"
				this["c"] = "also not the answer"
				this["d"] = "again, not the answer"
			})
	}
}