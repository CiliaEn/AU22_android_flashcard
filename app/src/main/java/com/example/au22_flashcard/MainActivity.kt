package com.example.au22_flashcard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    lateinit var db: AppDatabase
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    private lateinit var flashCardButton: Button
    var currentWord: Word? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = AppDatabase.getInstance(this)
        job = Job()

        flashCardButton = findViewById(R.id.flashCardButton)
        flashCardButton.setOnClickListener {
            revealTranslation()
        }

        val addWordsButton = findViewById<Button>(R.id.addWordsButton)
        addWordsButton.setOnClickListener {
            val intent = Intent(this, AddWordsActivity::class.java)
            startActivity(intent)
        }

        val removeWordsButton = findViewById<Button>(R.id.removeWordsButton)
        removeWordsButton.setOnClickListener {
            val intent = Intent (this, RemoveWordsActivity::class.java)
            startActivity(intent)
        }


        val list = loadAllItems()

        launch {

            val wordList = list.await()
            WordList.wordList.addAll(wordList)
            for (word in wordList) {

                Log.d("!!!", "word: ${word.english}")
            }
        }
        showNewWord()
    }

    private fun loadAllItems(): Deferred<List<Word>> =
        async(Dispatchers.IO) {
            db.wordDao.getAll()
        }

    private fun revealTranslation() {
        if (flashCardButton.text == currentWord?.swedish) {
            flashCardButton.text = currentWord?.english
        } else {
            flashCardButton.text = currentWord?.swedish
        }
    }

    private fun showNewWord() {

        currentWord = WordList.getNewWord()
        flashCardButton.text = currentWord?.english
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (event?.action == MotionEvent.ACTION_UP) {
            showNewWord()
        }
        return true
    }
}