package com.example.retrofit_pmmb.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.retrofit_pmmb.ui.home.HomeDestination
import com.example.retrofit_pmmb.ui.home.HomeScreen
import com.example.retrofit_pmmb.ui.product.ProductDetailsDestination
import com.example.retrofit_pmmb.ui.product.ProductDetailsScreen
import com.example.retrofit_pmmb.ui.product.ProductEditDestination
import com.example.retrofit_pmmb.ui.product.ProductEditScreen
import com.example.retrofit_pmmb.ui.product.ProductEntryDestination
import com.example.retrofit_pmmb.ui.product.ProductEntryScreen

/**
 * Proporciona un gráfico de navegación para la aplicación.
 */
@Composable
fun ProductsNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToProductEntry = { navController.navigate(ProductEntryDestination.route) },
                navigateToProductUpdate = {
                    navController.navigate("${ProductDetailsDestination.route}/${it}")
                }
            )
        }
        composable(route = ProductEntryDestination.route) {
            ProductEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(
            route = ProductDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(ProductDetailsDestination.productIdArg) {
                type = NavType.IntType
            })
        ) {
            ProductDetailsScreen(
                navigateToEditItem = { navController.navigate("${ProductEditDestination.route}/$it") },
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(
            route = ProductEditDestination.routeWithArgs,
            arguments = listOf(navArgument(ProductEditDestination.productIdArg) {
                type = NavType.IntType
            })
        ) {
            ProductEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}