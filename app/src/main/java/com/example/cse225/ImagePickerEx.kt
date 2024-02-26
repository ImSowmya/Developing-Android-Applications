package com.example.cse225

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import android.net.Uri

class ImagePickerEx : AppCompatActivity() {
    var imageUri:Uri? = null
    lateinit var getImage: ActivityResultLauncher<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_picker_ex)

        val imageV = findViewById<ImageView>(R.id.imageV)
        val btnSelect = findViewById<Button>(R.id.BtnSelect)
        val btnSend = findViewById<Button>(R.id.button4)

        getImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                imageUri=it
                imageV.setImageURI(it)
            })

        btnSend.setOnClickListener {
            val intent = Intent(this, ImageReceiverEx::class.java)
            intent.putExtra("img", imageUri)
            startActivity(intent)
        }

        btnSelect.setOnClickListener {
            getImage.launch("image/*")
        }

    }
}