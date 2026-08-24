package com.raza.householdrecharge.v2.auth

import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.raza.householdrecharge.v2.SessionManager
import kotlinx.coroutines.launch

class SignupViewModel(
    private val sessionManager: SessionManager
) : AuthViewModel() {
    fun signup(onSuccess: (String?) -> Unit, onFailure: (String?) -> Unit) {
        isLoading = true

        FirebaseAuth
            .getInstance()
            .createUserWithEmailAndPassword("$mobileNumber@householdrecharge.local", password)
            .addOnSuccessListener { documentReference ->

                val userId = documentReference.user?.uid ?: ""
                viewModelScope.launch {
                    sessionManager.saveUserId(userId)
                    sessionManager.saveMobileNumber(mobileNumber)
                }

                this.userId = userId
                isLoading = false

                onSuccess(userId)
            }
            .addOnFailureListener {

                isLoading = false
                onFailure(it.message)
            }
    }
}