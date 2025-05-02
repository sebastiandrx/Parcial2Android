package com.example.parcialandroidstudio.items

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import java.util.Locale

import com.example.parcialandroidstudio.items.Producto

class ProductoViewModel: ViewModel() {
    private var nextId = 1
    var productos = mutableStateListOf<Producto>()
        private set

    var carrito = mutableStateListOf<Producto>()
        private set

    fun agregarProducto(nombre: String, precio: Double, descripcion: String, imagenUrl: String) {
        productos.add(Producto(nextId++, nombre, precio, descripcion, imagenUrl))
    }

    fun agregarAlCarrito(producto: Producto) {
        val existente = carrito.find { it.id == producto.id }
        if (existente != null) {
            existente.cantidad += 1
        } else {
            carrito.add(producto.copy(cantidad = 1))
        }
    }
    fun eliminarDelCarrito(producto: Producto): String {
        val index = carrito.indexOfFirst { it.nombre == producto.nombre }
        return if (index != -1) {
            if (carrito[index].cantidad > 1) {
                carrito[index] = carrito[index].copy(cantidad = carrito[index].cantidad - 1)
                "Se disminuyó la cantidad de ${producto.nombre}."
            } else {
                carrito.removeAt(index)
                "${producto.nombre} fue eliminado del carrito."
            }
        } else {
            "El producto no se encontró en el carrito."
        }
    }

    fun obtenerProductoPorId(id: Int): Producto? {
        return productos.find { it.id == id }
    }

    fun totalCarrito(): Double {
        return carrito.sumOf { it.precio * it.cantidad }
    }

    fun finalizarCompra() {
        carrito.clear()
    }

    fun formatearPrecio(precio: Double): String {
        val formatoColombiano = NumberFormat.getCurrencyInstance(Locale("es", "CO"))
        return formatoColombiano.format(precio)
    }

}
