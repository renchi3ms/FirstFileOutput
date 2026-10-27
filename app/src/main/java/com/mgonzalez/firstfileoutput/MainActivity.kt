package com.mgonzalez.firstfileoutput

import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File
import java.io.FileOutputStream
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.widget.Toast
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val fileName = "firstFile.txt"
        val file = File(this.filesDir, fileName)

        val sendButton = findViewById<Button>(R.id.saveButton)
        val inputUser = findViewById<EditText>(R.id.inputTextUser)
        val outputFile = findViewById<TextView>(R.id.viewContent)

        if (file.exists()) {
            outputFile.text = File(this.filesDir, fileName).readText()
        } else {
            outputFile.text = "Not file exist!"
        }

        sendButton.setOnClickListener{
            val context: Context = this
            var text: String = inputUser.text.toString() + "\n"

            if (text.trim().isEmpty()) {
                Toast.makeText(context, "Not empty entry accept!!!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val fechaString: String = formatter.format(Date())
            text = fechaString + "\n" + "-".repeat(50) + "\n" + text + "=".repeat(50) + "\n\n"
            try {
                context.openFileOutput(fileName, Context.MODE_APPEND).use { ofs ->
                    ofs.write(text.toByteArray())
                }
                inputUser.text.clear()
                outputFile.text = File(context.filesDir, fileName).readText()

                Toast.makeText(context, "New entry saved 'u'", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(context, "Somthing went wrong!! T_T", Toast.LENGTH_SHORT).show()
            }

        }
    }
}