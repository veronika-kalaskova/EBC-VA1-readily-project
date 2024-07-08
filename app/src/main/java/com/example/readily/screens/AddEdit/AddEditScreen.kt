package com.example.readily.screens.AddEdit

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.readily.R
import com.example.readily.navigation.INavigationRouter
import com.example.readily.screens.ScreenData
import com.example.readily.ui.elements.CustomButton
import com.example.readily.ui.elements.CustomDatePickerDialog
import com.example.readily.ui.elements.DateField
import com.example.readily.ui.theme.DarkerMainColor
import com.example.readily.ui.theme.MainColor
import com.example.readily.utils.DateUtils
import com.example.readily.utils.LanguageUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditScreen(navigationRouter: INavigationRouter, id: Long?) {

    val viewModel = hiltViewModel<AddEditViewModel>()

    val state = viewModel.addEditUIState.collectAsStateWithLifecycle()

    var data by remember {
        mutableStateOf(ScreenData())
    }
    
    state.value.let {
        when(it) {
            is AddEditUIState.BookDeleted -> {
                LaunchedEffect(it) {
                    navigationRouter.returnBack()
                }
            }
            is AddEditUIState.BookSaved -> {
                LaunchedEffect(it) {
                    navigationRouter.navigateToMain()
                }
            }
            is AddEditUIState.Loading -> {
                viewModel.loadBook(id)
            }
            is AddEditUIState.ScreenDataChanged -> {
                data = it.data
            }
        }
    }

    var selectedImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        data.book.imageUri = uri.toString()  // Aktualizace imageUri při výběru obrázku
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = if (id == null) stringResource(id = R.string.add_book_header) else stringResource(id = R.string.edit_book_header), fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MainColor
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        navigationRouter.returnBack()
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Zpet")
                    }
                }
            )
        }

    ) {
        AddEditScreenContent(paddingValues = it, data = data, actions = viewModel, navigationRouter = navigationRouter, viewModel = viewModel)
    }


}

@Composable
fun AddEditScreenContent(paddingValues: PaddingValues, data: ScreenData, actions: AddEditActions, navigationRouter: INavigationRouter, viewModel: AddEditViewModel) {

    var showDatePicker by remember {
        mutableStateOf(false)
    }

    var showDropdown by remember { mutableStateOf(false) }

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val stars = listOf(stringResource(id = R.string.one_star), stringResource(id = R.string.two_stars), stringResource(id = R.string.three_stars), stringResource(id = R.string.four_stars), stringResource(id = R.string.five_stars))

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        data.book.imageUri = uri.toString()
    }

    val state = rememberScrollState()
    LaunchedEffect(Unit) { state.animateScrollTo(100) }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(16.dp)
            .verticalScroll(state)
    ) {

        textField(value = data.book.name, onValueChange = {
            actions.bookNameChanged(it)
        }, label = stringResource(id = R.string.book_title), clear = {
            actions.bookNameChanged("")
        })

        Spacer(modifier = Modifier.height(8.dp))

        textField(value = data.book.author, onValueChange = {
            actions.bookAuthorChanged(it)
        }, label = stringResource(id = R.string.author_title), clear = {
            actions.bookAuthorChanged("")
        })

        Spacer(modifier = Modifier.height(8.dp))

        textField(value = data.book.genre, onValueChange = {
            actions.bookGenreChanged(it)
        }, label = stringResource(id = R.string.genre), clear = {
            actions.bookGenreChanged("")
        })

        Spacer(modifier = Modifier.height(8.dp))

        DateField(
            icon = Icons.Filled.DateRange,
            hint = stringResource(id = R.string.date),
            value = if (data.book.date != null) DateUtils.getDateString(data.book.date!!) else null,
            onClick = {
                showDatePicker = true
            }
        )

        if (showDatePicker) {
            CustomDatePickerDialog(
                date = data.book.date,
                onDismiss = { showDatePicker = false },
                onDateSelected = {
                    actions.bookDateChanged(it)
                    showDatePicker = false
                }
            )
        }
        
        if (data.book.date != null) {
            Spacer(modifier = Modifier.height(8.dp))
            CustomButton(onClick = { actions.bookDateChanged(null) }, text = stringResource(id = R.string.delete_date))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box{
            OutlinedTextField(
                value = data.book.stars.toString(),
                onValueChange = {  },
                label = { Text(stringResource(id = R.string.number_of_stars)) },
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "",
                        Modifier.clickable { showDropdown = true }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            DropdownMenu(
                expanded = showDropdown,
                onDismissRequest = { showDropdown = false }
            ) {
                stars.forEach { star ->
                    DropdownMenuItem(
                        onClick = {
                            viewModel.bookStarsChanged(returnStarsInt(starsSelected = star))
                            showDropdown = false
                        },
                        text = { Text(text = star) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))


        if (viewModel.formError.value.isNotEmpty()) {
            Text(
                text = viewModel.formError.value,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }


        selectedImageUri?.let { uri ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(uri)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = "",
                modifier = Modifier
                    .size(200.dp)
                    .padding(16.dp)
            )
        }

        CustomButton(onClick = {
            launcher.launch("image/*")
        }, text = stringResource(id = R.string.add_image))

        Button(
            onClick = {
            actions.saveBook()
        },
            colors = ButtonDefaults.buttonColors(containerColor = DarkerMainColor)
        ) {
            Text(text = stringResource(id = R.string.save_book))
        }
    }
}

fun returnStarsInt(starsSelected: String): Int {
    return if (LanguageUtils.isLanguageCloseToCzech()) {
        when (starsSelected.lowercase()) {
            "1 hvězdička" -> 1
            "2 hvězdičky" -> 2
            "3 hvězdičky" -> 3
            "4 hvězdičky" -> 4
            "5 hvězdiček" -> 5
            else -> 0
        }
    } else {
        when (starsSelected.lowercase()) {
            "1 star" -> 1
            "2 stars" -> 2
            "3 stars" -> 3
            "4 stars" -> 4
            "5 stars" -> 5
            else -> 0
        }
    }
}

@Composable
fun textField(value: String, onValueChange: (String) -> Unit, label: String, clear: () -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        trailingIcon = {
            IconButton(onClick = {
                clear()
            }) {
                Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear text")
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}
