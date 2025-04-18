package com.jaeckel.androidportal

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.launch
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
//import androidx.datastore.core.use
//import androidx.privacysandbox.tools.core.generator.build
import com.jaeckel.androidportal.ui.theme.AndroidPortalTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import org.slf4j.LoggerFactory
import samba.Samba
import timber.log.Timber

private val logger = LoggerFactory.getLogger(MainActivity::class.java)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logger.info("MainActivity.onCreate()...")

        setContent {
            AndroidPortalTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Greeting("Android")
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(onClick = {
                                Toast.makeText(applicationContext, "Starting Samba.main()...", Toast.LENGTH_LONG).show()
                                /* do something on click */
                                Thread({
                                    Samba.main(arrayOf("--data-path=/data/user/0/com.jaeckel.androidportal/files/samba/"))
                                }).start()
                            }) {
                                Text("Start Samba")
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = {
                                Toast.makeText(applicationContext, "Getting latest block number...", Toast.LENGTH_LONG).show()
                                getLatestBlockNumber()
                            }) {
                                Text("Get latest block number")
                            }
                        }
                    }
                }
            }
        }
    }

    private val client = OkHttpClient()
    private val url = "http://127.0.0.1:8545" // Replace with your IP and port
    private val mediaType = "application/json".toMediaType()
    private val logger = LoggerFactory.getLogger(AndroidPortalApplication::class.java)

        fun getLatestBlockNumber() {
        CoroutineScope(Dispatchers.IO).launch {
            val json = JSONObject().apply {
                put("jsonrpc", "2.0")
                put("method", "eth_blockNumber")
                put("params", emptyList<String>())
                put("id", 1)
            }.toString()

            val body = json.toRequestBody(mediaType)
            val request = Request.Builder()
                .url(url)
                .post(body)
                .build()

            try {
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        val responseBody = response.body?.string()
                        logger.debug("JSONRPC", "Response: $responseBody")
                        // Parse the JSON response here
                    } else {
                        logger.error("JSONRPC", "Error: ${response.code}")
                    }
                }
            } catch (e: Exception) {
                logger.error("JSONRPC", "Exception: ${e.message}", e)
            }
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
    AndroidPortalTheme {
        Greeting("Android")
    }
}

