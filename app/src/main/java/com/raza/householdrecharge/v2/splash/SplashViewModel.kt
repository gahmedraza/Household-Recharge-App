package com.raza.householdrecharge.v2.splash

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.v2.BaseViewModel

class SplashViewModel : BaseViewModel() {

    fun loadHousehold(onSuccess: () -> Unit, onFailure: (String?) -> Unit) {

        val firebaseUser = FirebaseAuth
            .getInstance()
            .currentUser

        FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(firebaseUser?.uid ?: "")
            .get()
            .addOnSuccessListener { document ->
                if (!document.exists()) {
                    onFailure("User data not found")
                }

                val householdId = document.getString("householdId")
                household.id = householdId

                onSuccess()
            }
            .addOnFailureListener {
                onFailure(it.message)
            }
    }
}