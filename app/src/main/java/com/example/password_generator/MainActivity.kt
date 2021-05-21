package com.example.password_generator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.slider.Slider

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val generate_button = findViewById<Button>(R.id.generate_button)
        val copy_button = findViewById<ImageButton>(R.id.copy_password_icon)
        val length_slider = findViewById<Slider>(R.id.password_length_slider)
        length_slider.addOnChangeListener { slider, value, fromUser ->
            val length_value = findViewById<TextView>(R.id.password_length)
            length_value.text = value.toInt().toString()
        }

        generate_button.setOnClickListener { generate_password() }
        copy_button.setOnClickListener { copy_to_clipboard() }
    }

    private fun generate_password(){
        val upper = findViewById<CheckBox>(R.id.check_uppercase).isChecked()
        val lower = findViewById<CheckBox>(R.id.check_lowercase).isChecked()
        val num = findViewById<CheckBox>(R.id.check_numbers).isChecked()
        val sym = findViewById<CheckBox>(R.id.check_symbols).isChecked()
        val password_length = findViewById<Slider>(R.id.password_length_slider).value


        val available_chars = get_available_chars(upper, lower, num, sym)

        var password = ""
        for (i in 1..password_length.toInt()) {
            password += available_chars.random()
        }

        val result = findViewById<TextView>(R.id.password)
        result.text = password
    }

    private fun get_available_chars(upper:Boolean, lower:Boolean, num:Boolean, sym:Boolean): String {
        val uppercase_letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val lowercase_letters = uppercase_letters.toLowerCase()
        val numbers = "0123456789"
        val syms = "~`! @#$%^&*()_-+={[}]|\\:;\"\'<,>.?/"

        var allowed_chars = ""
        if (upper) allowed_chars += uppercase_letters
        if (lower) allowed_chars += lowercase_letters
        if (num) allowed_chars += numbers
        if (sym) allowed_chars += syms

        return allowed_chars
    }

    private fun copy_to_clipboard() {
        val result = findViewById<TextView>(R.id.password).text.toString()
        val clipboard : ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip : ClipData = ClipData.newPlainText("edit_text", result)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, "text copied !", Toast.LENGTH_SHORT).show()
    }

    private fun update_length_text() {

    }
}