package com.example.testmenu.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {


    private val _textCompt = MutableLiveData<String>().apply {
        value = "Number of point : " + "sharedPref.getInt('Points')"
    }

    val textCompt: LiveData<String> = _textCompt
}