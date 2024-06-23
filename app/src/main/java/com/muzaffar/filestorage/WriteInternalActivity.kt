package com.muzaffar.filestorage

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.muzaffar.filestorage.databinding.ActivityWriteInternalBinding

class WriteInternalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWriteInternalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWriteInternalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.internalSaveBtn.setOnClickListener {

            val fileName = binding.internalFileNameEditText.text.toString()
            val content = binding.internalContentEditText.text.toString()

            try {
                val fos = this.openFileOutput(fileName, Context.MODE_PRIVATE)
                fos.write(content.toByteArray())
                fos.flush()
                fos.close()
                Toast.makeText(this, "FIle written to Internal memory",
                    Toast.LENGTH_LONG).show()


            }
            catch(e:Exception) {
                Toast.makeText(this, "Error occured while writing file",
                    Toast.LENGTH_LONG).show()
            }
        }

    }
}