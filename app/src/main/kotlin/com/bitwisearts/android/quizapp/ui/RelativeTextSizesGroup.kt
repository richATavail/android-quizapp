package com.bitwisearts.android.quizapp.ui

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

/**
 * This interface declares state that is used to adjust the text size of various
 * components in [QuizViewFixed] based on the [WindowHeightSizeClass].
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