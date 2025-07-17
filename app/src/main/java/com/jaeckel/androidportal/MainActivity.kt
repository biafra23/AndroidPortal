package com.jaeckel.androidportal

//import androidx.datastore.core.use
//import androidx.privacysandbox.tools.core.generator.build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jaeckel.androidportal.ui.theme.AndroidPortalTheme
import com.jaeckel.trueblocks.IpfsLocalClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.kethereum.model.Address
import org.slf4j.LoggerFactory
import samba.Samba
import samba.SambaSDK
import timber.log.Timber
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


private val logger = LoggerFactory.getLogger(MainActivity::class.java)
private lateinit var filesDirPath: String
var sambaSDK: SambaSDK? = null

class MainActivity : ComponentActivity() {
    private var debugText by mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        filesDirPath = filesDir.absolutePath
        logger.info("MainActivity.onCreate()...")
        setContent {
            AndroidPortalTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        debugText = debugText,
                        onDebugMessage = { message -> appendDebug(message) }
                    )
                }
            }
        }
    }

    private fun appendDebug(message: String) {
        val timestamp = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault()).format(Date())
        val debugMessage = "[$timestamp] $message"

        debugText = if (debugText.isEmpty()) {
            debugMessage
        } else {
            "$debugText\n$debugMessage"
        }
    }


    //    val loggingInterceptor = HttpLoggingInterceptor().apply {
//        level = HttpLoggingInterceptor.Level.BODY // Set the desired logging level
//    }
//    private val client = OkHttpClient.Builder()
//        .connectTimeout(5, TimeUnit.SECONDS)
//        .readTimeout(120, TimeUnit.SECONDS)
//        .writeTimeout(15, TimeUnit.SECONDS)
//        .addInterceptor(RedirectLoggingInterceptor())
//        .addInterceptor(loggingInterceptor)
//        .build()
//    private val url = "http://127.0.0.1:8545" // Replace with your IP and port
//    private val mediaType = "application/json".toMediaType()
    private val logger = LoggerFactory.getLogger(AndroidPortalApplication::class.java)

}


@Composable
fun MainScreen(
    debugText: String,
    onDebugMessage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Button(onClick = {
                onDebugMessage("Starting Samba.main()...")
                /* do something on click */
                Thread({
                    //Samba.main(arrayOf("--data-path=/data/user/0/com.jaeckel.androidportal/files/samba/"))
                    val options = arrayOf(
                        "--data-path=$filesDirPath/samba/",
//                        "--disable-json-rpc-server",
                        "--disable-rest--server",
                        "--disable-metric--server"
                    )
                    sambaSDK = Samba.init(options)
                    onDebugMessage("Samba initialized: $sambaSDK")
                }).start()
            }) {
                Text("Start Samba")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                onDebugMessage("Connect pinata...")
                connectPinata(onDebugMessage)
            }) {
                Text("Connect Pinata")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                onDebugMessage("Check address against Trueblocks...")
                checkAddressAgainstTrueblocks()
            }) {
                Text("Check address against Trueblocks")
            }
            Button(onClick = {
//                onDebugMessage("sambaSDK: $sambaSDK")
//                logger.error("---> sambaSDK: $sambaSDK")

//               val blockNumber = 15_100_000L
//                val blockNumber = 1_149_999L // Frontier (last block)
//                val blockNumber = 1_150_000L // Homestead ?
//                val blockNumber = 1_160_001L // Homestead
//                val blockNumber = 4_370_000L // Byzantium
//                val blockNumber = 10_370_000L
//                val blockNumber = 12_000_000L // works
//                val blockNumber = 12_500_000L // works
//                val blockNumber = 12_600_000L // works
//                val blockNumber = 12_700_000L // works
//                val blockNumber = 12_750_000L // works
//                val blockNumber = 12_780_000L // works
//                val blockNumber = 12_790_000L // works
//                val blockNumber = 12_795_000L // works
//                val blockNumber = 12_799_000L // works
//                val blockNumber = 12_799_900L // works
//                val blockNumber = 12_799_940L // works
//                val blockNumber = 12_799_945L // works
                val blockNumber = 12_799_946L // works
//                val blockNumber = 12_799_947L // broken
//                val blockNumber = 12_799_950L // broken
//                val blockNumber = 12_799_990L // broken
//                val blockNumber = 12_800_000L // broken
//                val blockNumber = 13_000_000L // broken
                Thread({
                    val result = sambaSDK?.historyAPI()?.get()?.getBlockHeaderByBlockNumber("$blockNumber")?.get()
                    logger.error("---> Content key: $blockNumber -> result: ${result?.blockHash}")
                    onDebugMessage("$blockNumber: ${result?.blockHash}")

                    // Use hash to get block body
                    result?.let { header ->
                        onDebugMessage("Fetching block header for hash: ${header.blockHash}")
                        // If you want to fetch the block body, you can do it like this:
//                        val blockBody = sambaSDK?.getBlockBodyByBlockHash(header.blockHash)
                        val blockHeader = sambaSDK?.historyAPI()?.get()?.getBlockHeaderByBlockHash(header.blockHash)
                        logger.error("---> Block header response: ${blockHeader?.get()}")
                        onDebugMessage("Block header: ${blockHeader?.get()}")

                        val blockBody = sambaSDK?.historyAPI()?.get()?.getBlockBodyByBlockHash(header.blockHash)
                        logger.error("---> Block body response: ${blockBody?.get()}")
                        onDebugMessage("Block body: ${blockBody?.get()}")

                        val body = blockBody?.get()
                        body?.transactions?.forEach { tx ->
                            logger.info("---> Transaction: $tx")
                        }
                    }

                }).start()

            }) {
                Text("Get block by number from Samba")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                onDebugMessage("Getting content from localhost...")
                getContentFromLocalhost(onDebugMessage)
            }) {
                Text("GetContent from localhost")
            }
            Spacer(modifier = Modifier.weight(1f))

            // Debug area
            BasicTextField(
                value = debugText,
                onValueChange = { /* Read-only */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(
                        color = Color.Black.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                    )
                    .padding(8.dp)
                    .verticalScroll(scrollState),
                textStyle = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                readOnly = true,
                singleLine = false
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AndroidPortalTheme {
        MainScreen(
            debugText = "[12:34:56.789] Debug message 1\n[12:34:57.123] Debug message 2",
            onDebugMessage = {}
        )
    }
}

fun connectPinata(onDebugMessage: (String) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        val ipfsClient = IpfsLocalClient()
        val result = ipfsClient.swarmConnect("/dnsaddr/bitswap.pinata.cloud")
        onDebugMessage("Swarm connect result: $result")
        val result2 =
            ipfsClient.swarmConnect("/ip4/137.184.243.187/tcp/3000/ws/p2p/Qma8ddFEQWEU8ijWvdxXm3nxU7oHsRtCykAaVz8WUYhiKn")
        onDebugMessage("Swarm connect result: $result2")

        ipfsClient.swarmPeers()
    }
}

