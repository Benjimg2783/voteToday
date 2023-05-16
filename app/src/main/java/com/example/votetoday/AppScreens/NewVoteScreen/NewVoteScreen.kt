package com.example.votetoday.AppScreens.NewVoteScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.votetoday.AppScreens.LoginAndRegistre.LoginAndRegistreViewModel
import com.example.votetoday.Common.SystemBarColor
import com.example.votetoday.ui.theme.VoteTodayOrange

@Preview
@Composable
fun PreviewNewVoteScreenView() {
    NewVoteScreen(rememberNavController())
}

@Composable
fun NewVoteScreen(
    navController: NavController,
    viewModel: NewVoteScreenViewModel = hiltViewModel()
) {
    SystemBarColor(color = VoteTodayOrange)
    Column() {
        
    }
}