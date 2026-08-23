package com.raza.householdrecharge.v2.addhousehold

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.v2.BaseViewModel
import com.raza.householdrecharge.v2.Household

class AddHouseholdViewModel : BaseViewModel() {

    fun onAddHousehold(onSuccess: () -> Unit, onFailure: (String?) -> Unit) {
        FirebaseFirestore
            .getInstance()
            .collection("households")
            .add(household)
            .addOnSuccessListener { documentReference ->
                household.id = documentReference.id
                onSuccess()
            }
            .addOnFailureListener {
                onFailure(it.message)
            }
    }
}