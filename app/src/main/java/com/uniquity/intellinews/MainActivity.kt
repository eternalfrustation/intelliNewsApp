package com.uniquity.intellinews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cycleone.cycleoneapp.ui.components.PrestyledText
import com.mikepenz.markdown.m3.Markdown
import com.uniquity.intellinews.ui.theme.IntelliNewsTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IntelliNewsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var serverAddress by remember {
        mutableStateOf("https://192.168.1.1:8080")
    }
    val topics = listOf("technology", "sports", "stocks", "world politics")
    var showLoading by remember {
        mutableStateOf(false)
    }
    var currentTopic by remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier.fillMaxWidth(0.8f)) {

        PrestyledText().Regular(placeholder = "https://192.168.1.1:8080", onChange = {serverAddress = it;}, label = "Server Address")
        PrestyledText().Regular(placeholder = "Indian Politics", onChange = {currentTopic = it}, label = "Custom Topic")
        if (currentTopic != "") {
            Topic(topic = currentTopic , serverAddress = serverAddress)
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            topics.forEach { topic -> Button({showLoading = true;
                currentTopic = topic;
            }, modifier = Modifier.fillMaxWidth()) {Text(topic)} }
        }
    }
}

@Composable
fun Topic(topic: String, serverAddress: String) {
    var summary by remember {
        mutableStateOf("")
    }
     getTopicSummary(topic, serverAddress) { s -> summary = s }
    Text(summary)
}
fun getTopicSummary(topic: String, serverAddress: String, onSuccess: (String) -> Unit) {
    val client = OkHttpClient();
    val request = Request.Builder()
        .url("$serverAddress/news?topic=$topic")
        .build();
    val response = client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()

        }
        override  fun  onResponse(call: Call, r: Response) {
            r.use { response ->
                if (!response.isSuccessful) {
                    throw IOException("Unexpected code $response")
                }
                onSuccess(response.body.toString())
            }
        }
    })
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IntelliNewsTheme {
        Greeting("Android")
    }
}