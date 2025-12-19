package com.example.shopnow.features.login

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.shopnow.R
import com.example.shopnow.ui.theme.Accent
import com.example.shopnow.ui.theme.Peach
import com.example.shopnow.ui.theme.Primary

@Composable
fun LoginScreen(
    onLoginClick: (email: String, password: String) -> Unit = { _, _ -> }
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Peach)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            HeaderPeach(modifier = Modifier.height(200.dp))

            BottomSheetWithWave(
                modifier = Modifier.weight(1f),
                waveHeight = 70.dp
            ) {
                Spacer(Modifier.height(100.dp))

                Text(
                    text = "Welcome back!",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(Modifier.height(18.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.large
                )

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.large
                )

                Spacer(Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = rememberMe,
                        onCheckedChange = { rememberMe = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Primary,
                            uncheckedColor = Primary.copy(alpha = 0.6f),
                            checkmarkColor = Color.White
                        )
                    )
                    Text(
                        text = "Remember me",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black.copy(alpha = 0.65f)
                    )

                    Spacer(Modifier.weight(1f))

                    Text(
                        text = "Forgot password?",
                        style = MaterialTheme.typography.bodySmall,
                        color = Primary,
                        modifier = Modifier.clickable { }
                    )
                }

                Spacer(Modifier.height(14.dp))

                Button(
                    onClick = { onLoginClick(email.trim(), password) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Primary,
                        contentColor = Color.White
                    )
                ) {
                    Text("Login", fontWeight = FontWeight.SemiBold)
                }

                Spacer(Modifier.height(14.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "New user? ",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black.copy(alpha = 0.6f)
                    )
                    Text(
                        text = "Sign Up",
                        style = MaterialTheme.typography.bodySmall,
                        color = Primary,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable { }
                    )
                }

                Spacer(Modifier.weight(1f))

                Text(
                    text = "Demo app • QA Automation",
                    style = MaterialTheme.typography.labelSmall,
                    color = Accent.copy(alpha = 0.95f)
                )
            }
        }

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "ShopNow logo",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 120.dp)
                .height(50.dp)
                .zIndex(10f)
        )
    }
}

@Composable
private fun HeaderPeach(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Peach)
    )
}

@Composable
private fun BottomSheetWithWave(
    modifier: Modifier = Modifier,
    waveHeight: Dp,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawWavyTopBackground(
                color = Color.White,
                waveH = waveHeight.toPx(),
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 26.dp, start = 22.dp, end = 22.dp, bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content()
        }
    }
}

private fun DrawScope.drawWavyTopBackground(
    color: Color,
    waveH: Float,
    backWaveColor: Color = Primary,
    backXOffsetDp: Float = 0f,          // your -50f, but configurable (in dp)
    backYOffsetFactor: Float = -0.15f     // your -waveH * 0.15f, configurable
) {
    val w = size.width
    val h = size.height

    val backX = backXOffsetDp.dp.toPx()
    val backY = waveH * backYOffsetFactor

    // Back wave FIRST (so it's behind)
    val backWave = buildWavePath(
        w = w,
        h = h,
        waveH = waveH,
        x = backX,
        y = backY
    )
    drawPath(
        path = backWave,
        color = backWaveColor
    )

    // Main wave on top
    val mainWave = buildWavePath(
        w = w,
        h = h,
        waveH = waveH,
        x = 0f,
        y = 0f
    )
    drawPath(
        path = mainWave,
        color = color
    )
}

private fun buildWavePath(
    w: Float,
    h: Float,
    waveH: Float,
    x: Float,
    y: Float
): Path {
    return Path().apply {
        moveTo(0f + x, waveH + y)

        cubicTo(
            w * 0.25f + x, waveH * 0.2f + y,
            w * 0.55f + x, waveH * 1.8f + y,
            w * 0.75f + x, waveH * 0.9f + y
        )
        cubicTo(
            w * 0.88f + x, waveH * 0.4f + y,
            w * 0.96f + x, waveH * 1.2f + y,
            w + x, waveH + y
        )

        lineTo(w + x, h)
        lineTo(0f + x, h)
        close()
    }
}