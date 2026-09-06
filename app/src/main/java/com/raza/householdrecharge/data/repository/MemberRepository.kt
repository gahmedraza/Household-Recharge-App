package com.raza.householdrecharge.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.common.MemberDto
import com.raza.householdrecharge.common.map
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.dto.AppUserDto
import com.raza.householdrecharge.domain.model.Member
import com.raza.householdrecharge.util.cleanString
import kotlinx.coroutines.tasks.await

class MemberRepository(
    private val firestore: FirebaseFirestore
) {

    suspend fun fetchMembers(
        appUserDto: AppUserDto
    ): Result<List<Member>, String> {

        var result: Result<List<Member>, String>

        try {

            val documentSnapshot = firestore

                .collection("users")
                .document(appUserDto.authId)

                .collection("households")
                .document(appUserDto.householdId)

                .collection("members")
                .get()
                .await()

            val memberList = mutableListOf<Member>()

            documentSnapshot.documents.mapNotNull { document ->
                val memberDto = document.toObject(MemberDto::class.java)

                val member = map(document.id, memberDto)

                memberList.add(member)
            }

            result = Result.Success(memberList)

        } catch (e: Exception) {

            result = Result.Failure(e.message.cleanString())
        }

        return result
    }

    suspend fun addMember(
        appUserDto: AppUserDto,
        member: MemberDto
    ) : Result<String, String> {

        var result: Result<String, String>

        try {

            val documentReference = firestore

                .collection("accounts")
                .document(appUserDto.authId)

                .collection("households")
                .document(appUserDto.householdId)

                .collection("members")
                .add(member)

                .await()

            val memberId = documentReference.id
            result = Result.Success(memberId)

        } catch (e: Exception) {

            result = Result.Failure(e.message.cleanString())
        }

        return result
    }
}