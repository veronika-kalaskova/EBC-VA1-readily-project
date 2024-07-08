package com.example.readily.screens.Detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.readily.R
import com.example.readily.navigation.INavigationRouter
import com.example.readily.screens.ScreenData
import com.example.readily.ui.elements.CustomButton
import com.example.readily.ui.elements.CustomHeader
import com.example.readily.ui.theme.MainColor
import com.example.readily.utils.DateUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navigationRouter: INavigationRouter, id: Long?) {

    val viewModel = hiltViewModel<DetailViewModel>()

    val state = viewModel.detailUIState.collectAsStateWithLifecycle()

    var data by remember {
        mutableStateOf(ScreenData())
    }

    state.value.let {
        when(it) {
            is DetailUIState.BookDeleted -> {
                LaunchedEffect(it) {
                    navigationRouter.returnBack()
                }
            }
            is DetailUIState.Loading -> {
                viewModel.loadBook(id)
            }
            is DetailUIState.ScreenDataChanged -> {
                data = it.data
            }
        }
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.book_detail_title), fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MainColor
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        navigationRouter.returnBack()
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Zpet")
                    }
                },
                actions = {
                    if (id != null) {
                        IconButton(onClick = {
                            navigationRouter.navigateToAddEdit(id)
                        }) {
                            Icon(imageVector = Icons.Outlined.Edit, contentDescription = "Upravit")
                        }
                    }
                }
            )
        }

    ) {
        Column (
            modifier = Modifier.padding(it)
        ) {
            CustomHeader(text = data.book.name)
            DetailScreenContent(paddingValues = it, data = data, actions = viewModel)
        }
    }


}

@Composable
fun DetailScreenContent(paddingValues: PaddingValues, data: ScreenData, actions: DetailActions) {

    Column(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    ) {

        Text(text = "${data.book.author}, ${data.book.genre}", fontWeight = FontWeight.Bold, fontSize = 22.sp)

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Text(text = if (data.book.date != null) stringResource(id = R.string.read_date) + ": " else stringResource(id = R.string.date_not_set), fontWeight = FontWeight.Bold)
            Text(text = if (data.book.date != null) DateUtils.getDateString(data.book.date!!) else "", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row (
            modifier = Modifier.padding(paddingValues),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(data.book.stars) {
                Icon(imageVector = Icons.Default.Star, contentDescription = "pocet hvezd")
            }

            Spacer(modifier = Modifier.weight(1.0f))
            
            CustomButton(onClick = {
                actions.deleteBook()
            }, text = stringResource(id = R.string.delete_book))
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (data.book.imageUri != null) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(data.book.imageUri)
                    .crossfade(true)
                    .build(),
                contentDescription = "image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
        } else { }


    }
}