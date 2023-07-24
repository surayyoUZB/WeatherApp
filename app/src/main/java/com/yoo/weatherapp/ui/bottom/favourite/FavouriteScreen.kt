package com.yoo.weatherapp.ui.bottom.favourite

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.yoo.weatherapp.model.FavoriteName
import com.yoo.weatherapp.util.Graph

@Composable
fun FavouriteScreen(
    navHostController: NavHostController,
    viewModel: FavoriteViewModel= hiltViewModel()
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ){
        items(
            items = viewModel.favoriteList,
            key={it.id}
        ){
            FavouriteItem(
                favoriteName = it,
                onItemClick = {f->
                    navHostController.navigate("${Graph.DETAIL}/${f.id}/${f.name}/true")
                              }
            )
        }

    }

}




@Composable
fun FavouriteItem(
    favoriteName: FavoriteName,
    onItemClick:(FavoriteName) ->Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .border(border = BorderStroke(2.dp, Color.Black), RoundedCornerShape(12.dp))
            .padding(5.dp)
            .clickable { onItemClick(favoriteName) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = favoriteName.name,
            modifier = Modifier.fillMaxWidth(0.9f).padding(6.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
                Icon(
                    imageVector =Icons.Outlined.Favorite ,
                    contentDescription = "icon",
                    tint = Color.Red
                )

    }

}