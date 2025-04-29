package com.example.parcialandroidstudio.items

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.parcialandroidstudio.items.ProductoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(navController: NavController, viewModel: ProductoViewModel) {
    var mostrarConfirmacion by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Carrito de Compras") })
        }
    ) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .padding(16.dp)) {

            if (viewModel.carrito.isEmpty()) {
                Text("No hay productos en el carrito.")
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(viewModel.carrito.size) { index ->
                        val producto = viewModel.carrito[index]
                        Text("- ${producto.nombre} ($${producto.precio})")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Total a pagar: $${viewModel.totalCarrito()}",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = {
                        mostrarConfirmacion = true
                    }) {
                        Text("Finalizar Compra")
                    }

                    OutlinedButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Text("Regresar")
                    }
                }
            }

            if (mostrarConfirmacion) {
                AlertDialog(
                    onDismissRequest = { mostrarConfirmacion = false },
                    title = { Text("Compra realizada") },
                    text = { Text("¡Gracias por tu compra!") },
                    confirmButton = {
                        TextButton(onClick = {
                            viewModel.finalizarCompra()
                            mostrarConfirmacion = false
                            navController.popBackStack()
                        }) {
                            Text("Aceptar")
                        }
                    }
                )
            }
        }
    }
}
