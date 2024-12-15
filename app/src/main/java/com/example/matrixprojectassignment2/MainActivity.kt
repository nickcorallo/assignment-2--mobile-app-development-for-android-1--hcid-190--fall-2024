
package com.example.matrixprojectassignment2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MatrixProgramTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MatrixApp()
                }
            }
        }
    }

    @Composable
    fun MatrixApp() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DisplayTitle(title = "Matrix Program")
                Spacer(modifier = Modifier.height(16.dp))
                MatrixInputSection()
            }
        }
    }

    @Composable
    fun DisplayTitle(title: String) {
        Text(
            text = title,
            fontSize = 24.sp,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center
        )
    }

    @Composable
    fun MatrixInputSection() {
        var inputText by remember { mutableStateOf("") }
        var matrixSize by remember { mutableStateOf(0) }
        var generatedMatrix by remember { mutableStateOf("") }
        var errorMessage by remember { mutableStateOf("") }

        TextField(
            value = inputText,
            onValueChange = {
                inputText = it
                matrixSize = it.toIntOrNull() ?: 0
                errorMessage = if (matrixSize <= 0) "Please enter a positive integer." else ""
            },
            label = { Text("Enter matrix size") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (matrixSize > 0) {
                generatedMatrix = createMatrix(matrixSize)
                errorMessage = ""
            } else {
                errorMessage = "Please enter a valid matrix size."
            }
        }) {
            Text("Generate Matrix")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = Color.Red)
        } else if (generatedMatrix.isNotEmpty()) {
            MatrixDisplay(matrixSize, generatedMatrix)
        }
    }

    @Composable
    fun MatrixDisplay(matrixSize: Int, matrixContent: String) {
        val fontSize = if (matrixSize >= 12) 12.sp else 16.sp
        val matrixRows = matrixContent.split("\n").filter { it.isNotEmpty() }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            for (rowContent in matrixRows) {
                Row(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    val elements = rowContent.split(" ")
                    for (element in elements) {
                        val isDiagonalElement = element.startsWith("RED_")
                        Text(
                            text = element.removePrefix("RED_"),
                            color = if (isDiagonalElement) Color.Red else Color.Black,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            fontSize = fontSize,
                            fontFamily = FontFamily.SansSerif
                        )
                    }
                }
            }
        }
    }

    private fun createMatrix(size: Int): String {
        if (size <= 0) {
            return "Invalid matrix size"
        }

        val defaultMatrix = generateDefaultMatrix(size)
        val numberedMatrix = generateNumberedMatrix(size)
        val flippedMatrix = generateFlippedMatrix(size)

        return "Default Matrix:\n$defaultMatrix\n" +
                "Numbered Matrix:\n$numberedMatrix\n" +
                "Flipped Matrix:\n$flippedMatrix"
    }

    private fun generateDefaultMatrix(size: Int): String {
        val builder = StringBuilder()
        for (row in 0 until size) {
            for (col in 0 until size) {
                val value = if (row + col == size - 1) "RED_0" else "0"
                builder.append(value).append(" ")
            }
            builder.append("\n")
        }
        return builder.toString()
    }

    private fun generateNumberedMatrix(size: Int): String {
        val builder = StringBuilder()
        var count = 1
        for (row in 0 until size) {
            for (col in 0 until size) {
                val value = if (row + col == size - 1) "RED_$count" else "$count"
                builder.append(value).append(" ")
                count++
            }
            builder.append("\n")
        }
        return builder.toString()
    }

    private fun generateFlippedMatrix(size: Int): String {
        val builder = StringBuilder()
        val maxNumber = size * size
        var count = maxNumber
        for (row in 0 until size) {
            for (col in 0 until size) {
                val value = if (row + col == size - 1) "RED_$count" else "$count"
                builder.append(value).append(" ")
                count--
            }
            builder.append("\n")
        }
        return builder.toString()
    }
}

@Composable
fun MatrixProgramTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(),
        content = content
    )
}