package com.jaeckel.androidportal

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jaeckel.androidportal.ui.theme.AndroidPortalTheme
import samba.Samba
import timber.log.Timber

//import org.slf4j.LoggerFactory
//import samba.Samba


//private external fun runTrin(): String
//private val logger = LoggerFactory.getLogger(MainActivity::class.java)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("MainActivity.onCreate()...")

        setContent {
            AndroidPortalTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Greeting("Android")
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(onClick = {
                            Toast.makeText(applicationContext, "Starting Samba.main()...", Toast.LENGTH_LONG).show()
                            /* do something on click */
                            Thread({
//                                val client = OkHttpClient()
//                                val request = okhttp3.Request.Builder()
//                                    .url("http://localhost:8545")
//                                    .build()
//                                val response = client.newCall(request).execute()
//                                println("----> " + response.body!!.string())

                                Samba.main(arrayOf())
                            }).start()
                        }) {
                            Text("Start Samba")
                        }
                    }
                }
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
