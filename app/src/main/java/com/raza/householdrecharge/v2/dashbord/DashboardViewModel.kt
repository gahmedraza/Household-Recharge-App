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
import com.raza.householdrecharge.v2.common.getDateInMillis
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val sessionManager: SessionManager
) : BaseViewModel() {
    var members by mutableStateOf<List<Member>>(emptyList())

    fun loadMembers(onSuccess: () -> Unit, onFailure: (String?) -> Unit) {
        viewModelScope.launch {
            val householdId = sessionManager.householdId.first()

            val householdNotFound = householdId.isEmpty()

            if (householdNotFound) {
                onFailure("No household found")
                return@launch
            }

            val userId = sessionManager.userId.first()

            if (userId.isEmpty()) {
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
                    val memberList = mutableListOf<Member>()

                    result.documents.mapNotNull { document ->
                        val memberDto = document.toObject(MemberDto::class.java)

                        val member = Member(
                            id = document.id,
                            name = memberDto?.name ?: "",
                            mobileNumber = memberDto?.mobileNumber ?: "",
                            planDurationDays = memberDto?.planDurationDays?.toInt() ?: 0,
                            lastRechargeDate = getDateInMillis(memberDto?.lastRechargeDate),
                            planExpiryDate = getDateInMillis(memberDto?.planExpiryDate),
                            rechargeRequested = false,
                            planAmount = memberDto?.planAmount?.toInt() ?: 0
                        )

                        memberList.add(member)
                    }

                    this@DashboardViewModel.members = memberList

                    onSuccess()
                }
                .addOnFailureListener {
                    onFailure(it.message)
                }
        }
    }
}