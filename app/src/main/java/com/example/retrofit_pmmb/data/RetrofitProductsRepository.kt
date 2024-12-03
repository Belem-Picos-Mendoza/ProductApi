package com.example.retrofit_pmmb.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class RetrofitProductsRepository(
    private val productApi: ProductApi
) : ProductsRepository {

    override fun getAllProducts(): Flow<List<Product>> = flow {
        try {
            val products = productApi.getAllProducts()
            emit(products)
        } catch (e: IOException) {
            // Manejo de error de red (sin conexión)
            emit(emptyList())
            // Registrar el error o mostrarlo de alguna forma
            println("Error de red: ${e.localizedMessage}")
        } catch (e: HttpException) {
            // Manejo de error HTTP (errores 4xx, 5xx)
            emit(emptyList())
            // Registrar el error o mostrarlo de alguna forma
            println("Error HTTP: ${e.localizedMessage}")
        } catch (e: Exception) {
            // Manejo de errores generales
            emit(emptyList())
            // Registrar el error o mostrarlo de alguna forma
            println("Error desconocido: ${e.localizedMessage}")
        }
    }

    override fun getProductById(id: Int): Flow<Product?> = flow {
        try {
            val product = productApi.getProductById(id)
            emit(product)
        } catch (e: IOException) {
            // Manejo de error de red (sin conexión)
            emit(null)
            println("Error de red: ${e.localizedMessage}")
        } catch (e: HttpException) {
            // Manejo de error HTTP
            emit(null)
            println("Error HTTP: ${e.localizedMessage}")
        } catch (e: Exception) {
            // Manejo de errores generales
            emit(null)
            println("Error desconocido: ${e.localizedMessage}")
        }
    }

    override suspend fun createProduct(product: Product) {
        try {
            productApi.createProduct(product)
        } catch (e: IOException) {
            // Manejo de error de red (sin conexión)
            println("Error de red: ${e.localizedMessage}")
        } catch (e: HttpException) {
            // Manejo de error HTTP
            println("Error HTTP: ${e.localizedMessage}")
        } catch (e: Exception) {
            // Manejo de errores generales
            println("Error desconocido: ${e.localizedMessage}")
        }
    }

    override suspend fun deleteProduct(product: Product) {
        try {
            productApi.deleteProduct(product.id)
        } catch (e: IOException) {
            // Manejo de error de red (sin conexión)
            println("Error de red: ${e.localizedMessage}")
        } catch (e: HttpException) {
            // Manejo de error HTTP
            println("Error HTTP: ${e.localizedMessage}")
        } catch (e: Exception) {
            // Manejo de errores generales
            println("Error desconocido: ${e.localizedMessage}")
        }
    }

    override suspend fun updateProduct(product: Product) {
        try {
            productApi.updateProduct(product.id, product)
        } catch (e: IOException) {
            // Manejo de error de red (sin conexión)
            println("Error de red: ${e.localizedMessage}")
        } catch (e: HttpException) {
            // Manejo de error HTTP
            println("Error HTTP: ${e.localizedMessage}")
        } catch (e: Exception) {
            // Manejo de errores generales
            println("Error desconocido: ${e.localizedMessage}")
        }
    }
}