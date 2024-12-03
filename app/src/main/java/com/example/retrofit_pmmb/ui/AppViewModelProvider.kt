package com.example.retrofit_pmmb.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.retrofit_pmmb.ProductsApplication
import com.example.retrofit_pmmb.data.RetrofitInstance
import com.example.retrofit_pmmb.ui.home.HomeViewModel
import com.example.retrofit_pmmb.ui.product.ProductDetailsViewModel
import com.example.retrofit_pmmb.ui.product.ProductEditViewModel
import com.example.retrofit_pmmb.ui.product.ProductEntryViewModel

/**
 * Proporciona Factory para crear instancias de ViewModel para toda la aplicación Empleados,
 * utilizando RetrofitInstance para todas las operaciones de red.
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Inicializador para EmployeeEditViewModel
        initializer {
            ProductEditViewModel(
                savedStateHandle = createSavedStateHandle(),
                productApi = RetrofitInstance.productApi // Usamos Retrofit para manejar API
            )
        }

        // Inicializador para EmployeeEntryViewModel
        initializer {
            ProductEntryViewModel(
                productApi = RetrofitInstance.productApi // Usamos RetrofitInstance.employeeApi
            )
        }

        // Inicializador para EmployeeDetailsViewModel
        initializer {
            ProductDetailsViewModel(
                savedStateHandle = createSavedStateHandle(),
                productApi = RetrofitInstance.productApi // Usamos RetrofitInstance.employeeApi
            )
        }

        // Inicializador para HomeViewModel
        initializer {
            HomeViewModel(
                productApi = RetrofitInstance.productApi // Usamos RetrofitInstance.employeeApi
            )
        }
    }
}

/**
 * Función de extensión para consultas del objeto [Application] y devuelve una instancia de
 * [ProductsApplication].
 */
fun CreationExtras.employeesApplication(): ProductsApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as ProductsApplication)