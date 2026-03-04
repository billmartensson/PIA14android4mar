package se.magictechnology.pia14android4mar

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import se.magictechnology.pia14android4mar.ui.theme.PIA14android4marTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //demoapi()

        enableEdgeToEdge()
        setContent {
            PIA14android4marTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Trellotest()
                }
            }
        }
    }

}

@Serializable
data class Chuckjoke(val created_at : String, val value : String)
fun demoapi() {
    Log.d("PIA14DEBUG", "THIS IS DEMO API")
    val client = OkHttpClient()

    CoroutineScope(Dispatchers.IO).launch {
        val request = Request.Builder()
            .url("https://api.chucknorris.io/jokes/random")
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



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PIA14android4marTheme {
        Greeting("Android")
    }
}