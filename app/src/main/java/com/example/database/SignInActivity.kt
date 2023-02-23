package com.example.database

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignInActivity : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference

    companion object {
        const val KEY1 = "com.example.database.SignInActivity.mail"
        const val KEY2 = "com.example.database.SignInActivity.name"
        const val KEY3 = "com.example.database.SignInActivity.id"
    }


    override fun onCreate(savedInstanceState: Bundle?) {  //Oncreate is a method too
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        supportActionBar?.hide()

        val signInButton  = findViewById<Button>(R.id.btnSignIn)
        val userName  = findViewById<TextInputEditText>(R.id.userNameEditText)

        signInButton.setOnClickListener {
            //Take reference till node "User" and then find weather the username is present in it or not

            val uniqueId = userName.text.toString() //we will get the username using this gettext
            if(uniqueId.isNotEmpty()){
                readData(uniqueId) //Making method readData and it's function is given below else statement
            }else{
                Toast.makeText(this, "Please Enter A valid username", Toast.LENGTH_SHORT).show()

            }
        }
    } // onCreate method over

     private fun readData(uniqueId: String) {

         databaseReference = FirebaseDatabase.getInstance().getReference("Users")

         databaseReference.child(uniqueId).get().addOnSuccessListener {

             // Checking if user exist or not
             if(it.exists()){
                 // Welcome user in your in your app with intent and also pass
                 val email = it.child("email").value
                 val name = it.child("name").value
                 val userId = it.child("uniqueId").value

                 val intentWelcome = Intent(this, WelcomeAcitivity::class.java)

                 intentWelcome.putExtra(KEY1,email.toString())
                 intentWelcome.putExtra(KEY2,name.toString())
                 intentWelcome.putExtra(KEY3,userId.toString())

                 startActivity(intentWelcome)
             }else{
                 Toast.makeText(this,"User Doesn't Exist", Toast.LENGTH_SHORT).show()
             }

         }.addOnFailureListener {
             Toast.makeText(this,"Failed, Problem in BataBase", Toast.LENGTH_SHORT).show()
         }

    }
}