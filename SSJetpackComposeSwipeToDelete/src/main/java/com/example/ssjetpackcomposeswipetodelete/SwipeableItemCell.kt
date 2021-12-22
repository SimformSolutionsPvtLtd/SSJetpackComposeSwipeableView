package com.example.ssjetpackcomposeswipetodelete.swipetodelete

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@ExperimentalMaterialApi
@Composable

/**
 * @param leftViewIcons Icons which needs to be shown on the left side of the screen requires a Pair of Icon and Id of the view Pair(Icon, Id).
 * @param rightViewIcons Icons which needs to be shown on the right side of the screen requires a Pair of Icon and Id of the view Pair(Icon, Id).
 * @param position Position of the cell.
 * @param onClick OnClick event of the swipeable view returns a Pair of Position(Int) and Id(String) of the view Pair(Position, Id).
 * @param height Height of the swipeable view.
 * @param backgroundColor Background color of swipeable view.
 * @param fractionalThreshold The fraction (between 0 and 1) that the threshold will be at.
 * @param content Pass the content of your cell.
**/
fun SwipeAbleItemCell(
    leftViewIcons: ArrayList<Pair<ImageVector, String>>,
    rightViewIcons: ArrayList<Pair<ImageVector, String>>,
    position: Int,
    onClick: (Pair<Int, String>) -> (Unit),
    swipeDirection: SwipeDirection,
    leftViewWidth: Dp,
    rightViewWidth: Dp,
    height: Dp,
    backgroundColor: Color,
    fractionalThreshold: Float,
    content: @Composable () -> Unit
) {

    val swipeAbleState = rememberSwipeableState(initialValue = 0)
    val anchors = when (swipeDirection) {
        SwipeDirection.LEFT -> mapOf(
            0f to 0,
            -(with(LocalDensity.current) { rightViewWidth.toPx() }) to 1
        )
        SwipeDirection.RIGHT -> mapOf(
            0f to 0,
            (with(LocalDensity.current) { leftViewWidth.toPx() }) to 1
        )
        else -> mapOf(
            0f to 0,
            ((with(LocalDensity.current) { leftViewWidth.toPx() })) to 1,
            -(with(LocalDensity.current) { rightViewWidth.toPx() }) to 2
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(backgroundColor)
                    .swipeable(
                        state = swipeAbleState,
                        anchors = anchors,
                        thresholds = { _, _ ->
                            FractionalThreshold(fractionalThreshold)
                        },
                        orientation = Orientation.Horizontal
                    ),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height),
                    horizontalArrangement = when (swipeDirection) {
                        SwipeDirection.BOTH -> Arrangement.SpaceBetween
                        SwipeDirection.LEFT -> Arrangement.End
                        else -> Arrangement.Start
                    },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // When user Swipes to right the left side of the view which is visible
                    Row {
                        if (swipeDirection == SwipeDirection.RIGHT || swipeDirection == SwipeDirection.BOTH) {
                            leftViewIcons.forEachIndexed { index, pair ->
                                IconButton(
                                    onClick = {
                                        // Pair(Position, Id)
                                        onClick(Pair(position, pair.second))
                                    }
                                ) {
                                    // Pair(Icon, Id)
                                    Icon(imageVector = pair.first, contentDescription = pair.second)
                                }
                                if (index != (leftViewIcons.size - 1)) {
                                    Spacer(modifier = Modifier.width(10.dp))
                                }
                            }
                        }
                    }
                    // When user Swipes to left the right side of the view which is visible
                    Row {
                        if (swipeDirection == SwipeDirection.LEFT || swipeDirection == SwipeDirection.BOTH) {
                            rightViewIcons.forEachIndexed { index, pair ->
                                IconButton(
                                    onClick = {
                                        // Pair(Position, Id)
                                        onClick(Pair(position, pair.second))
                                    }
                                ) {
                                    // Pair(Icon, Id)
                                    Icon(imageVector = pair.first, contentDescription = pair.second)
                                }
                                if (index != (rightViewIcons.size - 1)) {
                                    Spacer(modifier = Modifier.width(10.dp))
                                }
                            }
                        }
                    }
                }
                // Main content view of the cell
                Box(
                    modifier = Modifier
                        .offset {
                            IntOffset(
                                swipeAbleState.offset.value.roundToInt(), 0
                            )
                        }
                        .fillMaxWidth()
                ) {
                    content()
                }
            }
        }
    }
}