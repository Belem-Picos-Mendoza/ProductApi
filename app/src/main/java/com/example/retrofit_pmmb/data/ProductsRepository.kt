package com.example.retrofit_pmmb.data

import kotlinx.coroutines.flow.Flow

/**
 * Repositorio que proporciona inserción, actualización, borrado y recuperación de [Product] desde una fuente de datos dada.
 */
interface ProductsRepository {
    /**
     * Recupera todos los elementos de la base de datos dada.
     */
    fun getAllProducts(): Flow<List<Product>>

    /**
     * Recuperar un empleado de la base de datos dada que coincida con el [id].
     */
    fun getProductById(id: Int): Flow<Product?>

    /**
     * Insertar elemento en la base de datos
     */
    suspend fun createProduct(product: Product)

    /**
     * Borrar elemento de la base de datos
     */
    suspend fun deleteProduct(product: Product)

    /**
     * Actualizar elemento en la base de datos
     */
    suspend fun updateProduct(product: Product)
}