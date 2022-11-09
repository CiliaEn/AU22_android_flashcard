package com.example.au22_flashcard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    lateinit var wordButton : Button
    var currentWord : Word? = null
    lateinit var db : AppDatabase
    private lateinit var job : Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = AppDatabase.getInstance(this)
        job = Job()

        wordButton = findViewById(R.id.wordButton)


        showNewWord()

        wordButton.setOnClickListener {
            revealTranslation()
        }

        val addWordsButton = findViewById<Button>(R.id.addWordsButton)
        addWordsButton.setOnClickListener {
            val intent = Intent(this, AddWordsActivity::class.java)
            startActivity(intent)
        }

        val list  = loadAllItems()

        launch {

            val wordList = list.await()

            WordList.wordList.addAll(wordList)


            for(word in wordList) {

                Log.d("!!!", "word: ${word.english}")
            }
        }


    }



    private fun loadAllItems() : Deferred<List<Word>> =
        async (Dispatchers.IO) {
            db.wordDao.getAll()
        }

    private fun revealTranslation() {
        if(wordButton.text == currentWord?.swedish){
            wordButton.text = currentWord?.english
        }else{
            wordButton.text = currentWord?.swedish
        }

    }


    private fun showNewWord() {

        currentWord = WordList.getNewWord()
        wordButton.text = currentWord?.swedish
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (event?.action == MotionEvent.ACTION_UP) {
            showNewWord()
        }

        return true
    }
}

//1. skapa en ny activity där ett nytt ord får skrivas in
//2. spara det nya ordet i databasen
//3. I main activity läs in alla ord från databasen
//använd coroutiner när ni läser och skriver till databasen se tidigare exempel