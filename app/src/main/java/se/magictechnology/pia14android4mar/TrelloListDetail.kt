package se.magictechnology.pia14android4mar

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TrelloListDetail(
    trelloViewmodel : TrelloViewModel = viewModel(),
    currentlist : TrelloList, goCard : (TrelloCard) -> Unit
) {

    var trellocards = trelloViewmodel.trellocards.collectAsState()

    var loading by remember { mutableStateOf(false) }

    var addcardname by remember { mutableStateOf("") }

    LaunchedEffect(true) {
        Log.d("PIA14DEBUG", "LIST DETAIL LAUNCH")
        loading = true
        trelloViewmodel.getCards(currentlist)
        loading = false
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text("TRELLO LIST DETAIL")
        Text(currentlist.name)

        Row {
            TextField(
                modifier = Modifier.weight(1f),
                value = addcardname,
                onValueChange = { addcardname = it }
            )

            Button(onClick = {
                loading = true
                trelloViewmodel.createCard(addcardname, currentlist, loadDone =  {
                    loading = false
                })
            }) {
                Text("Add")
            }
        }

        if(loading) {
            Text("Loading...")
        }
        LazyColumn() {
            items(trellocards.value.size) { index ->
                Row(modifier = Modifier
                    .height(80.dp)
                    .clickable {
                        goCard(trellocards.value[index])
                    }
                ) {
                    Text(trellocards.value[index].name)

                    Button(onClick = {
                        trelloViewmodel.deleteCard(trellocards.value[index], currentlist)
                    }) {
                        Text("Delete")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TrelloListDetailPreview() {
    TrelloListDetail(currentlist = TrelloList(id = "1", name = "Testlist"), goCard = {})
}