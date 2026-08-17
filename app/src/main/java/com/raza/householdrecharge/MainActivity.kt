package com.raza.householdrecharge

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raza.householdrecharge.ui.AppNavigation
import com.raza.householdrecharge.ui.HouseHoldContent
import com.raza.householdrecharge.ui.RoleScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (android.os.Build.VERSION.SDK_INT >=
            android.os.Build.VERSION_CODES.TIRAMISU &&
            checkSelfPermission(
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                100
            )
        }

        setContent {
            MaterialTheme {
                val application =
                    LocalContext.current.applicationContext as HouseholdRechargeApplication

                val viewModel: MemberViewModel =
                    viewModel(factory = MemberViewModel.Factory(application.repository))

                val userRole by viewModel.userRole.collectAsState()

                when (val role = userRole) {
                    null -> {
                        RoleScreen(onRoleSelected = viewModel::setUserRole)
                    }

                    else -> {
                        AppNavigation(viewModel = viewModel, userRole = role)
                    }
                }
            }
        }
    }
}
