package com.example.dicething

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOutBack
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.TargetBasedAnimation
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
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
                    ) {
                        Column(
                            modifier = Modifier.background(Color.White),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {

                            Starting_text()

                            Column(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Title_text()
                                Spacer(modifier = Modifier.height(120.dp))
                                Dice_Rotation()

                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Title_text() {
    Text(
        text = "Randomizer",
        style = TextStyle(color = Color.Black, fontSize = 20.sp)
    )
}

@Composable
@Preview(name = "Start_Ani", widthDp = 500, heightDp = 200)
fun Starting_text() {
    var state by remember { mutableStateOf(true) }
    AnimatedVisibility(
        visible = state,
        enter = fadeIn(initialAlpha = 0.2f),
        exit = slideOutVertically(
            animationSpec = spring(
                stiffness = Spring.StiffnessLow,
                dampingRatio = Spring.DampingRatioMediumBouncy
            ),
            targetOffsetY = { -1208 }),
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
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
                    .clip(shape = RoundedCornerShape(100))
                    .background(Color(0xff75bdd1))
                    .padding(20.dp, 5.dp),
                textAlign = TextAlign.Center
            )

            Title_text()
        }
    }
}

@Preview(name = "Dice", showBackground = true)
@Composable
fun Dice_Rotation() {
    var rotated by remember { mutableStateOf(0f) }
    val rotation = remember { androidx.compose.animation.core.Animatable(rotated) }
    var rotationstate by remember { mutableStateOf(false) }
    val rotationby: Float = 720F
    var randomvalue by remember { mutableIntStateOf(0) }
    var minrange by remember { mutableStateOf<Int?>(0) }
    var maxrange by remember { mutableStateOf<Int?>(10) }
    var mintext by remember { mutableStateOf(minrange.toString()) }
    var maxtext by remember { mutableStateOf(maxrange.toString()) }
    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(rotationstate) {
        if (rotationstate) {
            rotation.animateTo(
                targetValue = rotated + rotationby,
                animationSpec = tween(2000, easing = EaseInOutBack)
            ) { rotated = value }
        } else {
            rotation.animateTo(
                targetValue = 0F,
                animationSpec = tween(0)
            ) { rotated = value }
        }
        if (rotated >= rotationby) {
            rotationstate = !rotationstate
            randomvalue = (minrange!!..maxrange!!).random()
            isError = false
        }
    }
    Image(
        painter = rememberVectorPainter(image = ImageVector.vectorResource(R.drawable.dice_vec)),
        contentDescription = null,
        modifier = Modifier
            .size(240.dp)
            .rotate(rotated)
            .clickable(enabled = !rotationstate) {
                minrange = mintext.toInt()
                maxrange = maxtext.toInt()
                if (maxrange!! > minrange!!) {
                    rotationstate = !rotationstate
                } else {
                    isError = true
                }
            }
    )
    Spacer(Modifier.height(20.dp))
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            enabled = !rotationstate,
            onClick = {
                minrange = mintext.toInt()
                maxrange = maxtext.toInt()
                if (maxrange!! > minrange!!) {
                    rotationstate = !rotationstate
                } else {
                    isError = true
                }
              },
            modifier = Modifier
        ) {
            Text(stringResource(R.string.roll))
        }

        Text(
            text = "$randomvalue",
            modifier = Modifier.padding(20.dp)
                .wrapContentWidth()
                .clip(shape = RoundedCornerShape(100))
                .background(Color(0xff75bdd1))
                .padding(20.dp, 5.dp)
            ,
            style = TextStyle(color = Color.Black, fontSize = 22.sp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            val sets: Any
            TextField(
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                value = mintext,
                onValueChange = {
                    if (it.isDigitsOnly()) {
                        mintext = it
                    }
                },
                label = { Text("Max") },
                singleLine = true,
                isError = isError,
                supportingText = {
                    if(isError){
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Wrong Input",
                            color = Color.Red
                        )
                    }
                },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.width(120.dp),
                colors = TextFieldDefaults.colors(
                    errorIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )
            TextField(
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                value = maxtext,
                onValueChange = { if (it.isDigitsOnly()) {
                                     maxtext = it
                                } },
                label = { Text("Max") },
                singleLine = true,
                isError = isError,
                supportingText = {
                    if(isError){
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Wrong Input",
                            color = Color.Red
                        )
                    }
                },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.width(120.dp),
                colors = TextFieldDefaults.colors(
                    errorIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )
        }
    }
}
