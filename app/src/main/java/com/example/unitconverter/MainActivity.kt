package com.example.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unitconverter.ui.theme.UnitConverterTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnitConverterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    UnitConverter(
                        name = "Unit Converter",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun UnitConverter(name: String, modifier: Modifier = Modifier, mainViewModel: MainViewModel = viewModel()) {

    val conversionFactor = remember { mutableStateOf(1.00) }
    val oConversionFactor = remember { mutableStateOf(1.00) }

    fun convertUnits(){
        val inputValueDouble = mainViewModel.inputValue.toDoubleOrNull() ?: 0.0
        val result = (inputValueDouble * conversionFactor.value * 100.0 / oConversionFactor.value).roundToInt() / 100.00
        mainViewModel.outputValue = result.toString()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.headlineLarge,
            modifier = modifier
        )

        Spacer(modifier = Modifier.padding(8.dp))

        OutlinedTextField(
            value = mainViewModel.inputValue,
            onValueChange = {
                mainViewModel.inputValue = it
                convertUnits()
            },
            label = { Text(text = "Enter Value") }
        )

        Spacer(modifier = Modifier.padding(16.dp))

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            //Input Button
            Button(onClick = { mainViewModel.iExpanded = true}) {
                Text(text = mainViewModel.inputUnit)
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Arrow Down"
                )
            }
            DropdownMenu(expanded = mainViewModel.iExpanded, onDismissRequest = { mainViewModel.iExpanded = false }) {
                DropdownMenuItem(
                    text = { Text(text = "Centimeters") },
                    onClick = {
                        mainViewModel.iExpanded = false
                        mainViewModel.inputUnit = "Centimeters"
                        conversionFactor.value = 0.01
                        convertUnits()
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "Meters") },
                    onClick = {
                        mainViewModel.iExpanded = false
                        mainViewModel.inputUnit = "Meters"
                        conversionFactor.value = 1.0
                        convertUnits()
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "Feet") },
                    onClick = {
                        mainViewModel.iExpanded = false
                        mainViewModel.inputUnit = "Feet"
                        conversionFactor.value = 0.3048
                        convertUnits()
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "Millimeters") },
                    onClick = {
                        mainViewModel.iExpanded = false
                        mainViewModel.inputUnit = "Millimeters"
                        conversionFactor.value = 0.001
                        convertUnits()
                    }
                )
            }

            Spacer(modifier = Modifier.padding(8.dp))

            //OutputButton
            Button(onClick = { mainViewModel.oExpanded = true }) {
                Text(text = mainViewModel.outputUnit)
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Arrow Down"
                )
                DropdownMenu(expanded = mainViewModel.oExpanded, onDismissRequest = { mainViewModel.oExpanded = false }) {
                    DropdownMenuItem(
                        text = { Text(text = "Centimeters") },
                        onClick = {
                            mainViewModel.oExpanded = false
                            mainViewModel.outputUnit = "Centimeters"
                            oConversionFactor.value = 0.01
                            convertUnits()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Meters") },
                        onClick = {
                            mainViewModel.oExpanded = false
                            mainViewModel.outputUnit = "Meters"
                            oConversionFactor.value = 1.00
                            convertUnits()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Feet") },
                        onClick = {
                            mainViewModel.oExpanded = false
                            mainViewModel.outputUnit = "Feet"
                            oConversionFactor.value = 0.3048
                            convertUnits()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Millimeters") },
                        onClick = {
                            mainViewModel.oExpanded = false
                            mainViewModel.outputUnit = "Millimeters"
                            oConversionFactor.value = 0.001
                            convertUnits()
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Result: ${mainViewModel.outputValue} ${mainViewModel.outputUnit}",
            style = MaterialTheme.typography.headlineMedium
        )
    }

}

@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {
    UnitConverterTheme {
        UnitConverter("Unit Converter")
    }
}