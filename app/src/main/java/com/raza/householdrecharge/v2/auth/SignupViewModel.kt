package com.raza.householdrecharge.v2.auth

import com.google.firebase.auth.FirebaseAuth

class SignupViewModel() : AuthViewModel() {
    fun signup(onSuccess: () -> Unit, onFailure: (String?) -> Unit) {
        FirebaseAuth
            .getInstance()
            .createUserWithEmailAndPassword("$mobileNumber@householdrecharge.local", password)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure(it.message)
            }
    }
}