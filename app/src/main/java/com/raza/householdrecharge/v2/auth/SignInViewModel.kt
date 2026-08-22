package com.raza.householdrecharge.v2.auth

import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class SignInViewModel : AuthViewModel() {
    fun signIn(onSuccess: () -> Unit, onFailure: (String?) -> Unit) {
        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword("$mobileNumber@householdrecharge.local", password)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure(it.message)
            }
    }
}