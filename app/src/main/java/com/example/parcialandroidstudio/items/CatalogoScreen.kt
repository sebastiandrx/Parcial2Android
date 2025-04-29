package com.example.parcialandroidstudio.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.parcialandroidstudio.R
import com.example.parcialandroidstudio.items.ProductoViewModel

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
            BottomAppBar {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total: $${viewModel.totalCarrito()}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Button(onClick = { navController.navigate("carrito") }) {
                        Text("Ver Carrito")
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("registro") },
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("+")
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
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            SubcomposeAsyncImage(
                model = imagenUrl,
                contentDescription = "Imagen de $nombre",
                modifier = Modifier.size(80.dp),
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

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(nombre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text("Precio: $${precio}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
