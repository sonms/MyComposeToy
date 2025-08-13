package com.example.mycomposetoy.presentation.user.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.example.mycomposetoy.presentation.user.model.UserListUiModel

@Composable
fun UserItem(
    /* id : Int,
     email : String,
     firstName : String,
     lastName : String,
     avatar : String,*/
    item : UserListUiModel,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
    ) {
        AsyncImage(
            model = item.avatar,
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
        )

        Text(
            text = "${item.id} - ${item.email} - ${item.firstName} - ${item.lastName}"
        )
    }
}

@Preview
@Composable
private fun UserItemPreview() {
    val item = UserListUiModel(
        id = 1,
        email = "john.archibald.campbell@example-pet-store.in",
        firstName = "George",
        lastName = "",
        avatar = "https://reqres.in/img/faces/1-image.jpg"
    )

    UserItem(
        item = item
    )
}