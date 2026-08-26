package com.raza.householdrecharge.v2.setuphousehold

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.v2.BaseViewModel
import com.raza.householdrecharge.v2.HouseholdDto
import com.raza.householdrecharge.v2.SessionManager
import com.raza.householdrecharge.v2.repository.AppUserDto
import com.raza.householdrecharge.v2.repository.FirestoreRepository
import com.raza.householdrecharge.v2.repository.cleanString
import kotlinx.coroutines.launch

class HouseholdViewModel(
    private val sessionManager: SessionManager
) : BaseViewModel() {

    var invitationCode by mutableStateOf("")

    fun onAddHousehold(
        userId: String?,
        householdName: String,
        onSuccess: (String?) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true

            val userNotFound = userId?.isEmpty() ?: false

            if (userNotFound) {
                onFailure("user not found")
                return@launch
            }

            val householdDto = HouseholdDto(
                name = householdName
            )

            val appUserDto = AppUserDto(
                userId = userId.cleanString(),
                householdId = ""
            )

            FirestoreRepository.addHousehold2(
                appUserDto = appUserDto,

                householdDto = householdDto,

                onSuccess = { householdId ->

                    viewModelScope.launch {
                        sessionManager.saveHouseholdName(householdName)
                        sessionManager.saveHouseholdId(householdId)

                        isLoading = false
                        onSuccess(householdId)
                    }
                },

                onFailure = { error ->

                    isLoading = false
                    onFailure(error)
                }
            )
        }
    }

    fun validateInvitationCode(
        invitationCode: String,
        onSuccess: (InvitationResultDto) -> Unit,
        onFailure: (String?) -> Unit
    ) {

        var invitationResultDto: InvitationResultDto

        FirebaseFirestore
            .getInstance()

            .collection("invitations")
            .document(invitationCode)

            .get()
            .addOnSuccessListener { document ->
                if(!document.exists()) {
                    onFailure("invalid invitation code")
                    return@addOnSuccessListener
                }
            }

    }
}

data class InvitationResultDto(
    val bool: Boolean = false,
    val householdId: String?,
    val householdName: String?,
    val message: String?
)