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

class AddWordsActivity : AppCompatActivity(), CoroutineScope {


    lateinit var db: AppDatabase
    lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_words)

        job = Job()
        db = AppDatabase.getInstance(this)

        val swedishWordEditText = findViewById<EditText>(R.id.swedishWordEditText)
        val englishWordEditText = findViewById<EditText>(R.id.englishWordEditText)

        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val addWordButton = findViewById<Button>(R.id.addWordButton)
        addWordButton.setOnClickListener {
            val englishWord = englishWordEditText.text.toString()
            val swedishWord = swedishWordEditText.text.toString()
            val word = Word(0, englishWord, swedishWord)

            saveWord(word)
            swedishWordEditText.setText("")
            englishWordEditText.setText("")

            Toast.makeText(
                this,
                "$swedishWord-$englishWord was added to flashcards!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun saveWord(word: Word) {
        launch(Dispatchers.IO) {
            db.wordDao.insert(word)
        }
    }
}