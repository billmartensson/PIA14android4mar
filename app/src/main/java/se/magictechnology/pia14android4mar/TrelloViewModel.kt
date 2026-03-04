package se.magictechnology.pia14android4mar

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request

class TrelloViewModel : ViewModel() {

    val client = OkHttpClient()

    val baseurl = "https://api.trello.com/1"
    val apikey = ""
    val apitoken = ""

    fun getBoard() {
        val apiurl = "$baseurl/boards/9999999?key=$apikey&token=$apitoken"

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

                val thejoke = Json {ignoreUnknownKeys = true}.decodeFromString<Chuckjoke>(theresponsetext)

                Log.d("PIA14DEBUG", thejoke.value)
            }

        }
    }

    fun createList() {

    }
}