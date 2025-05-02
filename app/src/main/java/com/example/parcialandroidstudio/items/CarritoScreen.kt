package com.example.parcialandroidstudio.items

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import androidx.compose.ui.res.painterResource
import com.example.parcialandroidstudio.R
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(navController: NavController, viewModel: ProductoViewModel) {
    var mostrarConfirmacion by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Carrito de Compras") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            if (viewModel.carrito.isEmpty()) {
                Text("No hay productos en el carrito.", style = MaterialTheme.typography.bodyMedium)
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(viewModel.carrito.size) { index ->
                        val producto = viewModel.carrito[index]
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(4.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateContentSize(),
                                    colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surface
                                    )
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(12.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    model = producto.imagenUrl,
                                    contentDescription = "Imagen del producto",
                                    modifier = Modifier
                                        .size(80.dp)
                                        .padding(end = 12.dp),
                                    placeholder = painterResource(id = R.drawable.placeholder),
                                    error = painterResource(id = R.drawable.imagen_no_disponible)
                                )

                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(producto.nombre, style = MaterialTheme.typography.titleMedium)
                                    Text("Precio: $${producto.precio}", style = MaterialTheme.typography.bodyMedium)
                                    Text("Cantidad: ${producto.cantidad}", style = MaterialTheme.typography.bodySmall)
                                }

                                IconButton(onClick = {
                                    val mensaje = viewModel.eliminarDelCarrito(producto)
                                    scope.launch {
                                        snackbarHostState.showSnackbar(mensaje)
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Eliminar del carrito"
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Total a pagar: ${formatearPrecio(viewModel.totalCarrito())}",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { mostrarConfirmacion = true },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Check, contentDescription = "Finalizar")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Finalizar Compra")
                    }

                    Button(
                        onClick = { navController.popBackStack() },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        )
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                        Spacer(modifier = Modifier.width(4.dp))
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
                        TextButton(
                            onClick = {
                                viewModel.finalizarCompra()
                                mostrarConfirmacion = false
                                navController.popBackStack()
                            }
                        ) {
                            Text("Aceptar")
                        }
                    }
                )
            }
        }
    }
}
fun formatearPrecio(precio: Double): String {
    val formatoColombiano = NumberFormat.getCurrencyInstance(Locale("es", "CO"))
    return formatoColombiano.format(precio)
}
