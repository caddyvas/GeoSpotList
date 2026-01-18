package com.deepak.geospotlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.deepak.geospotlist.ui.theme.GeoSpotListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    GeoSpotListTheme {
        val navController = rememberNavController()
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = colorResource(id = R.color.lavender),
            floatingActionButton = {
                AddLocationFab()
            },
            floatingActionButtonPosition = FabPosition.Center,
            bottomBar = {
                MainBottomBar(navController = navController)
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "start",
                modifier = Modifier.padding(innerPadding)
            ) {
                // An empty start destination, so no tab is selected initially
                composable("start") { }
                
                composable(Routes.PLACES) {
                    PlacesScreenDisplay()
                }

                composable(Routes.MAPS) {
                    MapsScreenDisplay()
                }
            }
        }
    }
}

@Composable
fun AddLocationFab() {
    FloatingActionButton(
        onClick = {
            // TODO - show list of locations
        }, containerColor = Color(0xFF4CAF50), // Green FAB
        modifier = Modifier
            .size(72.dp)
            .offset(y = (-32).dp) // Pull FAB into BottomAppBar
            .zIndex(1f)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_add_location),
            contentDescription = "Add Location",
            tint = Color.White,
            modifier = Modifier.size(40.dp)
        )
    }
}

@Composable
fun MainBottomBar(navController: NavController) {
    // These two lines get the current screen's route
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomAppBar(containerColor = Color.White, tonalElevation = 4.dp) {
        NavigationBar(containerColor = Color.Transparent, modifier = Modifier.fillMaxWidth()) {
            NavigationBarItem(
                selected = currentRoute == "places",
                onClick = {
                    navController.navigate(Routes.PLACES) {
                        launchSingleTop =
                            true // ensures that you only have a single instance of each screen in your back stack, which is the standard and expected behavior for bottom navigation.
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_places_marked),
                        contentDescription = "Places"
                    )
                },
                label = { Text("Places") }
            )
            NavigationBarItem(
                selected = currentRoute == "maps",
                onClick = {
                    navController.navigate(Routes.MAPS) {
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_map),
                        contentDescription = "Map"
                    )
                },
                label = { Text("Map") }
            )
            NavigationBarItem(
                selected = false,
                onClick = { /* TODO */ },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_items),
                        contentDescription = "Items"
                    )
                },
                label = { Text("Items") }
            )
            NavigationBarItem(
                selected = false,
                onClick = { /* TODO */ },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_dummy),
                        contentDescription = "Dummy"
                    )
                },
                label = { Text("Dummy") }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}

