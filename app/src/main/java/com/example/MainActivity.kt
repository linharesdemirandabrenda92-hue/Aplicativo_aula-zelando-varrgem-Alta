package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.*
import com.example.ui.theme.ConectaCidadaoTheme
import com.example.viewmodel.AppScreen
import com.example.viewmodel.CityViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ConectaCidadaoTheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp(
    viewModel: CityViewModel = viewModel()
) {
    val currentScreen by viewModel.currentScreen.collectAsState()

    // Proper BackHandler handling for nested screens
    BackHandler(enabled = currentScreen != AppScreen.HOME && currentScreen != AppScreen.LOGIN) {
        viewModel.navigateBack()
    }

    Scaffold(
        bottomBar = {
            if (currentScreen != AppScreen.LOGIN) {
                NavigationBar(modifier = Modifier.testTag("bottom_nav_bar")) {
                    NavigationBarItem(
                        selected = currentScreen == AppScreen.HOME,
                        onClick = { viewModel.navigateTo(AppScreen.HOME) },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Início") },
                        label = { Text("Início") },
                        modifier = Modifier.testTag("nav_item_home")
                    )
                    NavigationBarItem(
                        selected = currentScreen == AppScreen.SERVICES_MENU || currentScreen == AppScreen.NEW_REQUEST || currentScreen == AppScreen.REQUEST_DETAIL,
                        onClick = { viewModel.navigateTo(AppScreen.SERVICES_MENU) },
                        icon = { Icon(Icons.Default.Build, contentDescription = "Serviços") },
                        label = { Text("Serviços") },
                        modifier = Modifier.testTag("nav_item_services")
                    )
                    NavigationBarItem(
                        selected = currentScreen == AppScreen.NEWS,
                        onClick = { viewModel.navigateTo(AppScreen.NEWS) },
                        icon = { Icon(Icons.Default.Newspaper, contentDescription = "Notícias") },
                        label = { Text("Notícias") },
                        modifier = Modifier.testTag("nav_item_news")
                    )
                    NavigationBarItem(
                        selected = currentScreen == AppScreen.MESSAGES,
                        onClick = { viewModel.navigateTo(AppScreen.MESSAGES) },
                        icon = { Icon(Icons.Default.ChatBubbleOutline, contentDescription = "Mensagens") },
                        label = { Text("Mensagens") },
                        modifier = Modifier.testTag("nav_item_messages")
                    )
                    NavigationBarItem(
                        selected = currentScreen == AppScreen.FULL_MENU || currentScreen == AppScreen.PROFILE,
                        onClick = { viewModel.navigateTo(AppScreen.FULL_MENU) },
                        icon = { Icon(Icons.Default.Menu, contentDescription = "Menu") },
                        label = { Text("Menu") },
                        modifier = Modifier.testTag("nav_item_menu")
                    )
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        AnimatedContent(
            targetState = currentScreen,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "screen_transition",
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) { screen ->
            when (screen) {
                AppScreen.LOGIN -> LoginScreen(viewModel = viewModel)
                AppScreen.HOME -> HomeScreen(viewModel = viewModel)
                AppScreen.SERVICES_MENU -> ServicesMenuScreen(viewModel = viewModel)
                AppScreen.NEW_REQUEST -> NewRequestScreen(viewModel = viewModel)
                AppScreen.REQUEST_DETAIL -> RequestDetailScreen(viewModel = viewModel)
                AppScreen.NEWS -> NewsScreen(viewModel = viewModel)
                AppScreen.JOBS -> JobsScreen(viewModel = viewModel)
                AppScreen.EVENTS -> EventsScreen(viewModel = viewModel)
                AppScreen.MESSAGES -> MessagesScreen(viewModel = viewModel)
                AppScreen.PROFILE -> ProfileScreen(viewModel = viewModel)
                AppScreen.EVALUATION -> EvaluationScreen(viewModel = viewModel)
                AppScreen.FULL_MENU -> FullMenuScreen(viewModel = viewModel)
            }
        }
    }
}
