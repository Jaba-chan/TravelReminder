package ru.dreamteam.travelreminder.presentation.show_map

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.stringResource
import ru.dreamteam.travelreminder.domen.model.PlaceSuggestion
import travelreminder.composeapp.generated.resources.Res
import travelreminder.composeapp.generated.resources.search

@Composable
fun PlaceSuggestionsScreen(
    viewModel: MapViewModel,
    isOriginPlace: Boolean,
    returnToMap: () -> Unit,
) {
    Column(modifier = Modifier
        .fillMaxSize()

    ) {
        OutlinedTextField(
            value           = viewModel.placeSuggestionsQuery.value,
            onValueChange   = { viewModel.onPlaceSuggestionsQueryTextChanged(it) },
            singleLine      = true,

            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder     = {
                Text(stringResource(Res.string.search))
                              },
            leadingIcon = {
                Icon(
                    imageVector         = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription  = null,
                    modifier = Modifier
                        .clickable(onClick = returnToMap)
                )
            },
            trailingIcon = {
                if (viewModel.placeSuggestionsQuery.value.isNotEmpty()) {
                    Icon(
                        imageVector         = Icons.Default.Close,
                        contentDescription  = null,
                        modifier = Modifier
                            .clickable { viewModel.onClearQueryButtonPressed() }
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = MaterialTheme.colorScheme.primary
            )
        )
        SuggestionsList(
            suggestions       = viewModel.suggestions.value,
            onSuggestionClick = {
                returnToMap()
                viewModel.getPlaceCoordinates(it, isOriginPlace)
            },
        )
    }
}

@Composable
fun SuggestionsList(
    suggestions: List<PlaceSuggestion>,
    onSuggestionClick: (PlaceSuggestion) -> Unit,
) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
    ) {
        items(suggestions) { suggestion ->
            SuggestionRow(
                suggestion  = suggestion,
                onClick     = { onSuggestionClick(suggestion) },
            )
            Divider()
        }
    }
}

@Composable
private fun SuggestionRow(
    suggestion: PlaceSuggestion,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick  = onClick)
            .padding(
                vertical   = 12.dp,
                horizontal = 16.dp)
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector         = Icons.Default.Place,
            contentDescription  = null,
            modifier = Modifier.size(24.dp)
        )

        Spacer(Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text    = suggestion.title,
                style   = MaterialTheme.typography.labelLarge
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text    = suggestion.description,
                style   = MaterialTheme.typography.labelLarge.copy(letterSpacing = 0.5.sp),
                color   = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f)
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



