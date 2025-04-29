package com.example.parcialandroidstudio.items

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
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
        carrito.add(producto)
    }

    fun obtenerProductoPorId(id: Int): Producto? {
        return productos.find { it.id == id }
    }

    fun totalCarrito(): Double = carrito.sumOf { it.precio }

    fun finalizarCompra() {
        carrito.clear()
    }
}
