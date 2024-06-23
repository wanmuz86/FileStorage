package com.muzaffar.filestorage

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle

import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.muzaffar.filestorage.databinding.ActivityReadExternalBinding

import java.io.File
import java.io.IOException

class ReadExternalActivity : AppCompatActivity() {

    private lateinit var binding : ActivityReadExternalBinding;
    private val PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1

    val fileName = "ExtFile.txt" // The book hardcode the filename to ExtFile.txt
    // AS homework retrieve the filename from an EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadExternalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
                )
            } else {
                readFileAndDisplay()
            }
        } else {
            // For Android 10 and above, no permission needed for app-specific directories
            readFileAndDisplay()
        }


    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readFileAndDisplay()
            } else {
                Toast.makeText(this, "Permission required to read file", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun readFileAndDisplay() {
        try {
            val fileContent = readFileFromExternalStorage(this, fileName)
            binding.externalMessageTextView.text = fileContent
        } catch (e: Exception) {
            Log.e("ReadExternalActivity", "Error reading file", e)
            Toast.makeText(this, "Error reading file", Toast.LENGTH_SHORT).show()
        }
    }

    fun readFileFromExternalStorage(context: Context, filename: String): String {
        var result = ""
        try {
            // Check for Android 10 and above
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val resolver = context.contentResolver
                val uri = MediaStore.Files.getContentUri("external")
                val selection = "${MediaStore.Files.FileColumns.DISPLAY_NAME}=?"
                val selectionArgs = arrayOf(filename)
                val projection = arrayOf(MediaStore.Files.FileColumns._ID)

                resolver.query(uri, projection, selection, selectionArgs, null)?.use { cursor ->
                    if (cursor.moveToFirst()) {
                        val id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID))
                        val contentUri = MediaStore.Files.getContentUri("external", id)
                        resolver.openInputStream(contentUri)?.bufferedReader().use {
                            result = it?.readText() ?: ""
                        }
                    } else {
                        Log.e("readFile", "File not found in MediaStore")
                    }
                }
            } else {
                // For Android 9 and below
                val sdcard = context.getExternalFilesDir(null)
                val file = File(sdcard, filename)
                if (file.exists()) {
                    result = file.bufferedReader().use { it.readText() }
                } else {
                    Log.e("readFile", "File not found in external storage")
                }
            }
        } catch (e: IOException) {
            Log.e("readFile", "Error reading file", e)
        }

        return result
    }
}