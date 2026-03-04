package se.magictechnology.pia14android4mar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun Trellotest(trelloviewmodel : TrelloViewModel = viewModel()) {

    var trellolists = trelloviewmodel.trellolists.collectAsState()


    Column(modifier = Modifier.fillMaxSize().padding(30.dp)) {
        Button(onClick = {
            //trelloviewmodel.getBoard()
            //trelloviewmodel.getLists()
            trelloviewmodel.createList("testa skapa")
        }) {
            Text(("API"))
        }

        trellolists.value.forEach {
            Text(it.name)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TrellotestPreview() {
    Trellotest()
}