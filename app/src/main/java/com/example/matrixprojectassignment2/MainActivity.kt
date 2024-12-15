package com.example.matrixprogram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.matrixprogram.ui.theme.MatrixProgramTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontFamily
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MatrixProgramTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
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
            }
        }
    }
    @Composable
    fun MatrixDisplay(matrixSize: Int, matrixContent: String) {
        val fontSize = if (matrixSize >= 12) 12.sp else 16.sp
        val matrixRows = matrixContent.split("\n").filter { it.isNotEmpty() }

        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            for ((rowIndex, rowContent) in matrixRows.withIndex()) {
                Row(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val elements = rowContent.trim().split(" ")
                    for ((columnIndex, element) in elements.withIndex()) {
                        val isDiagonalElement = element.startsWith("RED_")
                        Text(
                            text = element.removePrefix("RED_").trim(),
                            color = if (isDiagonalElement) Color.Red else Color.Black,
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 1.dp),
                            textAlign = TextAlign.Center,
                            fontSize = fontSize,
                            fontFamily = FontFamily.SansSerif
                        )
                    }
                }
            }
        }
    }
    @Composable
    fun DisplayTitle(title: String, modifier: Modifier = Modifier) {
        Text(text = title, modifier = modifier)
    }

    @Composable
    fun MatrixInputSection() {
        var inputText by remember { mutableStateOf("") }
        var matrixSize by remember { mutableStateOf(0) }
        var generatedMatrix by remember { mutableStateOf("") }

        TextField(
            value = inputText,
            onValueChange = {
                inputText = it
                matrixSize = it.toIntOrNull() ?: 0
            },
            label = { Text("Enter a number") }
        )

        Button(onClick = {
            generatedMatrix = createMatrix(matrixSize)
        }) {
            Text("Generate Matrix")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (generatedMatrix.isNotEmpty()) {
            MatrixDisplay(matrixSize, generatedMatrix)
        }
    }
    private fun createMatrix(size: Int): String {
        if (size <= 0) {
            return "Invalid matrix size"
        } else {
            val matrix = Array(size) { IntArray(size) { 0 } }
            val outputBuilder = StringBuilder()

            outputBuilder.append("Default Matrix:\n")
            outputBuilder.append(generateDefaultMatrix(matrix))

            outputBuilder.append("Numbered Matrix:\n")
            outputBuilder.append(generateNumberedMatrix(matrix))

            outputBuilder.append("Flipped Matrix:\n")
            outputBuilder.append(generateFlippedMatrix(matrix))

            return outputBuilder.toString()
        }
    }
    private fun generateDefaultMatrix(matrix: Array<IntArray>): String {
        val size = matrix.size
        val maxNumber = size * size
        val width = maxNumber.toString().length - 2
        val outputBuilder = StringBuilder()

        for ((rowIndex, row) in matrix.withIndex()) {
            for ((columnIndex, value) in row
                for ((columnIndex, value) in row.withIndex()) {
                    val isDiagonalElement = columnIndex == size - 1 - rowIndex
                    outputBuilder.append(
                        if (isDiagonalElement) {
                            "RED_$value".padStart(width)  // Mark diagonal numbers
                        } else {
                            value.toString().padStart(width)
                        }
                    )
                    outputBuilder.append(" ")
                }
            outputBuilder.append("\n")
        }
        return outputBuilder.toString()
    }



