package com.example.ssjetpackcomposeswipetodelete.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ssjetpackcomposeswipetodelete.swipetodelete.SwipeAbleItemCell
import com.example.ssjetpackcomposeswipetodelete.swipetodelete.SwipeDirection
import com.example.ssjetpackcomposeswipetodelete.ui.theme.SSJetpackComposeSwipeToDeleteTheme

@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SSJetpackComposeSwipeToDeleteTheme {
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
                title = { Text("SWIPE TO DELETE") },
            )
        }) {
            Column() {
                Button(
                    onClick = {
                        navigateToSwipeToDeleteActivity(context, SwipeDirection.LEFT)
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
                        navigateToSwipeToDeleteActivity(context, SwipeDirection.RIGHT)
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
                        navigateToSwipeToDeleteActivity(context, SwipeDirection.BOTH)
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

    private fun navigateToSwipeToDeleteActivity(context: Context, swipeDirection: SwipeDirection) {
        val intent = Intent(context, SwipeToDeleteDirectionActivity::class.java)
        intent.putExtra("SwipeDirection", swipeDirection)
        startActivity(intent)
    }
}