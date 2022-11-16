package com.example.au22_flashcard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RemoveWordsActivity : AppCompatActivity(), CoroutineScope {

    lateinit var db: AppDatabase
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remove_words)

        job = Job()
        db = AppDatabase.getInstance(this)

        val backButton = findViewById<ImageButton>(R.id.backButton2)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val removeWordButton = findViewById<Button>(R.id.removeWordButton)
        removeWordButton.setOnClickListener {
            val wordEditText = findViewById<EditText>(R.id.wordEditText)
            val wordInEnglish = wordEditText.text.toString()

            for (word in WordList.wordList) {
                if (word.english.equals(wordInEnglish, ignoreCase = true)) {
                    deleteWord(word)
                    Toast.makeText(
                        this,
                        "$wordInEnglish was deleted from flashcards!",
                        Toast.LENGTH_SHORT
                    ).show()
                    wordEditText.setText("")
                }
            }
        }
    }

    private fun deleteWord(word: Word) =
        launch(Dispatchers.IO) {
            db.wordDao.delete(word)
        }
}