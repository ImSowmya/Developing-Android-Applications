package com.example.cse225

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class ImageReceiverEx : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_receiver_ex)

        val imageS = findViewById<ImageView>(R.id.imageS)
        val bundle:Bundle? = intent.extras
        val id = bundle?.get("img")

        imageS.setImageURI(id as Uri?)

    }
}