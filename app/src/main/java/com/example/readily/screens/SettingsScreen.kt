package com.example.readily.screens

import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.readily.R
import com.example.readily.model.Book
import com.example.readily.navigation.INavigationRouter
import com.example.readily.ui.elements.CustomHeader
import com.example.readily.ui.theme.DarkerMainColor
import com.example.readily.ui.theme.MainColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navigationRouter: INavigationRouter) {


    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Readily", fontWeight = FontWeight.Bold) },
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
        Column(
            modifier = Modifier.padding(it)
        ) {
            CustomHeader(text = stringResource(id = R.string.settings))
            SettingsContent()
        }
    }
}

@Composable
fun SettingsContent() {
    Column(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    ) {
        val context = LocalContext.current
        val versionName = getAppVersionName(context)
        Text(text = stringResource(id = R.string.version) + ": " + versionName)
    }
}

fun getAppVersionName(context: Context): String {
    return try {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        packageInfo.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        "Unknown"
    }
}