package com.example.readily.ui.elements

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.readily.ui.theme.DarkerMainColor

@Composable
fun CustomButton(onClick: () -> Unit, text: String) {

    Button(onClick = { onClick() }, colors = ButtonDefaults.buttonColors(containerColor = DarkerMainColor)) {
        Text(text = text)
    }

}