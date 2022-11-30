package view.next_schedules.search_line.search_stop_list.next_line_schedules

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import model.DTO.Station
import view.lines_map_list.SearchDisplay
import view.more_view.all_services_list.SearchTextField

@Stable
class SearchStopListSearchState(
    query: TextFieldValue,
    focused: Boolean,
    searching: Boolean,
    searchResults: List<Station>
) {
    var query by mutableStateOf(query)
    var focused by mutableStateOf(focused)
    var searching by mutableStateOf(searching)
    var searchResults by mutableStateOf(searchResults)

    val searchDisplay: SearchDisplay
        get() = when {
            !focused && query.text.isEmpty() -> SearchDisplay.INITIALRESULTS
            searchResults.isEmpty() -> SearchDisplay.NORESULT
            else -> SearchDisplay.RESULTS
        }

    override fun toString(): String {
        return "🚀 State query: $query, focused: $focused, searching: $searching " +
                "searchResults: ${searchResults.size}, " +
                "searchDisplay: $searchDisplay"
    }
}

@Composable
fun rememberSearchState(
    query: TextFieldValue = TextFieldValue(""),
    focused: Boolean = false,
    searching: Boolean = false,
    searchResults: List<Station> = emptyList()
): SearchStopListSearchState {
    return remember {
        SearchStopListSearchState(
            query = query,
            focused = focused,
            searching = searching,
            searchResults = searchResults
        )
    }
}

@Composable
private fun SearchHint(modifier: Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
        .fillMaxSize()
        .then(modifier)
    ) {
        Icon(imageVector = Icons.Rounded.Search, contentDescription = null)

        Spacer(modifier = Modifier
            .width(10.dp)
        )

        Text("Rechercher", color = Color(0xff757575))
    }
}

//

@ExperimentalAnimationApi
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchStopListSearchBar(
    query: TextFieldValue,
    onQueryChange: (TextFieldValue) -> Unit,
    onSearchFocusChange: (Boolean) -> Unit,
    onClearQuery: () -> Unit,
    onBack: () -> Unit,
    searching: Boolean,
    focused: Boolean,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier
        .fillMaxWidth()
    ) {
        AnimatedVisibility(visible = focused) {
            //Back button
            IconButton(modifier = Modifier
                .padding(start = 2.dp)
                , onClick = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                    onBack()
                }) {

                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
            }
        }

        SearchTextField(
            query,
            onQueryChange,
            onSearchFocusChange,
            onClearQuery,
            searching,
            focused,
            modifier
                .weight(1f)
        )
    }
}