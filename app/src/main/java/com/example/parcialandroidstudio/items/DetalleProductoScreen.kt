package com.example.parcialandroidstudio.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.parcialandroidstudio.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleProductoScreen(
    navController: NavController,
    viewModel: ProductoViewModel,
    productoId: Int?
) {
    val producto = productoId?.let { viewModel.obtenerProductoPorId(it) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Detalle del Producto") })
        }
    ) { padding ->
        if (producto == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Producto no encontrado", color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { navController.popBackStack() }) {
                    Text("Regresar")
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                // Usando AsyncImage con placeholder y error
                AsyncImage(
                    model = producto.imagenUrl,
                    contentDescription = "Imagen del producto",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    placeholder = painterResource(id = R.drawable.placeholder),
                    error = painterResource(id = R.drawable.imagen_no_disponible)
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text("Nombre: ${producto.nombre}", style = MaterialTheme.typography.titleMedium)
                Text("Precio: $${producto.precio}", style = MaterialTheme.typography.bodyMedium)
                Text("Descripción: ${producto.descripcion}", style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(16.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = {
                        viewModel.agregarAlCarrito(producto)
                        navController.popBackStack()
                    }) {
                        Text("Agregar al Carrito")
                    }

                    OutlinedButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Text("Regresar")
                    }
                }
            }
        }
    }
}