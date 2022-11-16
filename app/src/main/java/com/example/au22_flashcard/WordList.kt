package com.example.au22_flashcard

object WordList {
    val wordList = mutableListOf<Word>()
    private val usedWords = mutableListOf<Word>()

    init {
        wordList.add(Word(0, "Hello", "Hej"))
    }

    fun getNewWord(): Word {
        if (wordList.size == usedWords.size) {
            usedWords.clear()
        }

        var word: Word? = null

        do {
            val rnd = (0 until wordList.size).random()
            word = wordList[rnd]
        } while (usedWords.contains(word))

        usedWords.add(word!!)

        return word
    }
}








