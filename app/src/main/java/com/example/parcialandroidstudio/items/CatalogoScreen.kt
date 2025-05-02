package com.example.parcialandroidstudio.items

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.parcialandroidstudio.R
import java.text.NumberFormat
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoScreen(navController: NavController, viewModel: ProductoViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Catálogo de Productos") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)

            ) {
                Text(
                    text = "Total:  ${formatearPrecio(viewModel.totalCarrito())}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = { navController.navigate("carrito") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.height(48.dp)
                ) {
                        Icon(
                            Icons.Default.ShoppingCart,
                            contentDescription = "Carrito de compras",
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        Text("Ver Carrito")

                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("registro") },
                shape = RoundedCornerShape(16.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar producto")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            items(viewModel.productos.size) { index ->
                val producto = viewModel.productos[index]
                ProductoItem(
                    nombre = producto.nombre,
                    precio = producto.precio,
                    imagenUrl = producto.imagenUrl,
                    onClick = {
                        navController.navigate("detalle/${producto.id}")
                    }
                )
            }
        }
    }
}

@Composable
fun ProductoItem(nombre: String, precio: Double, imagenUrl: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }
            .animateContentSize(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            SubcomposeAsyncImage(
                model = imagenUrl,
                contentDescription = "Imagen de $nombre",
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 12.dp),
                loading = {
                    Image(
                        painter = painterResource(id = R.drawable.placeholder),
                        contentDescription = "Cargando imagen",
                        modifier = Modifier.size(80.dp)
                    )
                },
                error = {
                    Image(
                        painter = painterResource(id = R.drawable.imagen_no_disponible),
                        contentDescription = "Imagen no disponible",
                        modifier = Modifier.size(80.dp)
                    )
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Precio: ${formatearPrecio(precio)}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
