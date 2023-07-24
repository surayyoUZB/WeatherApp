package com.yoo.weatherapp.ui.bottom.settings


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yoo.weatherapp.R
import com.yoo.weatherapp.ui.component.ThemeDialog

@Composable
fun SettingsScreen(
    viewModel: SettingViewModel= hiltViewModel()
) {
    var isDialogOpen by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    isDialogOpen = true
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(12.dp))
            Icon(
                painter = painterResource(id = R.drawable.baseline_color_lens_24),
                contentDescription = "theme",
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Theme"
            )
            if (isDialogOpen){
                ThemeDialog(
                    onDismiss = { isDialogOpen=false },
                    onSelect = {
                               viewModel.saveTheme(it)
                    },
                    currentIndex = viewModel.theme.value
                )
            }
        }
    }
}