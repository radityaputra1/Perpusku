package com.raditttttt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var ref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ref = FirebaseDatabase.getInstance().getReference("USERS")

        btnSave.setOnClickListener {
            savedata()
            val intent = Intent (this, show::class.java)
            startActivity(intent)
        }
    }
    private fun savedata() {
        val nama = InputNama.text.toString()
        val jumlah = InputJumlah.text.toString()
        val deskripsi = InputDesc.text.toString()

        val userId = ref.push().key.toString()
        val user = Users(userId,nama,jumlah,deskripsi)

        ref.child(userId).setValue(user).addOnCompleteListener {
            Toast.makeText(this, "Successs", Toast.LENGTH_SHORT).show()
            InputNama.setText("")
            InputJumlah.setText("")
            InputDesc.setText("")
        }

    }
}