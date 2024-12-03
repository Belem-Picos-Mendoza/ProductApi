package com.example.retrofit_pmmb

import android.app.Application
import com.example.retrofit_pmmb.data.AppContainer
import com.example.retrofit_pmmb.data.AppDataContainer

class ProductsApplication : Application() {

    /**
     * Instancia de AppContainer utilizada por el resto de clases para obtener dependencias.
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer()
    }
}