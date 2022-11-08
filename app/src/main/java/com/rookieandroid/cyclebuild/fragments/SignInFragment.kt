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
import com.rookieandroid.cyclebuild.R
import com.rookieandroid.cyclebuild.architecture.HomeViewModel

class SignInFragment : Fragment(R.layout.fragment_sign_in), View.OnClickListener
{
    private val auth : FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var email : TextInputEditText
    private lateinit var password : TextInputEditText
    private lateinit var login : Button
    private lateinit var signUp : TextView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        if(auth.currentUser != null)
        {
            findNavController().navigate(R.id.nav_logged_in)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        email = view.findViewById(R.id.email)
        password = view.findViewById(R.id.password)
        login = view.findViewById(R.id.sign_in_button)
        login.setOnClickListener(this)
        signUp = view.findViewById(R.id.go_to_sign_up)
        signUp.setOnClickListener(this)
    }

    override fun onClick(v: View?)
    {
        when(v?.id)
        {
            login.id -> { if(checkTextFields()) loginUser() }
            signUp.id -> { findNavController().navigate(R.id.action_fragment_sign_in_to_fragment_sign_up) }
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

        return true
    }

    private fun loginUser()
    {
        auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
            .addOnCompleteListener(requireActivity())
            { task ->
                if (task.isSuccessful)
                {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(context?.packageName, "signInWithEmail:success")
                    findNavController().navigate(R.id.nav_logged_in)
                    //updateUI(user)
                }

                else
                {
                    // If sign in fails, display a message to the user.
                    Log.w(context?.packageName, "signInWithEmail:failure", task.exception)
                    Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }
            }
    }
}