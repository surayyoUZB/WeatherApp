package com.yoo.weatherapp.ui.bottom.location


import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.yoo.weatherapp.model.LocationName
import com.yoo.weatherapp.util.Graph

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationScreen(
    viewModel: LocationViewModel= hiltViewModel(),
    navHostController: NavHostController
) {
    var query by remember { mutableStateOf("") }
    Column {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            value = query,
            onValueChange = {
                query = it
                viewModel.onSearch(query)
            },
            placeholder = { Text(text = "Search") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "search"
                )
            }
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(10.dp)
        ) {
            items(
                items = viewModel.locationList,
                key = { it.id }
            ) {
                LocationItem(
                    locationName = it,
                    onItemClick = { l ->
                        navHostController.navigate("${Graph.DETAIL}/${l.id}/${l.name}/true")
                    },
                    onFavouriteClick = {
                        viewModel.changeFavourite(it)
                    }
                )
            }
        }
    }
}








@Composable
fun LocationItem(
    locationName:LocationName,
    onItemClick:(LocationName) ->Unit,
    onFavouriteClick:(LocationName) ->Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .border(border = BorderStroke(2.dp, Color.Black), RoundedCornerShape(12.dp))
            .padding(5.dp)
            .clickable { onItemClick(locationName) },
        verticalAlignment = Alignment.CenterVertically
        ) {
        Text(
            text = locationName.name,
            modifier = Modifier.fillMaxWidth(0.8f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    Column {
        IconButton(onClick = { onFavouriteClick(locationName) }) {
            Icon(
                imageVector = if (locationName.isSaved) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "icon",
                tint = if (locationName.isSaved) Color.Red else Color.Blue
            )
        }
        Text(
            text = locationName.savedDate,
            fontSize = 9.sp,
            maxLines = 1
        )
    }
    }
}