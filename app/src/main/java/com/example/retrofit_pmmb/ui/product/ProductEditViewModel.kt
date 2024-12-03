package com.example.retrofit_pmmb.ui.product

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofit_pmmb.data.ProductApi
import kotlinx.coroutines.launch

/**
 * ViewModel para recuperar y actualizar un empleado mediante Retrofit.
 */
class ProductEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val productApi: ProductApi
) : ViewModel() {

    var productUiState by mutableStateOf(ProductUiState())
        private set

    private val productId: Int = checkNotNull(savedStateHandle[ProductEditDestination.productIdArg])

    init {
        viewModelScope.launch {
            try {
                val employee = productApi.getProductById(productId)
                productUiState = employee?.toProductDetails()?.let {
                    ProductUiState(productDetails = it, isEntryValid = validateInput(it))
                } ?: productUiState.copy(error = "Empleado no encontrado.")
            } catch (e: Exception) {
                productUiState = productUiState.copy(error = "Error al recuperar empleado: ${e.message}")
            }
        }
    }

    fun updateEmployee() {
        if (validateInput(productUiState.productDetails)) {
            viewModelScope.launch {
                try {
                    val employee = productUiState.productDetails.toProduct()
                    productApi.updateProduct(employee.id, employee)
                    productUiState = productUiState.copy(successMessage = "Empleado actualizado exitosamente.")
                } catch (e: Exception) {
                    productUiState = productUiState.copy(error = "Error al actualizar empleado: ${e.message}")
                }
            }
        }
    }

    fun updateUiState(employeeDetails: ProductDetails) {
        productUiState = ProductUiState(
            productDetails = employeeDetails,
            isEntryValid = validateInput(employeeDetails)
        )
    }

    private fun validateInput(uiState: ProductDetails): Boolean {
        return uiState.name.isNotBlank() && uiState.description.isNotBlank() &&
                uiState.category.isNotBlank() && uiState.price.isNotBlank() &&
                uiState.quantity.isNotBlank()
    }
}