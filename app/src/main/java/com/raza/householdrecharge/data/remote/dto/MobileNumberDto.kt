package com.raza.householdrecharge.data.remote.dto

data class MobileNumberDto(
    //recharge details begin
    //...
    //this cannot be null and should point to valid mobile number
    var mobileNumber: Long = 0,
    //...
    //recharge details end

    //data modeling details begin
    //...
    //this cannot be null and should point to valid account id
    var accountId: String = "",
    //this cannot be null and should point to valid household
    var householdId: String = "",
    //...
    //data modeling details end
)