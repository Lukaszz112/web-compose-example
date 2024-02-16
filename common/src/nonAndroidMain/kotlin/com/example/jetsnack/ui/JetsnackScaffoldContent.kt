package com.example.jetsnack.ui

import androidx.compose.animation.*
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import com.example.jetsnack.ui.home.Feed
import com.example.jetsnack.ui.home.HomeSections
import com.example.jetsnack.ui.home.Profile
import com.example.jetsnack.ui.home.cart.UiCart
import com.example.jetsnack.ui.home.search.Search
import com.example.jetsnack.ui.snackdetail.SnackDetail

@OptIn(ExperimentalAnimationApi::class)
@Composable
actual fun JetsnackScaffoldContent(
    innerPaddingModifier: PaddingValues,
    appState: MppJetsnackAppState,
    focusManager: FocusManager,
    focusRequester: FocusRequester
) {

    when (appState.currentRoute) {
        HomeSections.FEED.route -> {
            Feed(
                onSnackClick = appState::navigateToSnackDetail,
                modifier = Modifier.padding(innerPaddingModifier),
                focusManager = focusManager,
                focusRequester = focusRequester
            )
        }

        HomeSections.SEARCH.route -> {
            Search(
                onSnackClick = appState::navigateToSnackDetail,
                modifier = Modifier.padding(innerPaddingModifier)
            )
        }

        HomeSections.CART.route -> {
            UiCart(
                onSnackClick = appState::navigateToSnackDetail,
                modifier = Modifier.padding(innerPaddingModifier),
                focusManager = focusManager,
                focusRequester = focusRequester
            )
        }

        HomeSections.PROFILE.route -> {
            Profile(modifier = Modifier.padding(innerPaddingModifier))
        }

        else -> {
            val snackId = appState.currentRoute?.takeIf {
                it.startsWith(MainDestinations.SNACK_DETAIL_ROUTE + "/")
            }?.let {
                it.split("/")[1].toLongOrNull()
            }
            if (snackId != null) {
                SnackDetail(
                    snackId, appState::upPress, appState::navigateToSnackDetail,
                    focusManager = focusManager,
                    focusRequester = focusRequester
                )
            }
        }
    }
}

class NavigationStack<T>(initial: T) {
    private val stack = mutableStateListOf(initial)
    fun push(t: T) {
        stack.add(t)
    }

    fun replaceBy(t: T) {
        stack.removeLast()
        stack.add(t)
    }

    fun back() {
        if (stack.size > 1) {
            // Always keep one element on the view stack
            stack.removeLast()
        }
    }

    fun lastWithIndex() = stack.withIndex().last()
}