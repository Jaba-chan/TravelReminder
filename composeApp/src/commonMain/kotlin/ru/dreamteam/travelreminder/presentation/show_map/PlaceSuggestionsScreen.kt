package ru.dreamteam.travelreminder.presentation.show_map

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import ru.dreamteam.travelreminder.domen.model.PlaceSuggestion

@Composable
fun PlaceSuggestionsScreen(
    viewModel: MapViewModel,
    returnToMap: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = viewModel.placeSuggestionsQuery.value,
            onValueChange = { viewModel.onPlaceSuggestionsQueryTextChanged(it) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text("Search places") },
            leadingIcon = {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.clickable(onClick = returnToMap)
                )
            },
            trailingIcon = {
                if (viewModel.placeSuggestionsQuery.value.isNotEmpty()) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            viewModel.onClearQueryButtonPressed()
                        }
                    )
                }
            }
        )
        SuggestionsList(
            suggestions = viewModel.suggestions.value,
            onSuggestionClick = {viewModel.getPlaceCoordinates(it)},
        )
    }
}

@Composable
fun SuggestionsList(
    suggestions: List<PlaceSuggestion>,
    onSuggestionClick: (PlaceSuggestion) -> Unit,
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(suggestions) { suggestion ->
            SuggestionRow(
                suggestion = suggestion,
                onClick = { onSuggestionClick(suggestion) },
                insertTheAddress = onSuggestionClick
            )
            Divider()
        }
    }
}

@Composable
private fun SuggestionRow(
    suggestion: PlaceSuggestion,
    onClick: () -> Unit,
    insertTheAddress: (PlaceSuggestion) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Place,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )

        Spacer(Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = suggestion.title,
                style = MaterialTheme.typography.subtitle1
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = suggestion.description,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
            )
        }

        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .rotate(45f)
        )
    }
}



