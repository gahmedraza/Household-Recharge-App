package com.raza.householdrecharge.data.remote.dto

data class MobileNumberDto(
    //server side id begin
    //...
    var id: String = "",
    //...
    //server side id end
    //mobile number details begin
    //...
    //this cannot be null and should point to valid mobile number
    var mobileNumber: Long = 0,
    //...
    //mobile number details end

    //last recharge details begin
    //...
    var lastRechargeId: String = "",
    //...
    //last recharge details end

    //data modeling details begin
    //...
    //this cannot be null and should point to valid account id
    var accountId: String = "",
    //this cannot be null and should point to valid household
    var householdId: String = "",
    //...
    //data modeling details end
)