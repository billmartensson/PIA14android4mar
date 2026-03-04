package se.magictechnology.pia14android4mar

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class TrelloViewModel : ViewModel() {

    private val client = OkHttpClient()

    private val baseurl = "https://api.trello.com/1"
    private val apikey = ""
    private val apitoken = ""

    private val boardid = "LlZObqEW"


    private var _trellolists = MutableStateFlow(listOf<TrelloList>())
    val trellolists: StateFlow<List<TrelloList>> = _trellolists.asStateFlow()


    fun getBoard() {
        val apiurl = "$baseurl/boards/$boardid?key=$apikey&token=$apitoken"

        CoroutineScope(Dispatchers.IO).launch {
            val request = Request.Builder()
                .url(apiurl)
                .build()

            client.newCall(request).execute().use { response ->

                //response.code

                if (!response.isSuccessful) {
                    //"Unexpected code $response"
                    Log.d("PIA14DEBUG", "API FAIL")
                } else {
                    Log.d("PIA14DEBUG", "API OK")
                }

                val theresponsetext = response.body!!.string()
                Log.d("PIA14DEBUG", theresponsetext)

                //val thejoke = Json {ignoreUnknownKeys = true}.decodeFromString<Chuckjoke>(theresponsetext)
            }

        }
    }

    fun getLists() {
        val apiurl = "$baseurl/boards/$boardid/lists?key=$apikey&token=$apitoken"

        CoroutineScope(Dispatchers.IO).launch {
            val request = Request.Builder()
                .url(apiurl)
                .build()

            client.newCall(request).execute().use { response ->

                //response.code

                if (!response.isSuccessful) {
                    //"Unexpected code $response"
                    Log.d("PIA14DEBUG", "API FAIL")
                } else {
                    Log.d("PIA14DEBUG", "API OK")
                }

                val theresponsetext = response.body!!.string()
                Log.d("PIA14DEBUG", theresponsetext)

                val apitrellolists =
                    Json { ignoreUnknownKeys = true }.decodeFromString<List<TrelloList>>(
                        theresponsetext
                    )

                _trellolists.value = apitrellolists


            }
        }
    }


    fun getCards(getlist: TrelloList) {
        val apiurl = "$baseurl/lists/${getlist.id}/cards?key=$apikey&token=$apitoken"

        CoroutineScope(Dispatchers.IO).launch {
            val request = Request.Builder()
                .url(apiurl)
                .build()

            client.newCall(request).execute().use { response ->

                //response.code

                if (!response.isSuccessful) {
                    //"Unexpected code $response"
                    Log.d("PIA14DEBUG", "API FAIL")
                } else {
                    Log.d("PIA14DEBUG", "API OK")
                }

                val theresponsetext = response.body!!.string()
                Log.d("PIA14DEBUG", theresponsetext)


            }
        }
    }


    fun createList(listname: String) {

        var board = "65eebade8efb3a50c751ead5"

        val apiurl =
            "$baseurl/lists/?name=$listname&idBoard=$board&key=$apikey&token=$apitoken"

        CoroutineScope(Dispatchers.IO).launch {
            val request = Request.Builder()
                .url(apiurl)
                .method("POST", "".toRequestBody())
                .build()

            client.newCall(request).execute().use { response ->

                //response.code
                if (!response.isSuccessful) {
                    //"Unexpected code $response"
                    Log.d("PIA14DEBUG", "API FAIL")
                } else {
                    Log.d("PIA14DEBUG", "API OK")
                }

                val theresponsetext = response.body!!.string()
                Log.d("PIA14DEBUG", theresponsetext)


            }
        }
    }
}



/*
    [
        {"id":"69a8005823bd35ebeeda40fb","name":"TODO","closed":false,"color":null,"idBoard":"65eebade8efb3a50c751ead5","pos":16384,"subscribed":false,"softLimit":null,"type":null,"datasource":{"filter":false}},
        {"id":"69a8008fe4751b486c9b95fa","name":"Doing","closed":false,"color":null,"idBoard":"65eebade8efb3a50c751ead5","pos":32768,"subscribed":false,"softLimit":null,"type":null,"datasource":{"filter":false}},{"id":"69a80095927216eb412f4f30","name":"Testing","closed":false,"color":null,"idBoard":"65eebade8efb3a50c751ead5","pos":49152,"subscribed":false,"softLimit":null,"type":null,"datasource":{"filter":false}},{"id":"69a80099ec96c8c046219af3","name":"Done","closed":false,"color":null,"idBoard":"65eebade8efb3a50c751ead5","pos":65536,"subscribed":false,"softLimit":null,"type":null,"datasource":{"filter":false}}]
 */