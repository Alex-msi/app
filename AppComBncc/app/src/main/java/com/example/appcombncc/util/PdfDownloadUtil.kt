package com.example.appcombncc.util

import android.content.Context
import android.os.Environment
import java.io.File
import java.io.FileOutputStream

object PdfDownloadUtil {
    fun salvarAssetEmDownloads(context: Context, nomeArquivo: String): Result<String> {
        val downloadsDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val destino = File(downloadsDir, nomeArquivo)

        return runCatching {
            context.assets.open(nomeArquivo).use { input ->
                FileOutputStream(destino).use { output ->
                    input.copyTo(output)
                }
            }
            destino.absolutePath
        }
    }
}
