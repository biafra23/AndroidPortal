package com.jaeckel.androidportal

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaeckel.androidportal.ui.theme.AndroidPortalTheme
import okhttp3.OkHttpClient
import samba.Samba
import java.io.File

private external fun runTrin(): String

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("onCreate()...-+")
        //val logger = LoggerFactory.getLogger("MyApp")
        //logger.info("Hello World")
//        val tmpFile = File.createTempFile("tmp", "")
//        val tmpDir = tmpFile.getParentFile()
//        android.system.Os.setenv("HOME", tmpDir.absolutePath, true)
//        android.system.Os.setenv("RUST_BACKTRACE", "full", true)
//        android.system.Os.setenv("RUST_LOG", "debug", true)
//        Toast.makeText(this, "System.loadLibrary(trin_jni_wrapper)", Toast.LENGTH_LONG).show()

//        System.loadLibrary("trin_jni_wrapper")
//        Thread({
//            val result = runTrin()
//            Toast.makeText(this, result, Toast.LENGTH_LONG).show()
//        }).start()

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

                                Samba.main(arrayOf("http://localhost:8545"))
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
