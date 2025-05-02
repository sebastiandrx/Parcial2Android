package com.example.parcialandroidstudio.items

data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Double,
    val descripcion: String,
    val imagenUrl: String,
    var cantidad: Int = 1
)
