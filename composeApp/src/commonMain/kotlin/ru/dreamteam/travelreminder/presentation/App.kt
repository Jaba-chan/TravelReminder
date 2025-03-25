package ru.dreamteam.travelreminder.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.sp
import io.ktor.websocket.Frame
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import ru.dreamteam.travelreminder.data.remoute.repository.TravelRepositoryImpl
import ru.dreamteam.travelreminder.domen.model.Travel
import travelreminder.composeapp.generated.resources.Res
import travelreminder.composeapp.generated.resources.ic_add

@Preview
@Composable
fun App() {
    val viewModel = TravelViewModel(TravelRepositoryImpl())
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                content = {
                    Icon(
                        painter = painterResource(Res.drawable.ic_add),
                        contentDescription = null
                    )
                }
            )
        }
    ) {
        Box {
            Column {
                Text(
                    text = if (viewModel.travels.collectAsState().value.isNotEmpty()) viewModel.travels.collectAsState().value.get(
                        0
                    ).destinationByAddress ?: "ffff" else "fffff", fontSize = 30.sp
                )
            }
        }
    }
}