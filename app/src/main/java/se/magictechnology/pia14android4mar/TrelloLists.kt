package se.magictechnology.pia14android4mar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TrelloLists(
    trelloViewmodel : TrelloViewModel = viewModel(),
    goList : (TrelloList) -> Unit
) {

    var trellolists = trelloViewmodel.trellolists.collectAsState()

    var addlistname by remember { mutableStateOf("") }

    var loading by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        trelloViewmodel.getLists()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text("TRELLO LISTS")

        Row {
            TextField(
                modifier = Modifier.weight(1f),
                value = addlistname,
                onValueChange = { addlistname = it }
            )

            Button(onClick = {
                trelloViewmodel.createList(addlistname)
            }) {
                Text("Add")
            }
        }

        LazyColumn() {
            items(trellolists.value.size) { index ->
                Row(modifier = Modifier.clickable {
                    goList(trellolists.value[index])
                }) {
                    Text(trellolists.value[index].name)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TrelloListsPreview() {
    TrelloLists(goList = {})
}