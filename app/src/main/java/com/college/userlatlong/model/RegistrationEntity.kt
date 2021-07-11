package com.college.userlatlong.model

data class RegistrationEntity(
    var user_id: String,
    var secret_key: String,
    var success: Boolean
)