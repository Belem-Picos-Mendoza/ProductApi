package com.example.retrofit_pmmb.data

import retrofit2.http.*

interface ProductApi {

    @GET("Products")
    suspend fun getAllProducts(): List<Product>

    @GET("Products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Product

    @POST("Products")
    suspend fun createProduct(@Body newEmployee: Product): Product

    @PUT("Products/{id}")
    suspend fun updateProduct(
        @Path("id") id: Int,
        @Body updatedEmployee: Product
    ): Product

    @DELETE("Products/{id}")
    suspend fun deleteProduct(@Path("id") id: Int)
}