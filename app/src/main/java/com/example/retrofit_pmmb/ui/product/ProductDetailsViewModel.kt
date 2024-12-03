package com.example.retrofit_pmmb.ui.product

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofit_pmmb.data.ProductApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * ViewModel para recuperar, actualizar y eliminar un elemento a través de la API de Retrofit.
 */
class ProductDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val productApi: ProductApi
) : ViewModel() {

    private val proudctId: Int = checkNotNull(savedStateHandle[ProductDetailsDestination.productIdArg])

    private val _uiState = MutableStateFlow(ProductDetailsUiState())
    val uiState: StateFlow<ProductDetailsUiState> = _uiState.asStateFlow()

    init {
        // Cargar los detalles del empleado al inicializar el ViewModel
        viewModelScope.launch {
            try {
                val employee = productApi.getProductById(proudctId)
                _uiState.value = ProductDetailsUiState(
                    outOfStock = employee.price <= 0,
                    productDetails = employee.toProductDetails()
                )
            } catch (e: Exception) {
                // Manejar errores de la API
                _uiState.value = ProductDetailsUiState(error = "Error al cargar empleado: ${e.message}")
            }
        }
    }

    suspend fun deleteProduct() {
        viewModelScope.launch {
            try {
                productApi.deleteProduct(proudctId)
                _uiState.value = ProductDetailsUiState(successMessage = "Empleado eliminado con éxito")
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = "Error al eliminar empleado: ${e.message}")
            }
        }
    }
}

/**
 * Estado de la interfaz de usuario para EmployeeDetailsScreen
 */
data class ProductDetailsUiState(
    val outOfStock: Boolean = true,
    val productDetails: ProductDetails = ProductDetails(),
    val error: String? = null,
    val successMessage: String? = null
)