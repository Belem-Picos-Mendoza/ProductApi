package com.example.retrofit_pmmb.data

/**
 * Contenedor de aplicaciones para inyección de dependencias.
 */
interface AppContainer {
    val productsRepository: ProductsRepository
}

/**
 * [AppContainer] implementación que proporciona instancia de [ProductApi] usando Retrofit.
 */
class AppDataContainer : AppContainer {
    /**
     * Implementación para [ProductsRepository] usando Retrofit.
     */
    override val productsRepository: ProductsRepository by lazy {
        RetrofitProductsRepository(RetrofitInstance.productApi)
    }
}