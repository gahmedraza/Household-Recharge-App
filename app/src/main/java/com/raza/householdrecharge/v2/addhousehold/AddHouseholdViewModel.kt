package com.raza.householdrecharge.v2.addhousehold

import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.v2.BaseViewModel
import com.raza.householdrecharge.v2.Household
import com.raza.householdrecharge.v2.HouseholdDto
import com.raza.householdrecharge.v2.SessionManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AddHouseholdViewModel(
    private val sessionManager: SessionManager
) : BaseViewModel() {

    fun onAddHousehold(
        userId: String?,
        householdName: String,
        onSuccess: (String?) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        viewModelScope.launch {
            val userNotFound = userId?.isEmpty() ?: false

            if (userNotFound) {
                onFailure("user not found")
                return@launch
            }

            val householdDto = HouseholdDto(
                name = householdName
            )

            FirebaseFirestore
                .getInstance()
                .collection("users")
                .document(userId ?: "")
                .collection("households")
                .add(householdDto)
                .addOnSuccessListener { documentReference ->
                    val householdId = documentReference.id

                    viewModelScope.launch {
                        sessionManager.saveHouseholdName(householdName)
                        sessionManager.saveHouseholdId(householdId)
                    }

                    onSuccess(householdId)
                }
                .addOnFailureListener {
                    onFailure(it.message)
                }
        }
    }
}