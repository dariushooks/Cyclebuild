package com.rookieandroid.cyclebuild.fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.rookieandroid.cyclebuild.R
import com.rookieandroid.cyclebuild.User
import com.rookieandroid.cyclebuild.architecture.HomeViewModel

class SignUpFragment : Fragment(R.layout.fragment_sign_up), View.OnClickListener
{
    private val auth : FirebaseAuth = FirebaseAuth.getInstance()
    private val dbRef : DatabaseReference = FirebaseDatabase.getInstance().reference
    private lateinit var email : TextInputEditText
    private lateinit var password : TextInputEditText
    private lateinit var signUp : Button
    private lateinit var signIn : TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        email = view.findViewById(R.id.email)
        password = view.findViewById(R.id.password)
        signUp = view.findViewById(R.id.sign_up_button)
        signUp.setOnClickListener(this)
        signIn = view.findViewById(R.id.go_to_sign_in)
        signIn.setOnClickListener(this)
    }

    override fun onClick(v: View?)
    {
        when(v?.id)
        {
            signUp.id -> { if(checkTextFields()) createUser() }
            signIn.id -> { findNavController().navigateUp() }
        }
    }

    private fun checkTextFields() : Boolean
    {
        val e = email.text.toString()
        val p = password.text.toString()

        if(TextUtils.isEmpty(e))
        {
            email.error = "Field cannot be empty"
            return false
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(e).matches())
        {
            email.error = "Enter valid email address"
            return false
        }

        if(TextUtils.isEmpty(p))
        {
            password.error = "Field cannot be empty"
            return false
        }

        if(p.length < 8)
        {
            password.error = "Password must be at least 8 characters"
            return false
        }

        return true
    }

    private fun createUser()
    {
        auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
            .addOnCompleteListener(requireActivity())
            { task ->
                if (task.isSuccessful)
                {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(context?.packageName, "createUserWithEmail:success")
                    addNewUserToDatabase(email.text.toString(), auth.currentUser?.uid!!)
                    findNavController().navigate(R.id.fragment_home)
                    //updateUI(user)
                }
                else
                {
                    // If sign in fails, display a message to the user.
                    Log.w(context?.packageName, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(context, task.exception?.message, Toast.LENGTH_LONG).show()
                    //updateUI(null)
                }
            }
    }

    private fun addNewUserToDatabase(userEmail : String, userId : String)
    {
        dbRef.child("users").child(userId).setValue(User(userEmail, userId))
    }
}