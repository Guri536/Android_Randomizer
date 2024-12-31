package com.example.dicething

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.captionBarPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dicething.ui.theme.DiceThingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DiceThingTheme {
                Scaffold(
                    modifier = Modifier
                        .background(Color.Black)
                        .statusBarsPadding()
                        .fillMaxSize()
                        .background(Color.White)
                ) { padding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                    ){
                        Column(
                            modifier = Modifier.background(Color.White),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            var state by remember { mutableStateOf(true) }
                            AnimatedVisibility(
                                visible = state,
                                enter = fadeIn(initialAlpha = 0.2f),
                                exit = slideOutVertically(animationSpec = spring(stiffness = Spring.StiffnessLow),
                                    targetOffsetY = {-1208}),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                ,
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,

                                ) {
                                    Text(
                                        "Start!",
                                        style = TextStyle(color = Color.Black, fontSize = 30.sp),
                                        modifier = Modifier
                                            .padding(20.dp)
                                            .clickable { state = !state }
                                            .fillMaxWidth(0.4f)
                                            .background(
                                                Color(0xFFDD5675),
                                                shape = RoundedCornerShape(corner = CornerSize(20.dp))
                                            )
                                            .border(
                                                border = BorderStroke(
                                                    width = 4.dp,
                                                    color = Color.Black
                                                ),
                                                shape = RoundedCornerShape(corner = CornerSize(20.dp))
                                            )
                                            .padding(10.dp)
                                        ,
                                        textAlign = TextAlign.Center
                                    )
                                    Title_text()
                                }
                            }

                            Column(
                                modifier = Modifier.padding(10.dp)
                                    .fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Title_text()

                                Image(painter = painterResource(R.drawable.dice),
                                    contentDescription = null)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Title_text(){
    Text(text = "Randomizer",
        style = TextStyle(color = Color.Black, fontSize = 20.sp)
    )
}