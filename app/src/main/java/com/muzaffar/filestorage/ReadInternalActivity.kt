package com.muzaffar.filestorage

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.muzaffar.filestorage.databinding.ActivityReadInternalBinding
import java.io.BufferedReader

class ReadInternalActivity : AppCompatActivity() {

    private  lateinit var binding:ActivityReadInternalBinding
    val fileName = "testFile.txt"
    // Homework: pastikan nama fail boleh dimasukkan dari EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadInternalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fileContent = readFileInternalStorage(fileName, this)
        binding.internalMessageTextView.text = fileContent
    }

    private fun readFileInternalStorage(fileName: String, context: Context): String {

        var stringToReturn = ""
        try {
            // Buka fail dengan nama fail yang diberi
            val inputStream = context.openFileInput(fileName)
            // Jika fail ada
            if (inputStream!= null){
                // baca fail dan masukkan di dalam variable stringToReturn
                stringToReturn = inputStream.bufferedReader().use(BufferedReader::readText)
            }

        } catch (e:Exception){
            print(e.message)
        }
        return stringToReturn

    }
}