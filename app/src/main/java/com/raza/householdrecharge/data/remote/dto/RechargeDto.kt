package com.raza.householdrecharge.data.remote.dto

data class RechargeDto(
    //recharge details begin
    //...
    //recharge amount input should be numbered type
    //recharge amount must be a number only
    //recharge amount cannot exceed 10,000
    //recharge amount cannot be below 1
    var rechargeAmount: Int = 0,
    //recharge date must be picked from calendar
    //recharge date cannot exceed a date range of +1 month
    //or -1 month from today's date
    var rechargeDate: Long = 0L,
    //expiry date cannot exceed more than a year from recharge date
    //expiry date cannot be before the recharge date
    //expiry date will not be accepted without recharge date
    var expiryDate: Long = 0L,
    //the value for this should be picked from a dropdown of household members only
    var rechargedBy: String = "",
    //...
    //recharge details end

    //data modeling details begin
    //...
    //this cannot be null and should point to valid mobile number
    var mobileNumber: Int = 0,
    //this cannot be null and should point to valid account id
    var accountId: String = "",
    //this cannot be null and should point to valid household
    var householdId: String = "",
    //...
    //data modeling details end
)