package com.example.ssjetpackcomposeswipeableview.ui

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ssjetpackcomposeswipeableview.SwipeAbleItemView
import com.example.ssjetpackcomposeswipeableview.SwipeDirection
import com.example.ssjetpackcomposeswipeableview.ui.theme.Background
import com.example.ssjetpackcomposeswipeableview.ui.theme.Primary
import com.example.ssjetpackcomposeswipeableview.ui.theme.SSJetpackComposeSwipeAbleViewTheme
import com.example.ssjetpackcomposeswipeableview.ui.theme.Secondary

@ExperimentalMaterialApi
class SwipeAbleViewActivity: ComponentActivity() {

    var swipeDirection: SwipeDirection? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            swipeDirection = intent.extras?.get("SwipeDirection") as? SwipeDirection
            SSJetpackComposeSwipeAbleViewTheme {
                Surface(color = Background) {
                    MainContent()
                }
            }
        }
    }

    @Preview
    @Composable
    fun MainContent() {
        val context = LocalContext.current
        val items = remember { mutableStateListOf<Int>() }
        items.addAll(0..10)
        Scaffold(topBar = {
            TopAppBar(
                title = {
                    Text("SWIPE ${swipeDirection?.name}")
                },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
                backgroundColor = Color.White
            )
        }) {
            Column {
                LazyColumn(
                    contentPadding = PaddingValues(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(items.size) {
                        CellItem(number = items[it], context)
                    }
                }
            }
        }
    }

    @Composable
    fun CellItem(number: Int, context: Context) {
        SwipeAbleItemView(
            // Pair(Icon, Id)
            leftViewIcons = arrayListOf(Triple(rememberVectorPainter(image = Icons.Filled.Delete), Color.White,"btnDeleteLeft")),
            rightViewIcons = arrayListOf(Triple((rememberVectorPainter(image = Icons.Filled.Delete)), Color.White,"btnDeleteRight")),
            position = number,
            swipeDirection = swipeDirection ?: SwipeDirection.BOTH,
            onClick = { // Pair(Position, Id)
                Toast.makeText(context, "${it.second} clicked. Position :- ${it.first}", Toast.LENGTH_SHORT)
                    .show()
            },
            leftViewWidth = 70.dp,
            rightViewWidth = 70.dp,
            height = 60.dp,
            fractionalThreshold = 0.3f,
            leftViewBackgroundColor = Primary,
            rightViewBackgroundColor = Primary,
            cornerRadius = 4.dp,
            leftSpace = 10.dp,
            rightSpace = 10.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(Secondary, shape = RoundedCornerShape(4.dp)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Item Number $number", color = Color.Black)
            }
        }
    }
}