package se.magictechnology.pia14android4mar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun Trellotest(trelloviewmodel : TrelloViewModel = viewModel()) {
    Column(modifier = Modifier.fillMaxSize()) {

    }
}


@Preview(showBackground = true)
@Composable
fun TrellotestPreview() {
    Trellotest()
}