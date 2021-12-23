package com.example.ssjetpackcomposeswipeableview

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@ExperimentalMaterialApi
@Composable

/**
 * @param leftViewIcons Icons which needs to be shown on the left side of the screen requires a Triplet of Icon, tint color and Id of the view Triplet(Icon, TintColor, Id).
 * @param rightViewIcons Icons which needs to be shown on the right side of the screen requires a Pair of Icon, tint color and Id of the view Pair(Icon, TintColor, Id).
 * @param position Position of the cell.
 * @param onClick OnClick event of the swipeable view returns a Pair of Position(Int) and Id(String) of the view Pair(Position, Id).
 * @param height Height of the swipeable view.
 * @param leftViewBackgroundColor Background color of swipeable view of left side.
 * @param rightViewBackgroundColor Background color of swipeable view of right side.
 * @param cornerRadius Corner radius of the swipeable views.
 * @param leftSpace Space between left side swipeable view and content view
 * @param rightSpace Space between right side swipeable view and content view
 * @param fractionalThreshold The fraction (between 0 and 1) that the threshold will be at.
 * @param content Pass the content of your view.
**/
fun SwipeAbleItemView(
    leftViewIcons: ArrayList<Triple<Painter, Color, String>>,
    rightViewIcons: ArrayList<Triple<Painter, Color, String>>,
    position: Int = 0,
    onClick: (Pair<Int, String>) -> (Unit),
    swipeDirection: SwipeDirection,
    leftViewWidth: Dp = 70.dp,
    rightViewWidth: Dp = 70.dp,
    height: Dp = 70.dp,
    leftViewBackgroundColor: Color,
    rightViewBackgroundColor: Color,
    cornerRadius: Dp = 0.dp,
    leftSpace: Dp = 0.dp,
    rightSpace: Dp = 0.dp,
    fractionalThreshold: Float = 0.3f,
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
                    .background(Color.Transparent)
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
                    if (swipeDirection == SwipeDirection.RIGHT || swipeDirection == SwipeDirection.BOTH) {
                        Row(modifier = Modifier
                            .fillMaxHeight()
                            .width(leftViewWidth - leftSpace)
                            .background(
                                leftViewBackgroundColor,
                                shape = RoundedCornerShape(cornerRadius)
                            ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center) {
                            leftViewIcons.forEachIndexed { index, triplet ->
                                IconButton(
                                    onClick = {
                                        // Pair(Position, Id)
                                        onClick(Pair(position, triplet.third))
                                    }
                                ) {
                                    // Triplet(Icon, TintColor, Id)
                                    Icon(painter = triplet.first, contentDescription = triplet.third, tint = triplet.second)
                                }
                            }
                        }
                    }
                    // When user Swipes to left the right side of the view which is visible
                    if (swipeDirection == SwipeDirection.LEFT || swipeDirection == SwipeDirection.BOTH) {
                        Row(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(rightViewWidth - rightSpace)
                                .background(
                                    rightViewBackgroundColor,
                                    shape = RoundedCornerShape(cornerRadius)
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            rightViewIcons.forEachIndexed { index, triplet ->
                                IconButton(
                                    onClick = {
                                        // Pair(Position, Id)
                                        onClick(Pair(position, triplet.third))
                                    }
                                ) {
                                    // Triplet(Icon, TintColor, Id)
                                    Icon(painter = triplet.first, contentDescription = triplet.third, tint = triplet.second)
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