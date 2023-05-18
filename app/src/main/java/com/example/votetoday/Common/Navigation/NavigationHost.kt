package com.example.votetoday.Common.Navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.votetoday.AppScreens.LoginAndRegistre.LoginScreen
import com.example.votetoday.AppScreens.MainScreen.MainScreen
import com.example.votetoday.AppScreens.NewVoteScreen.NewVoteScreen
import com.example.votetoday.AppScreens.ProfileScreen.ProfileScreen
import com.example.votetoday.Common.GestorBD.FBAuth
import com.example.votetoday.Composables.LogOutPopUp
import com.example.votetoday.ui.theme.VoteTodayOrange


@Composable
fun NavigationHost(navController: NavHostController, startDestination: String) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    )
    {
        composable(Screens.LoginScreen.ruta) {
            LoginScreen(navController = navController)
        }
        composable(Screens.MainScreen.ruta) {
            MainScreen(navController = navController)
        }
        composable(Screens.ProfileScreen.ruta) {
            ProfileScreen(navController = navController)
        }
        composable(Screens.NewVoteScreen.ruta) {
            NewVoteScreen(navController = navController)
        }
    }
}

class NavigationFunctions() {
    companion object {
        //region BottomBar Status
        var changeScreen = mutableStateOf(false)
        var screenID = mutableStateOf(0)

        fun changeScreen(screenID: Int) {
            if (screenID != -1) {
                this.screenID.value = screenID
            }
            changeScreen.value = !changeScreen.value
        }
        //endregion

        //region NavigationPOP
        @Composable
        fun NavigatePop(navController: NavController, destination: String) {
            navController.navigate(destination) {
                popUpTo(Screens.LoginScreen.ruta) {
                    inclusive = true
                }
            }
        }

        //endregion
        //Region NavBar
        @Composable
        fun NavBar(
            navController: NavHostController,
            items: List<Screens> = listOf(
                Screens.MainScreen,
                Screens.ProfileScreen,
                Screens.LoginScreen
            ),
            viewModel: NavigationHostViewModel = hiltViewModel()
        ) {
            LogOutPopUp( onConfirmButton = {

                navController.navigate(Screens.LoginScreen.ruta) {
                    popUpTo(Screens.MainScreen.ruta) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
                FBAuth.logOut()
                viewModel.dialogState = false
            }, onDismissRequest = {viewModel.dialogState = false}, showDialog = viewModel.dialogState)
            BottomNavigation(
                backgroundColor = VoteTodayOrange,
            ) {
                items.filter { it.icon != null }.forEach { screen ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                imageVector = screen.icon!!,
                                contentDescription = null,
                                tint = Color.White
                            )
                        },
                        selected = screenID.value == items.indexOf(screen),
                        onClick = {
                            if (screen == Screens.LoginScreen) {
                                viewModel.dialogState = true    //Show Dialog
                            } else {
                                navController.navigate(screen.ruta) {
                                    popUpTo(Screens.MainScreen.ruta) {
                                        inclusive = true
                                    }
                                    launchSingleTop = true
                                }
                            }
                        },
                    )

                }
            }

            //endregion
        }
    }
}
