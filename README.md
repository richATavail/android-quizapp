Android Interview At-home Assignment
================================================================================

Make a simple Android "quiz" app.  Spend 2-3 hours on it, and stop.  It is OK if 
the app is not complete or perfect - part of the exercise is to see where you 
stop (if you did not finish) and what parts you chose to prioritize. We can then 
discuss those decisions and what you would have done with more time.  

## Implementation Details
1. On launch of the app, parse the JSON (included later on this page) and 
present the first question and available answers.
2. Answers should be presented in a 2x2 grid, and not in a vertical list.
3. If you are comfortable With Jetpack Compose for your UI, please use that. 
4. Provide a “selected” visual state for an answer when tapped (like a colored
stroke around the answer, as an example).  If there was already a selected 
answer, the prior answer should be deselected and the new answer selected. 
5. When pressing the submit button, provide an alert or some indication if the 
answer was correct or not. 
6. Then move on to the next question.  Repeat until all questions have been 
answered. 
7. If you have time and are feeling fancy, provide a final screen indicating 
overall score from the quiz and a button to start over.


## Delivery
Provide your recruitment contact with either: A link to a public github(or 
similar) repo for your assignment, or a .zip file containing your full 
assignment.

--------------------------------------------------------------------------------

## Post Submittal Observations
After submitting this as an assignment I reviewed the work I completed in the
allotted time and came up with the following observations of things I missed,
things I'd do differently if given more time, as well as some rationale behind
my decisions. I waited to commit these observations until after the accompanying
interview. The initial submission, without these observations and code changes
can be found on Github under the tag,
[initial-submission](https://github.com/richATavail/android-quizapp/tree/initial-submission).

1. I neglected to add [`aspectRatio`](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier#(androidx.compose.ui.Modifier).aspectRatio(kotlin.Float,kotlin.Boolean)) 
to [AnswerCard](app/src/main/kotlin/com/bitwisearts/android/quizapp/ui/components/AnswerCard.kt). 
Doing so with input `1f` would have forced the `AnswerCard` to be square like 
the mockup provided with the assignment instructions. 
2. I developed the view to be a strict, inflexible presentation in 
[`QuizViewFixed`](app/src/main/kotlin/com/bitwisearts/android/quizapp/ui/QuizViewFixed.kt). 
This was done to quickly build the view in the allotted time. To enable more 
flexibility in the presentation of the `Question` to support questions with 
varying numbers of answers (*not just 4*), I should have used 
[`LazyVerticalGrid`](https://developer.android.com/reference/kotlin/androidx/compose/foundation/lazy/grid/package-summary#LazyVerticalGrid(androidx.compose.foundation.lazy.grid.GridCells,androidx.compose.ui.Modifier,androidx.compose.foundation.lazy.grid.LazyGridState,androidx.compose.foundation.layout.PaddingValues,kotlin.Boolean,androidx.compose.foundation.layout.Arrangement.Vertical,androidx.compose.foundation.layout.Arrangement.Horizontal,androidx.compose.foundation.gestures.FlingBehavior,kotlin.Boolean,kotlin.Function1))
(*introduced in compose in version 1.2.0*) with the columns set to `GridCells.Fixed(count = 2)`. Doing so reduces the 
amount of code and simplifies the `Composable` as can be seen in my reimplementation in
[QuizViewGrid](app/src/main/kotlin/com/bitwisearts/android/quizapp/ui/QuizViewGrid.kt).
3. Initial work using [`WindowSizeClass`](https://developer.android.com/reference/kotlin/androidx/compose/material3/windowsizeclass/WindowSizeClass)
was completed to provide more flexibility to address different form factors 
(*e.g. phone, tablet, foldable, etc*) and reconfiguration upon device rotation. 
Given time constraints, this work was discontinued and the application was 
locked in portrait mode in `AndroidManifest.xml`. The `WindowSizeClass` work was 
still used to provide adaptive font sizes relative to the device type. Given more 
time, I would have put more work into making the application more 
[adaptive](https://developer.android.com/jetpack/compose/layouts/adaptive) in
both orientation (*portrait and landscape*) and screen sizes. 
4. Nothing was changed in the code generated theme package at app creation in
Android Studio. Colors/Styles were applied right on the affected `Composables`.
Theme work should be part of any Android app. Given more time, proper development
of the theme would have taken place.
5. The actual quiz data was provided as a [JSON file](app/src/main/res/raw/android_quiz.json).
It was loaded from disk straight into the [`QuizRepository`](app/src/main/kotlin/com/bitwisearts/android/quizapp/model/QuizRepository.kt) 
on the main thread in `onCreate` of 
[`QuizApp`](app/src/main/kotlin/com/bitwisearts/android/quizapp/QuizApp.kt).
It is more probable that the source of data would be made available from an 
external source retrieved using a network API call. In this case, the retrieval 
of quiz data and loading it in `QuizRepository` should be done asynchronously, 
using coroutines. An observable flag that indicates whether quiz data is present 
could be used to force a recomposition of the starting view to a screen that 
allows for quiz selection once quizzes have been downloaded.
