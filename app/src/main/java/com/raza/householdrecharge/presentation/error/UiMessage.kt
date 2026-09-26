package com.raza.householdrecharge.presentation.error

sealed interface UiMessage {

    data class ResourceId(
        val id: Int
    ) : UiMessage

    data class TextMessage(
        val message: String
    ) : UiMessage
}