package com.example.retrofit_pmmb.ui.navigation

/**
 * Interfaz para describir los destinos de navegación de la aplicación
 */
interface NavigationDestination {
    /**
     * Nombre único para definir la ruta de un composable
     */
    val route: String

    /**
     * Cadena que contiene el título que se mostrará en la pantalla.
     */
    val titleRes: Int
}