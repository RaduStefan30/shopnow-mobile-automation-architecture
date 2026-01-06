package com.example.shopnow.ui.screens

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.shopnow.features.login.LoginTags

class LoginScreen(p: SemanticsNodeInteractionsProvider) {

    private val screen = p.onNodeWithTag(LoginTags.SCREEN)
    private val email = p.onNodeWithTag(LoginTags.EMAIL)
    private val password = p.onNodeWithTag(LoginTags.PASSWORD)
    private val loginButton = p.onNodeWithTag(LoginTags.LOGIN_BUTTON)
    private val error = p.onNodeWithTag(LoginTags.ERROR)

    fun assertDisplayed() = screen.assertIsDisplayed()

    fun login(emailValue: String, passwordValue: String) {
        email.performTextInput(emailValue)
        password.performTextInput(passwordValue)
        loginButton.performClick()
    }

    fun assertErrorDisplayed() = error.assertIsDisplayed()
}