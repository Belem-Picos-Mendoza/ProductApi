package com.example.retrofit_pmmb.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofit_pmmb.data.Product
import com.example.retrofit_pmmb.data.ProductApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.NumberFormat

class ProductEntryViewModel(
    private val productApi: ProductApi
) : ViewModel() {

    // Cambiar a StateFlow en lugar de mutableStateOf
    private val _productUiState = MutableStateFlow(ProductUiState())
    val productUiState: StateFlow<ProductUiState> = _productUiState

    // Suponiendo que tienes un método para obtener todos los empleados actuales
    private suspend fun getAllProducts(): List<Product> {
        return productApi.getAllProducts() // Asegúrate de tener este método en tu API
    }

    fun updateUiState(productDetails: ProductDetails) {
        _productUiState.value = ProductUiState(
            productDetails = productDetails,
            isEntryValid = validateInput(productDetails),
            successMessage = "", // Limpiar mensaje de éxito cuando se actualiza el estado
            error = "" // Limpiar error al actualizar
        )
    }

    fun saveProduct() {
        if (validateInput()) {
            viewModelScope.launch {
                try {
                    val currentProducts = getAllProducts() // Obtener lista de empleados actuales
                    val maxId = currentProducts.maxOfOrNull { it.id } ?: 0 // Obtener el ID más alto
                    val newProductId = maxId + 1 // Asignar el siguiente ID

                    // Crear el empleado con el nuevo ID
                    val productDetails = _productUiState.value.productDetails.copy(id = newProductId)
                    val product = productDetails.toProduct()

                    // Guardar el nuevo empleado
                    productApi.createProduct(product)

                    _productUiState.value = _productUiState.value.copy(
                        successMessage = "Producto guardado exitosamente.",
                        error = "" // Limpiar mensaje de error en caso de éxito
                    )
                } catch (e: Exception) {
                    _productUiState.value = _productUiState.value.copy(
                        successMessage = "", // Limpiar éxito en caso de error
                        error = "Error al guardar Producto: ${e.message}"
                    )
                }
            }
        } else {
            _productUiState.value = _productUiState.value.copy(error = "Por favor complete todos los campos correctamente.")
        }
    }

    private fun validateInput(productDetails: ProductDetails = _productUiState.value.productDetails): Boolean {
        return with(productDetails) {
            name.isNotBlank() && description.isNotBlank() && category.isNotBlank() &&
                    price.isNotBlank() && quantity.isNotBlank() &&
                    price.toIntOrNull() != null && quantity.toIntOrNull() != null
        }
    }
}

/**
 * Representa el estado de la UI de un empleado.
 */
data class ProductUiState(
    val productDetails: ProductDetails = ProductDetails(),
    val isEntryValid: Boolean = false,
    val successMessage: String = "",
    val error: String = ""
)

/**
 * Representa los detalles del empleado en la UI.
 */
data class ProductDetails(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val category: String = "",
    val price: String = "",
    val quantity: String = ""
)

/**
 * Convierte [ProductDetails] en [Product].
 */
fun ProductDetails.toProduct(): Product = Product(
    id = id,
    name = name,
    description = description,
    category = category,
    price = price.toIntOrNull() ?: 0,
    quantity = quantity.toIntOrNull() ?: 0
)

/**
 * Convierte [Product] en [ProductDetails].
 */
fun Product.toProductDetails(): ProductDetails = ProductDetails(
    id = id,
    name = name,
    description = description,
    category = category,
    price = price.toString(),
    quantity = quantity.toString()
)

fun Product.formatedPrice(): String {
    return NumberFormat.getCurrencyInstance().format(price)
}