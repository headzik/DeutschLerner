package fh.ooe.mcm.germanlehrner

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_flashcard.*
import java.util.*
import kotlin.collections.HashMap

class FlashcardActivity : AppCompatActivity() {

    private lateinit var wordList: ArrayList<Word>
    private lateinit var currentWord: Word
    private var numberOfWords: Int = 0

    private var correctAnswersCounter = 0
    private var wrongAnswersCounter = 0
    private var alreadyAnswered = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flashcard)

        wordList = intent.getParcelableArrayListExtra("wordsList")

        numberOfWords = wordList.count()

        displayNextWord()

        derButton.setOnClickListener { checkResult(it as Button) }
        dieButton.setOnClickListener { checkResult(it as Button) }
        dasButton.setOnClickListener { checkResult(it as Button) }
    }

    private fun displayNextWord() {
        if(wordList.isEmpty()) {
            val intent = Intent(this, StatisticsActivity::class.java)
            val scores = HashMap<Float, String>()
            scores[correctAnswersCounter.toFloat()] = "Correct"
            scores[wrongAnswersCounter.toFloat()] = "Incorrect"
            intent.putExtra("scores", scores)
            startActivity(intent)
            finish()
        } else {
            currentWord = pickUpNextWord()
            germanWordTextView.text = currentWord.germanWord
            englishWordTextView.text = currentWord.englishWord
        }
    }

    private fun pickUpNextWord(): Word {
        val random = Random()
        var index = random.nextInt(wordList.count())

        var word = wordList[index]
        wordList.removeAt(index)
        alreadyAnswered = false

        return word
    }

    private fun checkResult(articleButton: Button) {
        if(currentWord.article == articleButton.text.toString()) {
            if(!alreadyAnswered) correctAnswersCounter++
            makeToast(this, "Richtig!", Toast.LENGTH_SHORT)
            displayNextWord()
        } else {
            if(!alreadyAnswered) wrongAnswersCounter++
            makeToast(this, "Falsch!", Toast.LENGTH_SHORT)
            alreadyAnswered = true
        }
    }


    fun makeToast(context: Context, message: String, length: Int) {
        var toast = Toast.makeText(context, message, length)
        ((toast.view as LinearLayout).getChildAt(0) as TextView).gravity = Gravity.CENTER
        toast.show()

    }
}
