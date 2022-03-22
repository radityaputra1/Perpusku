package com.raditttttt

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class Adapter(val mCtx: Context, val layoutResId: Int, val list: List<Users> )
    : ArrayAdapter<Users>(mCtx,layoutResId,list){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId,null)

        val textNama = view.findViewById<TextView>(R.id.textNama)
        val textJumlah = view.findViewById<TextView>(R.id.textJumlah)
        val textDeskripsi = view.findViewById<TextView>(R.id.textDeskripsi)

        val textUpdate = view.findViewById<TextView>(R.id.textUpdate)
        val textDelete = view.findViewById<TextView>(R.id.textDelete)

        val user = list[position]

        textNama.text = user.nama
        textJumlah.text = user.jumlah
        textDeskripsi.text = user.deskripsi

        textUpdate.setOnClickListener {
            showUpdateDialog(user)
        }
        textDelete.setOnClickListener {
            Deleteinfo(user)
        }

        return view

    }

    private fun showUpdateDialog(user: Users) {
        val builder = AlertDialog.Builder(mCtx)

        builder.setTitle("Update")

        val inflater = LayoutInflater.from(mCtx)

        val view = inflater.inflate(R.layout.update, null)

        val textNama = view.findViewById<EditText>(R.id.InputNama)
        val textJumlah = view.findViewById<EditText>(R.id.InputJumlah)
        val textDeskripsi = view.findViewById<EditText>(R.id.InputDesc)

        textNama.setText(user.nama)
        textJumlah.setText(user.jumlah)
        textDeskripsi.setText(user.deskripsi)

        builder.setView(view)

        builder.setPositiveButton("Update") { dialog, which ->

            val dbUsers = FirebaseDatabase.getInstance().getReference("USERS")

            val nama = textNama.text.toString().trim()

            val jumlah = textJumlah.text.toString().trim()

            val deskripsi = textDeskripsi.text.toString().trim()

            if (nama.isEmpty()){
                textNama.error = "Masukan Nama Buku"
                textNama.requestFocus()
                return@setPositiveButton
            }

            if (jumlah.isEmpty()){
                textJumlah.error = "Masukan Nomor Buku"
                textJumlah.requestFocus()
                return@setPositiveButton
            }

            if (deskripsi.isEmpty()){
                textDeskripsi.error = "Masukkan Deskripsi Buku"
                textDeskripsi.requestFocus()
                return@setPositiveButton
            }

            val user = Users(user.id,nama,jumlah,deskripsi)

            dbUsers.child(user.id).setValue(user).addOnCompleteListener {
                Toast.makeText(mCtx,"Updated",Toast.LENGTH_SHORT).show()
            }

        }

        builder.setNegativeButton("No") { dialog, which ->

        }

        val alert = builder.create()
        alert.show()

    }

    private fun Deleteinfo(user: Users) {
        val progressDialog = ProgressDialog(context, R.style.Theme_MaterialComponents_Light_Dialog)

        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Deleting...")
        progressDialog.show()

        val mydatabase = FirebaseDatabase.getInstance().getReference("USERS")
        mydatabase.child(user.id).removeValue()
        Toast.makeText(mCtx,"Deleted!!",Toast.LENGTH_SHORT).show()

        val intent = Intent(context, show::class.java)
        context.startActivity(intent) }


}
