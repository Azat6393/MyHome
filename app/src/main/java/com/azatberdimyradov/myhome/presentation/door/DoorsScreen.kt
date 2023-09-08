package com.azatberdimyradov.myhome.presentation.door

import android.annotation.SuppressLint
import android.view.FrameMetrics
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.azatberdimyradov.myhome.R
import com.azatberdimyradov.myhome.domain.model.Camera
import com.azatberdimyradov.myhome.domain.model.Door
import com.azatberdimyradov.myhome.presentation.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlin.math.roundToInt

@Composable
fun DoorsScreen(
    modifier: Modifier = Modifier,
    state: DoorScreenState,
    onRefresh: () -> Unit
) {

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.isLoading)
    SwipeRefresh(state = swipeRefreshState, onRefresh = onRefresh) {
        LazyColumn(modifier = modifier) {
            items(
                state.doors,
                key = { item -> item.id }
            ) { item ->
                Box {
                    Row(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 25.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(
                                id = if (item.favorites) R.drawable.star_checked
                                else R.drawable.star_uncheacked
                            ), contentDescription = "Star",
                            modifier = Modifier
                                .drawBehind {
                                    drawCircle(
                                        color = Color.Gray,
                                        radius = 25.dp.toPx(),
                                        style = Stroke()
                                    )
                                }
                                .size(30.dp)
                        )
                        Spacer(modifier = Modifier.width(30.dp))
                        Image(
                            painter = painterResource(
                                id = R.drawable.edit_icon
                            ), contentDescription = "Edit",
                            modifier = Modifier
                                .drawBehind {
                                    drawCircle(
                                        color = Color.Gray,
                                        radius = 25.dp.toPx(),
                                        style = Stroke()
                                    )
                                }
                                .size(30.dp)
                        )
                    }
                    DoorItem(
                        door = item,
                        modifier = Modifier.fillMaxWidth(),
                        cardOffset = 136f.dp(),
                        onExpand = {},
                        onCollapse = {}
                    )
                }
            }
        }

    }
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun DoorItem(
    modifier: Modifier = Modifier,
    door: Door,
    cardOffset: Float,
    onExpand: () -> Unit,
    onCollapse: () -> Unit,
) {

    var showImage by remember {
        mutableStateOf(door.snapshot.isNotBlank())
    }

    var offsetX by remember { mutableStateOf(0f) }

    var isRevealed by remember {
        mutableStateOf(false)
    }

    val transitionState = remember {
        MutableTransitionState(isRevealed).apply {
            targetState = !isRevealed
        }
    }
    val transition = updateTransition(transitionState, "cardTransition")
    val offsetTransition by transition.animateFloat(
        label = "cardOffsetTransition",
        transitionSpec = { tween(durationMillis = FrameMetrics.ANIMATION_DURATION) },
        targetValueByState = { if (isRevealed) cardOffset - offsetX else -offsetX },

        )

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .offset { IntOffset((offsetX - offsetTransition).roundToInt(), 0) }
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    val original = Offset(offsetX, 0f)
                    val summed = original - Offset(x = dragAmount, y = 0f)
                    val newValue = Offset(x = summed.x.coerceIn(0f, cardOffset), y = 0f)
                    if (newValue.x >= 10) {
                        onExpand()
                        isRevealed = true
                        return@detectHorizontalDragGestures
                    } else if (newValue.x <= 0) {
                        onCollapse()
                        isRevealed = false
                        return@detectHorizontalDragGestures
                    }
                    if (change.positionChange() != Offset.Zero) change.consume()
                    offsetX = newValue.x
                }
            }
            .background(Color.White)
            .padding(start = 15.dp, end = 15.dp, bottom = 15.dp)
            .shadow(1.dp, RoundedCornerShape(15.dp))
            .clickable { showImage = !showImage }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            AnimatedVisibility(visible = showImage) {
                AsyncImage(
                    model = door.snapshot,
                    contentDescription = door.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(190.dp)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .padding(start = 15.dp, end = 30.dp)
            ) {
                Column(
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Text(
                        text = door.name,
                        textAlign = TextAlign.Start,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                    Text(
                        text = "В сети",
                        textAlign = TextAlign.Start,
                        fontSize = 14.sp,
                        color = Color.Gray,
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.key_icon),
                    contentDescription = "Shield icon",
                    modifier = Modifier
                        .size(25.dp)
                        .align(Alignment.CenterEnd)
                )
            }
        }
    }
}
