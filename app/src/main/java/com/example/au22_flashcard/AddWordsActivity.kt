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
    
    lateinit var swedishWordEditText: EditText
    lateinit var englishWordEditText: EditText
    lateinit var db : AppDatabase
    private lateinit var job : Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_words)
        job = Job()

        db = AppDatabase.getInstance(this)

        swedishWordEditText = findViewById(R.id.swedishWordEditText)
        englishWordEditText = findViewById(R.id.englishWordEditText)

        val doneButton = findViewById<ImageButton>(R.id.backButton)
        doneButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        
        val addWordButton = findViewById<Button>(R.id.addWordButton)
        addWordButton.setOnClickListener { 
            var swedishWord = swedishWordEditText.text.toString()
            var englishWord = englishWordEditText.text.toString()
            var word = Word(0, englishWord, swedishWord)
            saveItem(word)
            swedishWordEditText.setText("")
            englishWordEditText.setText("")
            Toast.makeText(this, "$swedishWord-$englishWord was added to flashcards!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveItem(word: Word) {
        launch(Dispatchers.IO) {
            db.wordDao.insert(word)
        }
    }
    private fun delete(word : Word) =
        launch(Dispatchers.IO) {
            db.wordDao.delete(word)
        }
}