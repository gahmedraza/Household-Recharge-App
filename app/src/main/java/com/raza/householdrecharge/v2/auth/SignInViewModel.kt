package com.raza.householdrecharge.v2.auth

import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.raza.householdrecharge.v2.SessionManager
import kotlinx.coroutines.launch

class SignInViewModel(
    private val sessionManager: SessionManager
) : AuthViewModel() {
    fun signIn(onSuccess: () -> Unit, onFailure: (String?) -> Unit) {
        isLoading = true

        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword("$mobileNumber@householdrecharge.local", password)
            .addOnSuccessListener { documentReference ->
                val userId = documentReference?.user?.uid ?: ""

                viewModelScope.launch {
                    sessionManager.saveUserId(userId)
                }

                this.userId = userId

                isLoading = false
                onSuccess()
            }
            .addOnFailureListener {

                isLoading = false
                onFailure(it.message)
            }
    }
}