package com.example.roomdatabase.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ViewMdole : ViewModel() {


    var conet = 0

    fun  add(){
        conet++
    }

    private  val _text = MutableLiveData("Hello Worled")
    val text : LiveData<String>  get()= _text

    fun setText(newText : String){
        viewModelScope.launch {
            _text.value = newText
        }
    }
}