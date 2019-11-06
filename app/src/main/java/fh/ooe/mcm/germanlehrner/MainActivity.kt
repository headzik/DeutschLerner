package fh.ooe.mcm.germanlehrner

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import fh.ooe.mcm.germanlehrner.R.string.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val SEEK_BAR_STEP = 50
    }

    private var wordsList: ArrayList<Word> = arrayListOf()
    private var numberOfWords = 0
    private lateinit var lines: List<String>
    private var numberOfAllWords = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        readLinesFromFile()

        infotTextView.text = getString(infoText).format(lines.size)

        numberOfWordsSeekBar.max = numberOfAllWords/SEEK_BAR_STEP
        if(lines.size%SEEK_BAR_STEP == 0) {  numberOfWordsSeekBar.max-- }
        numberOfWords = SEEK_BAR_STEP
        number.text = SEEK_BAR_STEP.toString()

        numberOfWordsSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                numberOfWords = SEEK_BAR_STEP*(progress+1)
                if(numberOfWords > numberOfAllWords) { numberOfWords = numberOfAllWords }
                number.text = numberOfWords.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
              //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })

        confirmButton.setOnClickListener {
            takeChosenNumberOfWords()
            moveToFlashcardActivity()
        }
    }

    private fun readLinesFromFile() {
        val fileName = "Words.csv"

        val reader = (assets.open(fileName) ?: throw RuntimeException("Cannot open file: $fileName"))
            .bufferedReader()

        lines = reader.useLines { line ->
            line.toList()
        }

        numberOfAllWords = lines.size
    }

    private fun moveToFlashcardActivity() {
        val intent = Intent(this, FlashcardActivity::class.java)
        intent.putParcelableArrayListExtra("wordsList", wordsList)
        startActivity(intent)
    }

    private fun takeChosenNumberOfWords() {
        wordsList.clear()
        lines.take(numberOfWords).forEach {
            val words = it.split(",")
            var word = Word(words[0], words[1], words[2], words[3])
            wordsList.add(word)
        }
    }


}
