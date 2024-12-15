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
