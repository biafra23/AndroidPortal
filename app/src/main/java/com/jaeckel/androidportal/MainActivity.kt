package com.jaeckel.androidportal

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jaeckel.androidportal.ui.theme.AndroidPortalTheme
import java.io.File

private external fun runTrin(): String

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("onCreate()")
        val tmpFile = File.createTempFile("tmp", "")
        val tmpDir = tmpFile.getParentFile()
        android.system.Os.setenv("HOME", tmpDir.absolutePath, true)
        android.system.Os.setenv("RUST_BACKTRACE", "full", true)
        android.system.Os.setenv("RUST_LOG", "debug", true)
        System.loadLibrary("trin")

        val result = runTrin()
        Toast.makeText(this, result, Toast.LENGTH_LONG).show()

        setContent {
            AndroidPortalTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Greeting("Android")
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
