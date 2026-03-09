package se.magictechnology.pia14android4mar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TrelloCardDetail(
    trelloViewmodel : TrelloViewModel = viewModel(),
    currentcard : TrelloCard,
    goBack: () -> Unit
) {

    var loading by remember { mutableStateOf(false) }

    var cardname by remember { mutableStateOf(currentcard.name) }
    var carddesc by remember { mutableStateOf(currentcard.desc) }

    Column(modifier = Modifier.fillMaxSize()) {
        Text("TRELLO CARD DETAIL")
        Text(currentcard.name)
        Text(currentcard.desc)

        TextField(
            modifier = Modifier,
            value = cardname,
            onValueChange = { cardname = it }
        )
        TextField(
            modifier = Modifier,
            value = carddesc,
            onValueChange = { carddesc = it }
        )


        Button(onClick = {
            currentcard.name = cardname
            currentcard.desc = carddesc
            trelloViewmodel.updateCard(currentcard) { saveresult ->
                if(saveresult) {
                    goBack()
                } else {
                    // ERROR
                }
            }
        }) {
            Text("Save")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TrelloCardDetailPreview() {
    TrelloCardDetail(
        currentcard = TrelloCard("1", "Testkort", ""),
        goBack = {}
    )
}
