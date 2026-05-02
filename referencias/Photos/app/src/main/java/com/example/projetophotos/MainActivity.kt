package com.example.projetophotos

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.android.volley.VolleyError
import com.android.volley.VolleyLog.TAG
import com.android.volley.toolbox.ImageRequest
import com.example.projetophotos.adapter.PhotoAdapter
import com.example.projetophotos.databinding.ActivityMainBinding
import com.example.projetophotos.model.Photo
import com.example.projetophotos.model.PlaceHolderJSONAPI

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val photoList: MutableList<Photo> = mutableListOf()
    private val photoAdapter: PhotoAdapter by lazy {
        PhotoAdapter(this, photoList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        // Configurar Toolbar
        setSupportActionBar(amb.mainTb.apply {
            title = "ProjetoPhotos"
            setTitleTextColor(ContextCompat.getColor(context, android.R.color.white))
        })

        // Configurar Spinner
        amb.photoSp.apply {
            adapter = photoAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    retrievePhotoImages(photoList[position])
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Não há ação necessária
                }
            }
        }

        retrievePhotos()
    }

    private fun retrievePhotos() {
        PlaceHolderJSONAPI.PhotoListRequest(
            { photos ->
                photos.also {
                    photoAdapter.addAll(it)
                }
            }, {
                Toast.makeText(this, "Problema na requisição", Toast.LENGTH_SHORT).show()

            }).also {
            PlaceHolderJSONAPI.getInstance(this).addToRequestQueue(it)
        }
    }

    private fun retrievePhotoImages(photo: Photo) {
        // Carrega a imagem thumbnail na primeira ImageView
        ImageRequest(photo.thumbnailUrl, { response ->
            amb.thumbnailImageView.setImageBitmap(response)
        }, 0, 0, ImageView.ScaleType.CENTER, Bitmap.Config.ARGB_8888, {
            Toast.makeText(this, "Problema ao carregar thumbnail", Toast.LENGTH_SHORT).show()
        }).also {
            PlaceHolderJSONAPI.getInstance(this).addToRequestQueue(it)
        }


        // Carregar imagem completa
        ImageRequest(photo.url, { response ->
            amb.fullImageView.setImageBitmap(response)
        }, 0, 0, ImageView.ScaleType.CENTER, Bitmap.Config.ARGB_8888, {
            Toast.makeText(this, "Problema ao carregar imagem", Toast.LENGTH_SHORT).show()
        }).also {
            PlaceHolderJSONAPI.getInstance(this).addToRequestQueue(it)
        }
    }
}