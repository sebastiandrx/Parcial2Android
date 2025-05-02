package com.example.parcialandroidstudio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.parcialandroidstudio.items.CatalogoScreen
import com.example.parcialandroidstudio.items.RegistroProductoScreen
import com.example.parcialandroidstudio.items.DetalleProductoScreen
import com.example.parcialandroidstudio.items.CarritoScreen
import com.example.parcialandroidstudio.items.ProductoViewModel
import com.example.parcialandroidstudio.ui.theme.ParcialAndroidStudioTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ParcialAndroidStudioTheme {
                val navController = rememberNavController()
                val productoViewModel: ProductoViewModel = viewModel()

                NavHost(navController, startDestination = "catalogo") {
                    composable("catalogo") {
                        CatalogoScreen(navController, productoViewModel)
                    }
                    composable("registro") {
                        RegistroProductoScreen(navController, productoViewModel)
                    }
                    composable("detalle/{productoId}") { backStackEntry ->
                        val id = backStackEntry.arguments?.getString("productoId")?.toIntOrNull()
                        DetalleProductoScreen(navController, productoViewModel, id)
                    }
                    composable("carrito") {
                        CarritoScreen(navController, productoViewModel)
                    }
                }
            }
        }

    }
}