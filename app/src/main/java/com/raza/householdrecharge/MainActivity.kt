package com.raza.householdrecharge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raza.householdrecharge.ui.HouseHoldContent
import com.raza.householdrecharge.ui.RoleScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                val application  = LocalContext.current.applicationContext as HouseholdRechargeApplication

                val viewModel: MemberViewModel = viewModel(factory = MemberViewModel.Factory(application.repository))

                val userRole by viewModel.userRole.collectAsState()

                when(val role = userRole) {
                    null -> {
                        RoleScreen(onRoleSelected = viewModel::setUserRole)
                    }

                    else -> {
                        HouseHoldContent(viewModel = viewModel, userRole = role)
                    }
                }
            }
        }
    }
}
