package com.example.retrofit_pmmb.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofit_pmmb.data.Product
import com.example.retrofit_pmmb.data.ProductApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para recuperar todos los empleados desde la API usando Retrofit.
 */
class HomeViewModel(private val productApi: ProductApi) : ViewModel() {

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            try {
                // Realizamos la llamada suspendida y obtenemos la lista de empleados directamente
                val products = productApi.getAllProducts()

                // Actualizamos el estado con los empleados obtenidos
                _homeUiState.value = HomeUiState(productsList = products)
            } catch (e: Exception) {
                // Manejamos cualquier error que ocurra durante la llamada
                _homeUiState.value = HomeUiState(error = "Error: ${e.message}")
            }
        }
    }
}

/**
 * Ui State para HomeScreen
 */
data class HomeUiState(
    val productsList: List<Product> = listOf(),
    val error: String? = null
)