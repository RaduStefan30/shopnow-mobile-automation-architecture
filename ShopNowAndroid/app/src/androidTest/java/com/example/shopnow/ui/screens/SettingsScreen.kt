package com.example.shopnow.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import com.example.shopnow.features.settings.SettingsTags

class SettingsScreen(p: SemanticsNodeInteractionsProvider) {

    private val screen = p.onNodeWithTag(SettingsTags.SCREEN)
    private val logout = p.onNodeWithTag(SettingsTags.LOGOUT)

    fun assertDisplayed() = screen.assertIsDisplayed()

    fun logout() {
        logout.performClick()
    }
}
