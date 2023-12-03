package com.example.room

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.room.data.AppDatabase
import com.example.room.data.entity.User

class EditorActivity : AppCompatActivity() {
    private lateinit var fullName: EditText
    private lateinit var email: EditText
    private lateinit var phone: EditText
    private lateinit var btnSave: Button

    private lateinit var database: AppDatabase //yg udah di buat di dalam package data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)
        fullName = findViewById(R.id.et_name)
        email = findViewById(R.id.et_email)
        phone = findViewById(R.id.et_phone)
        btnSave = findViewById(R.id.btn_save)

        database = AppDatabase.getInstance(applicationContext)

        //karena di MainActivity kita ambil id untuk menampilkan data maka
        //di EditorActivity kit harus tangkap si id tersebut
        val intent = intent.extras
        if (intent!=null){
            //maka akan diedit
            //diambil dari data yang dikirim
            val id = intent.getInt("id", 0)
            var user = database.userDao().get(id)
            //buat menampilkan, jadi kalau ada intent form akan mengisi secara otomatis
            fullName.setText(user.fullName)
            email.setText(user.email)
            phone.setText(user.phone)
        }

        //kondisi setelah buttonSave diklik
        btnSave.setOnClickListener {
            if (fullName.text.isNotEmpty() && email.text.isNotEmpty() && phone.text.isNotEmpty()) {
                if (intent!=null){
                    //untuk edit data
                    database.userDao().update(
                        User(
                            intent.getInt("id",0),
                            fullName.text.toString(),
                            email.text.toString(),
                            phone.text.toString()
                        )
                    )
                }else{
                    //buat nambah data saja
                    database.userDao().insertAll(
                        User(
                            null,
                            fullName.text.toString(),
                            email.text.toString(),
                            phone.text.toString()
                        )
                    )
                }
                finish()
            } else {
                Toast.makeText(applicationContext, "Mohon lengkapi semua data", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}