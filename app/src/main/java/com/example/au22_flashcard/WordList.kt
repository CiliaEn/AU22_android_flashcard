package com.example.au22_flashcard

object WordList {
     val wordList = mutableListOf<Word>()
    private val usedWords = mutableListOf<Word>()

    init {
        initializeWords()
    }


    fun initializeWords() {
        val word = Word(0, "Hello", "Hej")
        wordList.add(word)
        wordList.add(Word(0, "Good bye", "Hej då"))
        wordList.add(Word(0, "Thank you", "Tack"))
        wordList.add(Word(0, "Welcome", "Välkommen"))
        wordList.add(Word(0, "Computer", "Dator"))

    }

    fun getNewWord() : Word {
        if (wordList.size == usedWords.size) {
            usedWords.clear()
        }

        var word : Word? = null

        do {
            val rnd = (0 until wordList.size).random()
            word = wordList[rnd]
        } while(usedWords.contains(word))

        usedWords.add(word!!)

        return word
    }

    // 1. en till lista med redan använda ord
    // 2. lista med index på använda ord
    // 3. använt ord tas bort från listan
    // 4. ordet håller reda på om det redan är använt

}








