package com.muzaffar.filestorage

import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.muzaffar.filestorage.databinding.ActivityWriteExternalBinding
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter

class WriteExternalActivity : AppCompatActivity() {

    private lateinit var binding:ActivityWriteExternalBinding
    val PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityWriteExternalBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.externalSaveBtn.setOnClickListener {
            proceedToWrite()
        }

    }


    private fun proceedToWrite() {
        val fileName = binding.externalFileNameEditText.text.toString()
        val content = binding.externalContentEditText.text.toString()
        writeFileExternalStorage(content, this, fileName)
    }

    private fun writeFileExternalStorage(content: String, context: Context, fileName: String) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // Use MediaStore for Android 10 and above
                val resolver = context.contentResolver
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                    put(MediaStore.MediaColumns.MIME_TYPE, "text/plain")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS)
                }

                val uri = resolver.insert(MediaStore.Files.getContentUri("external"), contentValues)
                uri?.let {
                    resolver.openOutputStream(it).use { outputStream ->
                        OutputStreamWriter(outputStream).use { writer ->
                            writer.append(content)
                        }
                    }
                    Toast.makeText(context, "File written to external memory", Toast.LENGTH_LONG).show()
                } ?: run {
                    Log.e("WriteExternalActivity", "Error creating file in MediaStore")
                    Toast.makeText(context, "Error occurred while writing file", Toast.LENGTH_LONG).show()
                }
            } else {
                // Use app-specific external directory for pre-Android 10
                val appSpecificDir = context.getExternalFilesDir(null)
                val myFile = File(appSpecificDir, fileName)
                FileOutputStream(myFile).use { fOut ->
                    OutputStreamWriter(fOut).use { writer ->
                        writer.append(content)
                    }
                }
                Toast.makeText(context, "File written to app-specific directory", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Log.e("WriteExternalActivity", "Error writing file", e)
            Toast.makeText(context, "Error occurred while writing file", Toast.LENGTH_LONG).show()
        }
    }

}