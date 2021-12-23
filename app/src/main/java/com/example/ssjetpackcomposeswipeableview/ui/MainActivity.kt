package com.example.ssjetpackcomposeswipeableview.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ssjetpackcomposeswipeableview.SwipeDirection
import com.example.ssjetpackcomposeswipeableview.ui.theme.Background
import com.example.ssjetpackcomposeswipeableview.ui.theme.SSJetpackComposeSwipeAbleViewTheme

@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SSJetpackComposeSwipeAbleViewTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MainContent()
                }
            }
        }
    }

    @Preview
    @Composable
    fun MainContent() {
        val context = LocalContext.current
        Scaffold(topBar = {
            TopAppBar(
                title = { Text("SWIPEABLE VIEW") },
            )
        }) {
            Column(Modifier.fillMaxHeight().background(Background)) {
                Button(
                    onClick = {
                        navigateToSwipeAbleViewActivity(context, SwipeDirection.LEFT)
                    }, modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(), shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "Swipe Left",
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
                Button(
                    onClick = {
                        navigateToSwipeAbleViewActivity(context, SwipeDirection.RIGHT)
                    }, modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(), shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "Swipe Right",
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
                Button(
                    onClick = {
                        navigateToSwipeAbleViewActivity(context, SwipeDirection.BOTH)
                    }, modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(), shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "Swipe Left + Right",
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
            }
        }
    }

    private fun navigateToSwipeAbleViewActivity(context: Context, swipeDirection: SwipeDirection) {
        val intent = Intent(context, SwipeAbleViewActivity::class.java)
        intent.putExtra("SwipeDirection", swipeDirection)
        startActivity(intent)
    }
}