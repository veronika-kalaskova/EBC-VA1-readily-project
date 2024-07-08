package com.example.readily.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.readily.R
import com.example.readily.model.Book
import com.example.readily.navigation.INavigationRouter
import com.example.readily.screens.AddEdit.AddEditActions
import com.example.readily.ui.elements.CustomHeader
import com.example.readily.ui.theme.DarkerMainColor
import com.example.readily.ui.theme.MainColor
import com.example.readily.utils.DateUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navigationRouter: INavigationRouter) {

    val viewModel = hiltViewModel<MainViewModel>()

    val books: MutableList<Book> = mutableListOf()

    val state = viewModel.mainUIState.collectAsStateWithLifecycle()

    state.value.let {
        when(it) {
            is MainUIState.Loading -> {
                viewModel.loadBooks()
            }
            is MainUIState.Success -> {
                books.addAll(it.books)
            }
        }
    }

    data class BottomNavigationItem(
        val title: String,
    )

    val navigationItems = listOf(
        BottomNavigationItem(
            title = stringResource(id = R.string.homeNavigation),

        ),
        BottomNavigationItem(
            title = stringResource(id = R.string.statisticsNavigation),
        ),
        BottomNavigationItem(
            title = stringResource(id = R.string.challengeNavigation),
        )
    )
    var selectedNavigationItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Readily", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MainColor
                ),
                actions = {
                    IconButton(onClick = { navigationRouter.navigateToSettings() }) {
                        Icon(imageVector = Icons.Outlined.Info, contentDescription = "info")
                    }
                },
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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                navigationRouter.navigateToAddEdit(null)
            },
                containerColor = MainColor
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
            }
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            CustomHeader(text = stringResource(id = R.string.main_screen_header))
            MainScreenContent(actions = viewModel, books = books, navigationRouter = navigationRouter)
        }
    }


}

@Composable
fun MainScreenContent(actions: MainViewModel, books: List<Book>, navigationRouter: INavigationRouter) {


    if (books.isNotEmpty()) {
        LazyColumn {

            books.forEach {

                item {
                    ListItem(
                        headlineContent = {
                            Text(text = it.name, fontWeight = FontWeight.Bold)
                        },
                        leadingContent = {
                            if (it.imageUri != null) {

                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(Uri.parse(it.imageUri))
                                        .crossfade(true)
                                        .build(),
                                    contentScale = ContentScale.Crop,
                                    contentDescription = "",
                                    modifier = Modifier.size(56.dp)
                                )
                            } else { }
                        },
                        supportingContent = {
                            Column {
                                Text(text = stringResource(id = R.string.genre) + ": " + it.genre)
                                Row {
                                    Text(text = if (it.date != null) stringResource(id = R.string.read_date) + ": " else stringResource(id = R.string.date_not_set))
                                    Text(text = if (it.date != null) DateUtils.getDateString(it.date!!) else "")
                                }
                                Row {
                                    repeat(it.stars) {
                                        Icon(imageVector = Icons.Default.Star, contentDescription = "pocet hvezd")
                                    }
                                }

                            }
                        },
                        modifier = Modifier.clickable {
                            navigationRouter.navigateToDetail(it.id)
                        }
                    )
                }
            }
        }
    } else {
        Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
            Text(text = stringResource(id = R.string.empty_list))
        }

    }



}