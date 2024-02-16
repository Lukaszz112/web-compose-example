package com.example.jetsnack.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.jetsnack.ui.home.Feed
import com.example.jetsnack.ui.home.HomeSections
import com.example.jetsnack.ui.home.Profile
import com.example.jetsnack.ui.home.cart.UiCart
import com.example.jetsnack.ui.home.search.Search
import com.example.jetsnack.ui.snackdetail.SnackDetail

@Composable
actual fun JetsnackScaffoldContent(
    innerPaddingModifier: PaddingValues,
    appState: MppJetsnackAppState,
    focusManager: FocusManager,
    focusRequester: FocusRequester
) {
    NavHost(
        navController = appState.navController,
        startDestination = MainDestinations.HOME_ROUTE,
        modifier = Modifier.padding(innerPaddingModifier)
    ) {
        jetsnackNavGraph(
            onSnackSelected = appState::navigateToSnackDetail,
            upPress = appState::upPress,
            focusManager = focusManager,
            focusRequester = focusRequester
        )
    }
}

private fun NavGraphBuilder.jetsnackNavGraph(
    onSnackSelected: (Long, NavBackStackEntry) -> Unit,
    upPress: () -> Unit,
    focusManager: FocusManager,
    focusRequester: FocusRequester
) {
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = HomeSections.FEED.route
    ) {
        addHomeGraph(
            onSnackSelected,
            focusRequester = focusRequester,
            focusManager = focusManager
        )
    }
    composable(
        "${MainDestinations.SNACK_DETAIL_ROUTE}/{${MainDestinations.SNACK_ID_KEY}}",
        arguments = listOf(navArgument(MainDestinations.SNACK_ID_KEY) { type = NavType.LongType })
    ) { backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val snackId = arguments.getLong(MainDestinations.SNACK_ID_KEY)
        SnackDetail(snackId, upPress, onSnackClick = { onSnackSelected(snackId, backStackEntry) },
            focusManager = focusManager,
            focusRequester = focusRequester
        )
    }
}

fun NavGraphBuilder.addHomeGraph(
    onSnackSelected: (Long, NavBackStackEntry) -> Unit,
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester,
    focusManager: FocusManager
) {
    composable(HomeSections.FEED.route) { from ->
        Feed(onSnackClick = { id -> onSnackSelected(id, from) }, modifier, focusManager, focusRequester)
    }
    composable(HomeSections.SEARCH.route) { from ->
        Search(onSnackClick = { id -> onSnackSelected(id, from) }, modifier)
    }
    composable(HomeSections.CART.route) { from ->
        UiCart(onSnackClick = { id -> onSnackSelected(id, from) }, modifier, focusManager = focusManager, focusRequester = focusRequester)
    }
    composable(HomeSections.PROFILE.route) {
        Profile(modifier)
    }
}