package com.example.retrofit_pmmb

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.retrofit_pmmb.R.string
import com.example.retrofit_pmmb.ui.navigation.ProductsNavHost

/**
 * Elemento componible de nivel superior que representa las pantallas de la aplicación.
 */
@Composable
fun ProductsApp(navController: NavHostController = rememberNavController()) {
    ProductsNavHost(navController = navController)
}

/**
 * Barra de la aplicación para mostrar el título y mostrar condicionalmente la navegación hacia atrás.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(string.back_button)
                    )
                }
            }
        }
    )
}