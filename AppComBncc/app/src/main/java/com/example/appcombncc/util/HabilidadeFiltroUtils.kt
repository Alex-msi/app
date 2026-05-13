package com.example.appcombncc.util

object HabilidadeFiltroUtils {

    fun gerarHabilidadeLikePorSerie(serieCodigo: String): String {
        return when (serieCodigo) {
            "1_ANO" -> "EF01%"
            "2_ANO" -> "EF02%"
            "3_ANO" -> "EF03%"
            "4_ANO" -> "EF04%"
            "5_ANO" -> "EF05%"
            "6_ANO" -> "EF06%"
            "7_ANO" -> "EF07%"
            "8_ANO" -> "EF08%"
            "9_ANO" -> "EF09%"
            else -> ""
        }
    }
}