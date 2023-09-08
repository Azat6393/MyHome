package com.azatberdimyradov.myhome

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.azatberdimyradov.myhome.presentation.camera.CameraItem
import com.azatberdimyradov.myhome.presentation.camera.CameraScreenState
import com.azatberdimyradov.myhome.presentation.camera.CameraViewModel
import com.azatberdimyradov.myhome.presentation.camera.CamerasScreen
import com.azatberdimyradov.myhome.presentation.component.TabHome
import com.azatberdimyradov.myhome.presentation.door.DoorScreenState
import com.azatberdimyradov.myhome.presentation.door.DoorViewModel
import com.azatberdimyradov.myhome.presentation.door.DoorsScreen
import com.azatberdimyradov.myhome.ui.theme.MyHomeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyHomeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val doorViewModel = hiltViewModel<DoorViewModel>()
                    val cameraViewModel = hiltViewModel<CameraViewModel>()

                    val pagerSelect = rememberPagerState(initialPage = 0)
                    val scope = rememberCoroutineScope()

                    Column(modifier = Modifier.fillMaxSize()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Мой дом",
                            color = Color.Black,
                            fontSize = 25.sp,
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily.Cursive,
                            fontStyle = FontStyle.Normal,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(20.dp))

                        TabHome(
                            currentScreenId = pagerSelect.currentPage,
                            onItemSelected = { tabPage ->
                                scope.launch {
                                    pagerSelect.animateScrollToPage(page = tabPage.id)
                                }
                            })

                        HorizontalPager(2, state = pagerSelect) { page ->
                            when (page) {
                                0 -> {
                                    CamerasScreen(
                                        state = cameraViewModel.state,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }

                                1 -> {
                                    DoorsScreen(
                                        state = doorViewModel.state,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

