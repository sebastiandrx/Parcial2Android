package com.example.parcialandroidstudio.items

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.parcialandroidstudio.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleProductoScreen(navController: NavController, viewModel: ProductoViewModel, productoId: Int?) {
    val producto = productoId?.let { id ->
        viewModel.productos.find { it.id == id }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Producto") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->
        producto?.let {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize()
                    .animateContentSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(6.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = it.imagenUrl,
                            contentDescription = "Imagen del producto",
                            modifier = Modifier
                                .height(200.dp)
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            placeholder = painterResource(id = R.drawable.placeholder),
                            error = painterResource(id = R.drawable.imagen_no_disponible)
                        )

                        Text(
                            text = it.nombre,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = it.descripcion,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Precio: ${formatearPrecio(producto.precio)}",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }

                Button(
                    onClick = {
                        viewModel.agregarAlCarrito(it)
                        navController.popBackStack()
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = "Agregar al carrito")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Agregar al carrito")
                }

                OutlinedButton(
                    onClick = { navController.popBackStack() },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Regresar")
                }
            }
        } ?: run {
            Text(
                "Producto no encontrado.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(padding)
                    .padding(24.dp)
            )
        }
    }
}
