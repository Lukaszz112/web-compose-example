package com.example.jetsnack

import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.key.*

fun Modifier.addKeyListeners(focusManager: FocusManager) = onKeyEvent {
    if(it.type == KeyEventType.KeyUp){
        return@onKeyEvent false
    }

    when (it.key) {
        Key.DirectionLeft -> {
            focusManager.moveFocus(FocusDirection.Left)
            true
        }

        Key.DirectionRight -> {
            focusManager.moveFocus(FocusDirection.Right)
            true
        }

        Key.DirectionDown -> {
            focusManager.moveFocus(FocusDirection.Down)
            true
        }

        Key.DirectionUp -> {
            focusManager.moveFocus(FocusDirection.Up)
            true
        }

        else -> false
    }
}