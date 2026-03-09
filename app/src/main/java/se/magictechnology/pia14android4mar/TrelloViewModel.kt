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

    private var _trellocards = MutableStateFlow(listOf<TrelloCard>())
    val trellocards: StateFlow<List<TrelloCard>> = _trellocards.asStateFlow()

    private var _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()


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

    fun trelloApiCall(endpoint : String, apimethod : String, postdata : String? = null, apiresult: (String) -> Unit) {
        val apiurl = "$baseurl/$endpoint?key=$apikey&token=$apitoken"

        Log.d("PIA14DEBUG", "APIURL: $apiurl")
        Log.d("PIA14DEBUG", "POSTDATA: $postdata")
        Log.d("PIA14DEBUG", "APIMETHOD: $apimethod")

        CoroutineScope(Dispatchers.IO).launch {
            var request = Request.Builder()

            if(postdata != null) {
                request = request.header("Content-Type", "application/json")
                request = request.method(apimethod, postdata.toRequestBody())
            } else {
                if(apimethod != "GET") {
                    request = request.method(apimethod, "".toRequestBody())
                }
            }

            request = request.url(apiurl)

            client.newCall(request.build()).execute().use { response ->

                //response.code

                if (!response.isSuccessful) {
                    //"Unexpected code $response"
                    Log.d("PIA14DEBUG", "API FAIL")
                } else {
                    Log.d("PIA14DEBUG", "API OK")
                }

                val theresponsetext = response.body!!.string()
                Log.d("PIA14DEBUG", theresponsetext)

                apiresult(theresponsetext)
            }
        }
    }

    fun getLists() {
        val apiurl = "boards/$boardid/lists"

        trelloApiCall(endpoint = apiurl, apimethod = "GET") { jsondata ->
            val apitrellolists =
                Json { ignoreUnknownKeys = true }.decodeFromString<List<TrelloList>>(
                    jsondata
                )

            _trellolists.value = apitrellolists
        }
    }

    fun getListsOLD() {
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

                val apitrellocards =
                    Json { ignoreUnknownKeys = true }.decodeFromString<List<TrelloCard>>(
                        theresponsetext
                    )

                _trellocards.value = apitrellocards
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

                getLists()
            }
        }
    }

    fun createCard(cardname: String, addlist : TrelloList, loadDone: () -> Unit) {

        var board = "65eebade8efb3a50c751ead5"

        val apiurl =
            "$baseurl/cards/?idList=${addlist.id}&name=$cardname&key=$apikey&token=$apitoken"

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

                loadDone()
                getCards(addlist)
            }
        }
    }

    fun deleteCard(card : TrelloCard, trellolist : TrelloList) {
        var board = "65eebade8efb3a50c751ead5"

        val apiurl =
            "$baseurl/cards/${card.id}?key=$apikey&token=$apitoken"

        CoroutineScope(Dispatchers.IO).launch {
            val request = Request.Builder()
                .url(apiurl)
                .method("DELETE", "".toRequestBody())
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

                getCards(trellolist)
            }
        }
    }


    fun updateCard(card : TrelloCard, saveDone: (Boolean) -> Unit) {
        val apiurl = "cards/${card.id}"

        val jsondata = Json.encodeToString(card)

        trelloApiCall(endpoint = apiurl, apimethod = "PUT", postdata = jsondata) { jsondata ->
            CoroutineScope(Dispatchers.Main).launch {
                saveDone(true)
            }
        }
    }

    fun updateCardOLD(card : TrelloCard, saveDone: (Boolean) -> Unit) {
        val apiurl =
            "$baseurl/cards/${card.id}?key=$apikey&token=$apitoken"

        val postjson = Json.encodeToString(card)

        CoroutineScope(Dispatchers.IO).launch {
            val request = Request.Builder()
                .url(apiurl)
                .header("Content-Type", "application/json")
                .method("PUT", postjson.toRequestBody())
                .build()

            client.newCall(request).execute().use { response ->

                //response.code
                if (!response.isSuccessful) {
                    //"Unexpected code $response"
                    Log.d("PIA14DEBUG", "API FAIL")
                    saveDone(false)
                } else {
                    Log.d("PIA14DEBUG", "API OK")
                    CoroutineScope(Dispatchers.Main).launch {
                        saveDone(true)
                    }
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