fun getContentFromLocalhost(onDebugMessage: (String) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        val client = OkHttpClient()
        val json = """
    {
        "jsonrpc": "2.0",
        "method": "portal_historyGetContent",
        "params": ["0x005b636cd2fa095a3e2ba03bc45c55ce75e29c4fc11e8f8de9f60d264ee5c48696"],
        "id": 1
    }
""".trimIndent()

        val requestBody = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            json
        )

        val request = Request.Builder()
            .url("http://127.0.0.1:8545") // Use 10.0.2.2 for localhost in Android emulator
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                logger.error("--> Request failed: ${e.message}", )
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!it.isSuccessful) {
                        logger.error("--> Unexpected code $it")
                    } else {
                        logger.error("--> Response: ${it.body?.string()}")
                    }
                }
            }
        })
    }
}


private fun checkAddressAgainstTrueblocks() {
    try {
        val addressToCheck = "0x308686553a1EAC2fE721Ac8B814De638975a276e".lowercase()
        val manifestCID =
            "QmUBS83qjRmXmSgEvZADVv2ch47137jkgNbqfVVxQep5Y1" // version trueblocks-core@v2.0.0-release

        CoroutineScope(Dispatchers.IO).launch {
//            val ipfsClient: IpfsClient = IpfsHttpClient("https://ipfs.unchainedindex.io/ipfs/")
//                val ipfsClient: IpfsClient = IpfsHttpClient("http://localhost:8080/ipfs/")
            val ipfsClient = IpfsLocalClient()
//                ipfsClient.fetchIndex("QmYwAPJzv5CZsnAzt8auVZRn5s9rUw1XhuMQ6Y42ZPi7x2", false)

            ipfsClient.swarmConnect("/dnsaddr/bitswap.pinata.cloud")
            ipfsClient.swarmConnect("/ip4/137.184.243.187/tcp/3000/ws/p2p/Qma8ddFEQWEU8ijWvdxXm3nxU7oHsRtCykAaVz8WUYhiKn")
            ipfsClient.swarmPeers()

            val manifestResponse = ipfsClient.fetchAndParseManifestUrl(manifestCID)
            Timber.d("Manifest response: $manifestResponse")

            manifestResponse?.chunks?.reversed()?.forEach {
//    manifestResponse?.chunks?.forEach {
//        println(it)
                val bloom = ipfsClient.fetchBloom(it.bloomHash, it.range)

                bloom?.let { bloom ->
                    if (bloom.isMemberBytes(Address(addressToCheck))) {
                        // fetch index
                        val appearances =
                            ipfsClient.fetchIndex(cid = it.indexHash, parse = false)
                                ?.findAppearances(addressToCheck)
                        appearances?.forEach { appearance ->
                            println("$addressToCheck \t${appearance.blockNumber} \t${appearance.txIndex}")
                        }
                    } else {
                        logger.info("Address not found in bloom range: ${bloom.range}")
                    }
                }
            }
        }
    } catch (e: Exception) {
        Timber.e("Exception: ${e.message}")
    }
}