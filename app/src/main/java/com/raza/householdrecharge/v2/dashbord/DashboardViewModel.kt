package com.raza.householdrecharge.v2.dashbord

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.data.Member
import com.raza.householdrecharge.v2.BaseViewModel
import com.raza.householdrecharge.v2.SessionManager
import com.raza.householdrecharge.v2.common.MemberDto
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val sessionManager: SessionManager
) : BaseViewModel() {
    var members by mutableStateOf<List<MemberDto>>(emptyList())

    fun loadMembers(onSuccess: () -> Unit, onFailure: (String?) -> Unit) {
        viewModelScope.launch {
            val householdId = sessionManager.householdId.first()

            val householdNotFound = householdId.isEmpty()

            if (householdNotFound) {
                onFailure("No household found")
                return@launch
            }

            val userId = sessionManager.userId.first()

            if(userId.isEmpty()) {
                onFailure("No user found")
                return@launch
            }

            Log.d("TAG", "householdId= $householdId")

            FirebaseFirestore
                .getInstance()

                .collection("users")
                .document(userId)

                .collection("households")
                .document(householdId)

                .collection("members")
                .get()

                .addOnSuccessListener { result ->
                    val members = result.documents.mapNotNull { document ->
                        document.toObject(MemberDto::class.java)
                    }

                    this@DashboardViewModel.members = members

                    onSuccess()
                }
                .addOnFailureListener {
                    onFailure(it.message)
                }
        }
    }
}