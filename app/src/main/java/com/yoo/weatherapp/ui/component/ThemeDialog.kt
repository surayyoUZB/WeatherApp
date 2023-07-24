package com.yoo.weatherapp.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ThemeDialog(
    onDismiss:()->Unit,
    onSelect:(Int)->Unit,
    currentIndex:Int
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Select theme")
        },
        text = {
            Column{
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = currentIndex==0,
                        onClick = { onSelect(0) }
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(text = "System")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = currentIndex==1,
                        onClick = { onSelect(1) }
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(text = "Light")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = currentIndex==2,
                        onClick = { onSelect(2) }
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(text = "Dark")
                }

            }
        },
        confirmButton = {

        }
    )

}