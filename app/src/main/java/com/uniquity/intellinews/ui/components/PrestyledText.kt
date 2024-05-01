package com.cycleone.cycleoneapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class PrestyledText() {
    @Composable
    public fun Regular(placeholder: String = "",onChange: (String) -> Unit = {}, enabled: Boolean = true, label: String = "", icon: ImageVector? = null) {
        var c by remember{mutableStateOf("")}
        OutlinedTextField(value = c, onValueChange = {x: String -> c = x; onChange(x)}, placeholder = {Text(placeholder)}, enabled = enabled, label = {Text(label)}, trailingIcon = {icon?.let {Image(
             it, contentDescription = "Icon" )}})
    }

    @Composable
    public fun Password(placeholder: String = "",onChange: (String) -> Unit = {}, enabled: Boolean = true, label: String = "", icon: ImageVector? = null) {
        var c by remember{mutableStateOf("")}
        OutlinedTextField(value = c, visualTransformation = PasswordVisualTransformation(), onValueChange = {x: String -> c = x; onChange(x)}, placeholder = {Text(placeholder)}, enabled = enabled, label = {Text(label)}, trailingIcon = {icon?.let {Image(
            it, contentDescription = "Icon" )}})
    }

    @Composable
    public fun OTP(onFill: (Int) -> Unit = {}, enabled: Boolean = true, digits: Int = 4) {
        var otpCode by remember {
            mutableStateOf("")
        }
        BasicTextField (
            value = otpCode,
            onValueChange = { newValue ->
                otpCode = newValue
                if (otpCode.length == digits) {
                    onFill(otpCode.toInt())
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword
            )

        ) {}
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            repeat(digits) { index ->
                val number = when {
                    index >= otpCode.length -> ""
                    else -> otpCode[index].toString()
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = number,
                        style = MaterialTheme.typography.titleLarge,
                    )

                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(2.dp)
                            .background(Color.Black)
                    )
                }
            }
        }
    }

    @Composable
    @Preview
    fun prev() {
        Column {
            Regular("Preview", {}, true, label = "Test", Icons.Default.Build)
            Password("Preview", {}, true, label = "Test", Icons.TwoTone.Add)
            OTP()
        }
    }

}