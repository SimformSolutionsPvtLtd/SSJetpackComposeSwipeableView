package com.example.ssjetpackcomposeswipetodelete.ui

import android.content.Context
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
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ssjetpackcomposeswipetodelete.swipetodelete.SwipeAbleItemCell
import com.example.ssjetpackcomposeswipetodelete.swipetodelete.SwipeDirection
import com.example.ssjetpackcomposeswipetodelete.ui.theme.SSJetpackComposeSwipeToDeleteTheme

@ExperimentalMaterialApi
class SwipeToDeleteDirectionActivity: ComponentActivity() {

    var swipeDirection: SwipeDirection? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            swipeDirection = intent.extras?.get("SwipeDirection") as? SwipeDirection
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
                }
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
        SwipeAbleItemCell(
            // Pair(Icon, Id)
            leftViewIcons = arrayListOf(Pair(Icons.Filled.Edit, "btnEditLeft"), Pair(Icons.Filled.Delete, "btnDeleteLeft")),
            rightViewIcons = arrayListOf(Pair(Icons.Filled.Edit, "btnEditRight")),
            position = number,
            swipeDirection = swipeDirection ?: SwipeDirection.BOTH,
            onClick = { // Pair(Position, Id)
                Toast.makeText(context, "${it.second} clicked. Position :- ${it.first}", Toast.LENGTH_SHORT)
                    .show()
            },
            leftViewWidth = 120.dp,
            rightViewWidth = 60.dp,
            height = 60.dp,
            fractionalThreshold = 0.3f,
            backgroundColor = MaterialTheme.colors.secondary
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(MaterialTheme.colors.background)
                    .border(1.dp, MaterialTheme.colors.primary),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(text = "Item Number $number", color = MaterialTheme.colors.primary)
            }
        }
    }
}