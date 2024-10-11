package com.example.unitconverter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    var inputValue by mutableStateOf("")
    var outputValue by mutableStateOf("")
    var inputUnit by mutableStateOf("Meters")
    var outputUnit by mutableStateOf("Meters")
    var iExpanded by mutableStateOf(false)
    var oExpanded by mutableStateOf(false)
}