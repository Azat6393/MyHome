package com.azatberdimyradov.myhome.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

sealed class TabPage(
    val id: Int,
    val title: String
) {
    object Camera :
        TabPage(0, "Камеры")

    object Door : TabPage(1, "Двери")

    object Items {
        val list = listOf(
            Camera, Door
        )
    }
}

@Composable
fun TabHome(
    currentScreenId: Int,
    onItemSelected: (TabPage) -> Unit
) {
    val items = TabPage.Items.list
    Row(
        modifier = Modifier
            .background(Color.Transparent)
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            CustomTab(tabPage = item, isSelected = item.id == currentScreenId) {
                onItemSelected(item)
            }
        }
    }
}

@Composable
fun CustomTab(
    tabPage: TabPage,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val background = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .background(Color.Transparent)
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = tabPage.title, color = Color.Black, fontSize = 20.sp)

            AnimatedVisibility(visible = isSelected) {
                Divider(color = background, thickness = 2.dp, modifier = Modifier.fillMaxWidth(0.5f))
            }
        }
    }
}