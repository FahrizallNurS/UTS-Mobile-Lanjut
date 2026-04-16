package com.example.unscramble.ui

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.unscramble.data.WordsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddWordViewModel(application: Application) : AndroidViewModel(application) {

    private val wordsRepository = WordsRepository(application)

    var newWord by mutableStateOf("")
        private set

    private val _savedWords = MutableStateFlow<Set<String>>(emptySet())
    val savedWords: StateFlow<Set<String>> = _savedWords.asStateFlow()

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message.asStateFlow()

    init {
        viewModelScope.launch {
            wordsRepository.customWordsFlow.collect { words ->
                _savedWords.value = words
            }
        }
    }

    fun addWord(){
        when {
            newWord.isBlank() -> {
                _message.value = "kata tidak boleh kosong"
            }
            _savedWords.value.contains(newWord.trim().lowercase()) -> {
                _message.value = "kata sudah ada"
            } else ->{
                viewModelScope.launch{
                    wordsRepository.addWord(newWord.trim())
                    _message.value =
                        "kata '${newWord.trim()}' berhasil ditambahkan"
                    newWord = ""
                }
            }
        }
    }

    fun updateNewWord(input: String) {
        newWord = input
    }

    fun clearMessage() {
        _message.value = ""
    }
}