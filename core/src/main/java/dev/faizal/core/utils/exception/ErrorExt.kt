package dev.faizal.core.utils.exception


import retrofit2.HttpException
import java.io.IOException

fun Throwable.toUserMessage(): String = when (this) {
    is HttpException -> when (code()) {
        401 -> "Token tidak valid. Periksa API key kamu."
        404 -> "Data tidak ditemukan."
        429 -> "Terlalu banyak permintaan, coba lagi sebentar."
        else -> "Terjadi kesalahan server."
    }
    is IOException -> "Tidak ada koneksi internet."
    else -> message ?: "Terjadi kesalahan tidak diketahui."
}