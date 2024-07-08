package com.example.readily.screens.Challenge

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.readily.R
import com.example.readily.model.Book
import com.example.readily.navigation.INavigationRouter
import com.example.readily.screens.AddEdit.AddEditViewModel
import com.example.readily.screens.MainUIState
import com.example.readily.screens.MainViewModel
import com.example.readily.screens.ScreenData
import com.example.readily.ui.elements.CustomButton
import com.example.readily.ui.elements.CustomHeader
import com.example.readily.ui.theme.DarkerMainColor
import com.example.readily.ui.theme.MainColor
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookChallengeScreen(navigationRouter: INavigationRouter) {

    val viewModel = hiltViewModel<BookChallengeViewModel>()

    var numberOfBooks by remember { mutableStateOf(0) }

    val state = viewModel.bookChallengeUIState.collectAsStateWithLifecycle()

    var data by remember {
        mutableStateOf(BookChallengeData())
    }


    state.value.let {
        when(it) {
            is BookChallengeUIState.Loading -> {
                viewModel.loadTotalBooks()
            }
            is BookChallengeUIState.ScreenDataChanged -> {
                data = it.data
            }
            is BookChallengeUIState.Success -> {
                numberOfBooks = it.number
            }
        }
    }

    data class BottomNavigationItem(
        val title: String,
    )

    val navigationItems = listOf(
        BottomNavigationItem(
            title = stringResource(id = R.string.homeNavigation)

            ),
        BottomNavigationItem(
            title = stringResource(id = R.string.statisticsNavigation)
        ),
        BottomNavigationItem(
            title = stringResource(id = R.string.challengeNavigation)
        )
    )
    var selectedNavigationItemIndex by rememberSaveable {
        mutableStateOf(2)
    }

    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Readily", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MainColor
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MainColor,

                ) {
                navigationItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedNavigationItemIndex == index,
                        onClick = {
                            selectedNavigationItemIndex = index
                            if (selectedNavigationItemIndex == 0) {
                                navigationRouter.navigateToMain()
                            }

                            if (selectedNavigationItemIndex == 1) {
                                navigationRouter.navigateToStatistics()
                            }

                            if (selectedNavigationItemIndex == 2) {
                                navigationRouter.navigateToBookChallenge()
                            }

                        },
                        label = {
                            Text(text = item.title)
                        },
                        icon = {
                            Image(painter = painterResource(id = R.drawable.circle), contentDescription = "circle")
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = DarkerMainColor
                        )
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            CustomHeader(text = stringResource(id = R.string.book_challenge_header))
            BookChallengeScreenContent(paddingValues = it, totalBooks = numberOfBooks?: 0, data = data, actions = viewModel, viewModel = viewModel)
        }
    }


}

@Composable
fun BookChallengeScreenContent(paddingValues: PaddingValues, totalBooks: Int, data: BookChallengeData, actions: BookChallengeActions, viewModel: BookChallengeViewModel) {

    val currentYear = Calendar.getInstance().get(Calendar.YEAR)

    Column(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    ) {

        if(data.displayTextField) {
            OutlinedTextField(value = data.wantToRead, onValueChange = {
                actions.wantToReadChanged(it)
            }, label = {
                Text(text = stringResource(id = R.string.want_to_read))
            }, supportingText = {
                Text(text = stringResource(id = R.string.want_to_read_support_text))
            }, keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(8.dp))

            CustomButton(onClick = {
                actions.displayProgressChanged(true)
                actions.displayTextFieldChanged(false)
            }, text = stringResource(id = R.string.save_number))
        }

        if (data.displayProgress) {
            Text(text = stringResource(id = R.string.read_in_year) + " " + currentYear, fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 16.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = stringResource(id = R.string.want_to_read) + ": " + data.wantToRead)
            Text(text = stringResource(id = R.string.total_books) + ": " + totalBooks)
            Spacer(modifier = Modifier.height(8.dp))
            CustomButton(onClick = {
                actions.displayProgressChanged(false)
                actions.displayTextFieldChanged(true)
            }, text = stringResource(id = R.string.edit_number))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                val percentage = (totalBooks.toFloat() / data.wantToRead.toFloat()).coerceIn(0f, 1f)
                CircularProgressBar(percentage = percentage)
            }
        }

    }

}

@Composable
fun CircularProgressBar(percentage: Float) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(150.dp)
    ) {
        CircularProgressIndicator(
            progress = percentage,
            strokeWidth = 10.dp,
            modifier = Modifier.size(100.dp),
            color = MainColor
        )
        Text(
            text = "${(percentage * 100).toInt()} %",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}