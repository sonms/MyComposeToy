package com.example.mycomposetoy.presentation.user.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage

@Composable
fun UserItem(
    id : Int,
    email : String,
    firstName : String,
    lastName : String,
    avatar : String,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
    ) {
        AsyncImage(
            model = avatar,
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
        )

        Text(
            text = "$id $email $firstName $lastName"
        )
    }
}

@Preview
@Composable
private fun UserItemPreview() {
    UserItem(
        id = 1,
        email = "john.archibald.campbell@example-pet-store.in",
        firstName = "George",
        lastName = "",
        avatar = "https://reqres.in/img/faces/1-image.jpg"
    )
}