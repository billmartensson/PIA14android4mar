package se.magictechnology.pia14android4mar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute

@Composable
fun TrelloNav() {

    val navController = rememberNavController()

    val trelloViewModel : TrelloViewModel = viewModel()

    NavHost(navController = navController, startDestination = "trellolists") {

        composable("trellolists") {
            TrelloLists(
                trelloViewmodel = trelloViewModel,
                goList = { golist ->
                    navController.navigate(golist)
                }
            )
        }

        composable<TrelloList> { backstackEntry ->
            val navlist : TrelloList = backstackEntry.toRoute()

            TrelloListDetail(
                trelloViewmodel = trelloViewModel,
                currentlist = navlist,
                goCard = { goCard ->
                    navController.navigate(goCard)
                }
            )
        }

        composable<TrelloCard> { backstackEntry ->
            val navcard : TrelloCard = backstackEntry.toRoute()
            TrelloCardDetail(
                trelloViewmodel = trelloViewModel,
                currentcard = navcard,
                goBack = {
                    navController.popBackStack()
                }
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun TrelloNavPreview() {
    TrelloNav()
}