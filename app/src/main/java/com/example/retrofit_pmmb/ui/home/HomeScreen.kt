package com.example.retrofit_pmmb.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.retrofit_pmmb.ProductsTopAppBar
import com.example.retrofit_pmmb.R
import com.example.retrofit_pmmb.data.Product
import com.example.retrofit_pmmb.ui.AppViewModelProvider
import com.example.retrofit_pmmb.ui.product.formatedPrice
import com.example.retrofit_pmmb.ui.navigation.NavigationDestination
import com.example.retrofit_pmmb.ui.theme.ProductTheme

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToProductEntry: () -> Unit,
    navigateToProductUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ProductsTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToProductEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(
                        end = WindowInsets.safeDrawing.asPaddingValues()
                            .calculateEndPadding(LocalLayoutDirection.current)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.item_entry_title)
                )
            }
        },
    ) { innerPadding ->
        when {
            homeUiState.error != null -> {
                ErrorScreen(
                    errorMessage = homeUiState.error.toString(),
                    onRetry = { /* recargar todo */ },
                    modifier = Modifier.padding(innerPadding)
                )
            }
            homeUiState.productsList.isEmpty() -> {
                EmptyStateScreen(
                    modifier = Modifier.padding(innerPadding)
                )
            }
            else -> {
                ProductList(
                    productsList = homeUiState.productsList,
                    onItemClick = navigateToProductUpdate,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

@Composable
private fun ProductList(
    productsList: List<Product>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Usamos un índice único para asegurar que la clave sea única.
        itemsIndexed(
            items = productsList,
            key = { index, employee -> employee.id } // Usamos el id del empleado como clave única
        ) { _, product ->
            ProductCard(
                product = product,
                onClick = { onItemClick(product.id) }
            )
        }
    }
}

@Composable
private fun ProductCard(
    product: Product,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // ID del producto
            Text(
                text = "${stringResource(R.string.item_identificador)}: ${product.id}",
                style = MaterialTheme.typography.bodyMedium
            )
            // Nombre del producto
            Text(
                text = "${stringResource(R.string.item_nombre)}: ${product.name}",
                style = MaterialTheme.typography.titleLarge
            )
            // Descripción del producto
            Text(
                text = "${stringResource(R.string.item_description)}: ${product.description}",
                style = MaterialTheme.typography.bodyMedium
            )
            // Categoría del producto
            Text(
                text = "${stringResource(R.string.item_category)}: ${product.category}",
                style = MaterialTheme.typography.bodyMedium
            )
            // Precio del producto
            Text(
                text = "${stringResource(R.string.item_price)}: ${product.formatedPrice()}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            // Cantidad disponible
            Text(
                text = stringResource(R.string.disponibilidad, product.quantity),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun ErrorScreen(
    errorMessage: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = errorMessage,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text(text = stringResource(R.string.back_button))
        }
    }
}

@Composable
fun EmptyStateScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.no_item_description),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    ProductTheme {
        HomeScreen(
            navigateToProductEntry = {},
            navigateToProductUpdate = {}
        )
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewEmployeeCard() {
    ProductTheme {
        ProductCard(
            product = Product(
                id = 1,
                name = "John",
                description = "Doe",
                category = "Software Engineer",
                quantity = 5,
                price = 6000
            ),
            onClick = {}
        )
    }
}
