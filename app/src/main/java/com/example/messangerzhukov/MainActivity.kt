package com.example.messangerzhukov

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import androidx.compose.ui.text.input.PasswordVisualTransformation
import android.widget.Toast
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import android.app.Activity
import androidx.compose.material.icons.filled.MoreVert
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONException
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import androidx.compose.runtime.remember
import androidx.compose.material3.MaterialTheme

import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.delay

import com.example.messangerzhukov.ui.theme.MessangerZhukovTheme

private const val PREFS_NAME = "UserPrefs"
private const val KEY_USER_ID = "UserId"
private const val KEY_LOGIN = "login"
private const val KEY_ACCESS_TOKEN = "access_token"
private const val KEY_REFRESH_TOKEN = "refresh_token"

data class RefreshTokenRequest(
    val token: String,
    val user_id: Int
)
data class RefreshTokenResponse(
    val access_token: String
)
data class Chat(
    val id: Int,
    val isDirect: Boolean,
    val Name: String,
    val Owner: Owner,
    val OwnerId: Int
)
data class Owner(
    val id: Int,
    val Login: String,
    val Password: String,
    val UserName: String
)
/*data class Message(
    val ID: Int,
    val ChatId: Int,
    val SenderId: Int,
    val UserName: String,
    val SendingTime: String,
    val Text: String
)*/
data class MessageInfo(
    @SerializedName("ChatId")val chatId: Int,
    @SerializedName("ID")val id: Int,
    @SerializedName("SenderId")val senderId: Int,
    @SerializedName("SendingTime")val sendingTime: String,
    @SerializedName("Text")val text: String,
    @SerializedName("UserName")val userName: String
)
//private var onMessageReceived: ((MessageInfo) -> Unit)? = null
data class ChatMember(
    val ID: Int,
    val Login: String,
    val UserName: String
)
data class Contact(
    val ID: Int,
    val Login: String,
    val UserName: String
)

class MainActivity : ComponentActivity() {
    private val client = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MessangerZhukovTheme {
                val navController = rememberNavController()
                MainScreen(navController)
            }
        }
        checkServerConnection()
    }
    private fun checkServerConnection() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = makeRequest("http://193.124.33.25:8080/api/v1/chat/all")
            withContext(Dispatchers.Main) {
                if (response != null) {
                    Log.d("ServerConnection", "SERVER DOSTUPEN: ${response.body?.string()}")
                } else {
                    Log.e("ServerConnection", "NE UDALOS PODKLUCHISUA K SERVERY")
                }
            }
        }
    }
    private fun makeRequest(url: String): Response? {
        return try {
            val request = Request.Builder()
                .url(url)
                .build()
            client.newCall(request).execute()
        } catch (e: Exception) {
            Log.e("ServerConnection", "Oshibka pri vipolnenii zaproca: ${e.message}", e)
            null
        }
    }
}

private fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavHostController, onBack: () -> Unit) {
    var login by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }
    var responseMessage by remember { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Регистрация") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color(0xFFE8F5E9))
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Регистрация", fontSize = 20.sp)

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = login,
                    onValueChange = { login = it },
                    label = { Text("Логин") }
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Пароль") },
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        registerUser (context, login.text, password.text)
                        navController.navigate("main") {
                            popUpTo("main") { inclusive = true }
                        }
                        },
                    modifier = Modifier
                        .padding(8.dp)
                        .width(200.dp)
                        .height(90.dp)
                ) {
                    Text("Зарегистрироваться", fontSize = 20.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(responseMessage, color = Color.Red)
            }
        }
    )
}

private fun registerUser (context: Context, login: String, password: String) {
    val client = OkHttpClient()
    val json = """{"login": "$login", "password": "$password"}"""
    val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

    val request = Request.Builder()
        .url("http://193.124.33.25:8080/api/v1/user/sign-up")
        .post(requestBody)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("RegisterUser  ", "Request failed: ${e.message}", e)
            (context as? Activity)?.runOnUiThread {
                showToast(context, "Не удалось зарегистрироваться")
            }
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                response.body?.string()?.let {
                    Log.i("RegisterUser  ", "Response: $it")
                    (context as? Activity)?.runOnUiThread {
                        showToast(context, "Регистрация удалась!")
                    }
                }
            } else {
                Log.e("RegisterUser  ", "Error: ${response.code}")
                (context as? Activity)?.runOnUiThread {
                    showToast(context, "Не удалось зарегистрироваться")
                }
            }
        }
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController, onBack: () -> Unit) {
    var login by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }
    var responseMessage by remember { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Вход") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color(0xFFE8F5E9))
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Вход", fontSize = 20.sp)

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = login,
                    onValueChange = { login = it },
                    label = { Text("Логин") }
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Пароль") },
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        loginUser (context, login.text, password.text, navController)
                        },
                    modifier = Modifier
                        .padding(8.dp)
                        .width(200.dp)
                        .height(90.dp)
                ) {
                    Text("Войти", fontSize = 20.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(responseMessage, color = Color.Red)
            }
        }
    )
}

private fun loginUser (context: Context, login: String, password: String, navController: NavController) {
    val client = OkHttpClient()
    val json = """{"login": "$login", "password": "$password"}"""
    val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

    val request = Request.Builder()
        .url("http://193.124.33.25:8080/api/v1/user/sign-in")
        .post(requestBody)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("LoginUser  ", "Request failed: ${e.message}", e)
            (context as? Activity)?.runOnUiThread {
                showToast(context, "Не удалось войти")
            }
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                response.body?.string()?.let {

                    Log.i("LoginrUser  ", "Response: $it")

                    val jsonResponse = JSONObject("$it")
                    val UserId = jsonResponse.getInt("UserId")
                    val accessToken = jsonResponse.getJSONObject("Token").getString("AccessToken")
                    val refreshToken = jsonResponse.getJSONObject("Token").getString("RefreshToken")

                    val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putInt(KEY_USER_ID, UserId)
                    editor.putString(KEY_LOGIN, login)
                    editor.putString(KEY_ACCESS_TOKEN, accessToken)
                    editor.putString(KEY_REFRESH_TOKEN, refreshToken)
                    editor.apply()

                    (context as? Activity)?.runOnUiThread {
                        navController.navigate("main") {
                            popUpTo("main") { inclusive = true }
                        }
                        showToast(context, "Вход удался!")
                    }
                }
            } else {
                Log.e("LoginUser  ", "Error: ${response.code}")
                (context as? Activity)?.runOnUiThread {
                    showToast(context, "Не удалось войти")
                }
            }
        }
    })
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            val context = LocalContext.current
            val chatsState = remember { mutableStateOf<List<Chat>?>(null) }

            val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val login = sharedPreferences.getString(KEY_LOGIN, null)
            val accessToken = sharedPreferences.getString(KEY_ACCESS_TOKEN, null)
            val refreshToken = sharedPreferences.getString(KEY_REFRESH_TOKEN, null)

            if (accessToken!=null){
                GetChats(context, accessToken) { chats ->
                    chatsState.value = chats
                    //Log.d("ChatState", "Chats State: $chatsState.value")
                }
            } else {
                Log.e("MainScreenChats  ", "Access token is null")
            }

            if (login != null || accessToken != null || refreshToken != null) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Чаты") },
                            actions = {
                                IconButton(onClick = { navController.navigate("Menus") }) {
                                    Icon(imageVector = Icons.Default.Menu, contentDescription = "Меню")
                                }
                                IconButton(onClick = { navController.navigate("Create") }) {
                                    Icon(imageVector = Icons.Default.Create, contentDescription = "Создать")
                                }
                                IconButton(onClick = { navController.navigate("personalChat") }) {
                                    Icon(imageVector = Icons.Default.Face, contentDescription = "Личные чаты")
                                }
                            }
                        )
                    },
                    content = { innerPadding ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                                .background(Color(0xFFE8F5E9))
                        ) {
                            chatsState.value?.let { chats ->
                                LazyColumn {
                                    items(chats) { chat ->

                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable(onClick = {
                                                    val chatId = chat.id
                                                    val chatName = chat.Name
                                                    val ownerId = chat.OwnerId
                                                    navController.navigate("InChat/${chatId}/${chatName}/${ownerId}")
                                                })
                                                .padding(24.dp)
                                        ) {
                                            Text(
                                                text = chat.Name ?: "???Неизвестный чат???",
                                                fontSize = 24.sp
                                            )
                                        }
                                    }
                                }
                            } ?: run {
                                CircularProgressIndicator(modifier = Modifier.padding(64.dp))
                            }
                        }
                    }
                )
            }
            else {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Вход/Регистрация") },
                        )
                    },
                    content = { innerPadding ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                                .background(Color(0xFFE8F5E9)),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(
                                onClick = { navController.navigate("login") },
                                modifier = Modifier
                                    .padding(8.dp)
                                    .width(200.dp)
                                    .height(90.dp)
                            ) {
                                Text("Войти", fontSize = 20.sp)
                            }
                            Spacer(modifier = Modifier.height(128.dp))
                            Button(
                                onClick = { navController.navigate("register") },
                                modifier = Modifier
                                    .padding(8.dp)
                                    .width(200.dp)
                                    .height(90.dp)
                            ) {
                                Text("Зарегистрироваться", fontSize = 20.sp)
                            }
                        }
                    }
                )
            }
        }
        composable("Menus") {
            MenusScreen(navController = navController, onBack = { navController.popBackStack() })
        }
        composable("Create") {
            CreateScreen(navController = navController, onBack = { navController.popBackStack() })
        }
        composable("personalChat") {
            PersonalChatScreen(navController = navController, onBack = { navController.popBackStack() })
        }
        composable("login") {
            LoginScreen(navController = navController, onBack = { navController.popBackStack() })
        }

        composable("register") {
            RegisterScreen(navController = navController, onBack = { navController.popBackStack() })
        }

        composable("ChangeNameScreen") {
            ChangeNameScreen(navController = navController, onBack = { navController.popBackStack() })
        }

        composable("InChat/{chatId}/{chatName}/{ownerId}",
            //arguments = listOf(navArgument("chatId") { type = NavType.StringType }, navArgument("chatName") { type = NavType.StringType })
            arguments = listOf(navArgument("chatId") { type = NavType.StringType }, navArgument("chatName") { type = NavType.StringType }, navArgument("ownerId") { type = NavType.StringType })
        ) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId")?.toIntOrNull()
            val chatName = backStackEntry.arguments?.getString("chatName")
            val ownerId = backStackEntry.arguments?.getString("ownerId")?.toIntOrNull()
            val context = LocalContext.current
            //ChatScreen(context, navController = navController, chatId = chatId, chatName = chatName, onBack = { navController.popBackStack() })
            ChatScreen(context, navController = navController, chatId = chatId, chatName = chatName, ownerId = ownerId, onBack = { navController.popBackStack() })
        }

        composable(
            "ChatMembers/{chatId}/{chatName}",
            arguments = listOf(navArgument("chatId") { type = NavType.StringType }, navArgument("chatName") { type = NavType.StringType })
        ) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId")?.toIntOrNull()
            val chatName = backStackEntry.arguments?.getString("chatName")
            val context = LocalContext.current
            ChatMembers(context, navController = navController, chatId = chatId, chatName = chatName, onBack = { navController.popBackStack() })
        }

        composable("CreateChat") {
            val context = LocalContext.current
            CreateChatScreen(context, navController = navController, onBack = { navController.popBackStack() })
        }
        composable(
            "DeleteChat/{chatId}",
            arguments = listOf(navArgument("chatId") { type = NavType.IntType })
        ) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getInt("chatId")
            val context = LocalContext.current
            val isInitialized = remember { mutableStateOf(false) }

            if (!isInitialized.value) {
                isInitialized.value = true
                DeleteChat(context, navController = navController, chatId = chatId, onBack = { navController.popBackStack() })
            }
        }

        composable(
            "AddUser/{chatId}",
            arguments = listOf(navArgument("chatId") { type = NavType.IntType })
        ) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getInt("chatId")
            val context = LocalContext.current
            AddUserScreen(context, navController = navController, chatId = chatId, onBack = { navController.popBackStack() })
        }

    }
}

private fun GetChats(context: Context, accessToken: String, onResult: (List<Chat>?) -> Unit) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val userId = sharedPreferences.getInt(KEY_USER_ID, 0)
    val refreshToken = sharedPreferences.getString(KEY_REFRESH_TOKEN, null)

    val client = OkHttpClient()
    val request = Request.Builder()
        .url("http://193.124.33.25:8080/api/v1/chat/all")
        .addHeader("Authorization", "Bearer $accessToken")
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("GetChats  ", "Request failed: ${e.message}", e)
            onResult(null)
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                response.body?.string()?.let { responseBody ->
                    //Log.d("ChatResponse", "Response Body: $responseBody")
                    val chats = Gson().fromJson(responseBody, Array<Chat>::class.java).toList()
                    onResult(chats)
                } ?: onResult(null)
            } else {
                if (response.code == 401 && refreshToken!=null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val newAccessToken = refreshAccessToken(refreshToken, userId)
                        if (newAccessToken != null) {
                            val editor = sharedPreferences.edit()
                            editor.putString(KEY_ACCESS_TOKEN, newAccessToken)
                            editor.apply()

                            GetChats(context, newAccessToken, onResult)
                        } else {
                            Log.e("GetChats  ", "Failed to refresh access token")
                            onResult(null)
                        }
                    }
                } else {
                    Log.e("GetChats  ", "Error: ${response.code}")
                    onResult(null)
                }
            }
        }
    })
}
private suspend fun refreshAccessToken(refreshToken: String, userId: Int): String? {
    val url = "http://193.124.33.25:8080/api/v1/user/refresh"
    val client = OkHttpClient()

    val requestBody = RefreshTokenRequest(token = refreshToken, user_id = userId)
    val json = Gson().toJson(requestBody)

    val request = Request.Builder()
        .url(url)
        .post(RequestBody.create("application/json".toMediaType(), json))
        .build()

    return try {
        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
            val responseBody = response.body?.string()
            val refreshTokenResponse = Gson().fromJson(responseBody, RefreshTokenResponse::class.java)
            Log.d("TokenRefresh", "Token obnovlen")
            refreshTokenResponse.access_token
        } else {
            Log.e("TokenRefresh", "Ошибка обновления токена: ${response.code}")
            null
        }
    } catch (e: Exception) {
        Log.e("TokenRefresh", "Ошибка при выполнении запроса: ${e.message}", e)
        null
    }
}

class ApiService () {
    private val client = OkHttpClient()

    fun getMessages(accessToken: String?, chatId: Int, pageId: Int, callback: (List<MessageInfo>?) -> Unit) {
        val url = "http://193.124.33.25:8080/api/v1/chat/messages?chat-id=$chatId&page-id=$pageId"
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $accessToken")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("ApiService", "Error fetching messages", e)
                callback(null)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!it.isSuccessful) {
                        Log.e("ApiService", "Unexpected code $response")
                        Log.e("ApiService", "Response body: ${it.body?.string()}")
                        callback(null)
                        return
                    }
                    if (accessToken == null) {
                        Log.e("ApiService", "Access token is null")
                        return
                    }

                    val responseBody = it.body?.string()
                    val messages = Gson().fromJson(responseBody, Array<MessageInfo>::class.java).toList()
                    callback(messages)
                }
            }
        })
    }
    fun getChatMembers(accessToken: String?, chatId: Int, callback: (List<ChatMember>?) -> Unit) {
        val url = "http://193.124.33.25:8080/api/v1/chat/members/$chatId"
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $accessToken")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("ApiService", "Error fetching chat members", e)
                callback(null)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!it.isSuccessful) {
                        Log.e("ApiService", "Unexpected code $response")
                        Log.e("ApiService", "Response body: ${it.body?.string()}")
                        callback(null)
                        return
                    }
                    if (accessToken == null) {
                        Log.e("ChatMembersScreen", "Access token is null")
                        return
                    }

                    val responseBody = it.body?.string()
                    Log.d("ApiService", "Response body: $responseBody")

                    val chatMembers = Gson().fromJson(responseBody, Array<ChatMember>::class.java).toList()
                    Log.d("ApiService", "Parsed chat members: $chatMembers")
                    callback(chatMembers)
                }
            }
        })
    }
    fun getContacts(accessToken: String?, callback: (List<Contact>?) -> Unit) {
        val url = "http://193.124.33.25:8080/api/v1/contact/all"
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $accessToken")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("ApiService", "Error fetching contacts", e)
                callback(null)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!it.isSuccessful) {
                        Log.e("ApiService", "Unexpected code $response")
                        Log.e("ApiService", "Response body: ${it.body?.string()}")
                        callback(null)
                        return
                    }
                    if (accessToken == null) {
                        Log.e("ApiService", "Access token is null")
                        return
                    }

                    val responseBody = it.body?.string()
                    val contacts = Gson().fromJson(responseBody, Array<Contact>::class.java).toList()
                    callback(contacts)
                }
            }
        })
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    context: Context,
    navController: NavHostController,
    chatId: Int?,
    chatName: String?,
    ownerId: Int?,
    onBack: () -> Unit
) {
    var messages by remember { mutableStateOf(emptyList<MessageInfo>()) }
    var isLoading by remember { mutableStateOf(true) }
    var pageNumber by remember { mutableStateOf(0) }
    var webSocket by remember { mutableStateOf<WebSocket?>(null) }
    var newMessage by remember { mutableStateOf("") }

    val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val accessToken = sharedPreferences.getString(KEY_ACCESS_TOKEN, null)
    val userId = sharedPreferences.getInt(KEY_USER_ID, 0)

    val apiService = ApiService()

    LaunchedEffect(chatId, pageNumber) {
        chatId?.let {
            isLoading = true
            apiService.getMessages(accessToken, it, pageNumber) { fetchedMessages ->
                messages = fetchedMessages ?: emptyList()
                isLoading = false
            }
        }
    }

    fun sendMessage(message: String) {
        val json = JSONObject().apply {
            put("text", message)
        }
        if (webSocket != null) {
            val isSent = webSocket?.send(json.toString())
            if (isSent == true) {
                Log.d("WebSocket", "Message sent: ${json.toString()}")
            } else {
                Log.e("WebSocket", "Failed to send message: ${json.toString()}")
            }
        } else {
            Log.e("WebSocket", "WebSocket is not connected.")
        }
    }

    fun connectWebSocket(chatId: Int?, pageNumber: Int?, accessToken: String?){
        fun reconnect() {
            CoroutineScope(Dispatchers.Main).launch {
                delay(3000)
                connectWebSocket(chatId, pageNumber, accessToken)
            }
        }
        chatId?.let {
            val request = Request.Builder()
                .url("ws://193.124.33.25:8080/api/v1/messenger/connect?chat-id=$it")
                .addHeader("Authorization", "Bearer $accessToken")
                .build()

            val client = OkHttpClient()

            webSocket = client.newWebSocket(request, object : WebSocketListener() {
                override fun onOpen(ws: WebSocket, response: Response) {
                    super.onOpen(ws, response)
                    webSocket = ws
                    Log.d("WebSocket", "Connection opened: $response")
                }

                override fun onMessage(ws: WebSocket, text: String) {
                    super.onMessage(ws, text)
                    Log.d("WebSocket", "Message received: $text")
                    try {
                        val jsonResponse = JSONObject(text)
                        if (jsonResponse.has("error")) {
                            Log.e("WebSocket", "Error from server: ${jsonResponse.getString("error")}")
                        } else {
                            val message = parseMessageInfo(text)
                            if (message != null) {
                                messages = messages + message
                            } else {
                                Log.e("WebSocket", "Received message could not be parsed into MessageInfo.")
                            }
                        }
                    } catch (e: JSONException) {
                        Log.e("WebSocket", "Failed to parse response: ${e.message}", e)
                    }
                }

                override fun onClosing(ws: WebSocket, code: Int, reason: String) {
                    super.onClosing(ws, code, reason)
                    ws.close(1000, null)
                    Log.d("WebSocket", "Connection closing: $code / $reason")
                }

                override fun onClosed(ws: WebSocket, code: Int, reason: String) {
                    super.onClosed(ws, code, reason)
                    Log.d("WebSocket", "Connection closed: $code / $reason")
                    webSocket = null
                    reconnect()
                }

                override fun onFailure(ws: WebSocket, t: Throwable, response: Response?) {
                    webSocket = null
                    reconnect()
                }
            })
        }
    }

    LaunchedEffect(chatId) {
        chatId?.let {
            isLoading = true
            apiService.getMessages(accessToken, it, 0) { fetchedMessages ->
                messages = fetchedMessages ?: emptyList()
                isLoading = false
            }
            connectWebSocket(chatId, pageNumber, accessToken)
        }
    }

    DisposableEffect(chatId) {
        onDispose {
            webSocket?.close(1000, "Closing connection")
        }
    }

    DisposableEffect(chatId) {


        onDispose {
            webSocket?.close(1000, "Closing connection")
        }
    }

    Scaffold(
        topBar = {
            var expanded by remember { mutableStateOf(false) }
            TopAppBar(
                title = { Text(chatName ?: "??? Неизвестный чат ???") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                },
                actions = {
                    var showDialog by remember { mutableStateOf(false) }
                    IconButton(onClick = { expanded = true }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Больше")
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            onClick = { navController.navigate("ChatMembers/${chatId}/${chatName}") },
                            text = { Text("Все участники") }
                        )
                        if (ownerId == userId) {
                            Divider()
                            DropdownMenuItem(
                                onClick = { showDialog = true },
                                text = { Text("Удалить чат") }
                            )
                            if (showDialog) {
                                ConfirmDeleteDialog(
                                    onConfirm = {
                                        showDialog = false
                                        navController.navigate("DeleteChat/${chatId}")
                                    },
                                    onDismiss = {
                                        showDialog = false
                                    }
                                )
                            }
                            DropdownMenuItem(
                                onClick = { navController.navigate("AddUser/${chatId}") },
                                text = { Text("Добавить пользователей") }
                            )
                        }
                    }
                }
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 56.dp)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    } else {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = { if (pageNumber > 0) pageNumber -= 1 },
                                enabled = pageNumber > 0
                            ) {
                                Text("<")
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Text("Страница ${pageNumber + 1}")
                            Spacer(modifier = Modifier.width(16.dp))
                            Button(
                                onClick = { pageNumber += 1 }
                            ) {
                                Text(">")
                            }
                        }
                        Box(modifier = Modifier.fillMaxSize()) {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(messages.reversed()) { message ->
                                    MessageItem(message)
                                }
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = newMessage,
                        onValueChange = { newMessage = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Введите сообщение") }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (newMessage.isNotBlank()) {
                                sendMessage(newMessage)
                                newMessage = ""
                            }
                        }
                    ) {
                        Text("Отправить")
                    }
                }
            }
        }
    )
}

/*fun parseMessage(json: String): Message {
    val gson = Gson()
    return gson.fromJson(json, Message::class.java)
}*/
fun parseMessageInfo(jsonString: String): MessageInfo? {
    val jsonObject = JSONObject(jsonString)

    Log.d("WebSocket", "Received JSON: $jsonString")

    val chatId = if (jsonObject.has("chatId")) jsonObject.getInt("chatId") else -1
    val id = if (jsonObject.has("id")) jsonObject.getInt("id") else -1
    val senderId = if (jsonObject.has("senderId")) jsonObject.getInt("senderId") else -1
    val sendingTime = jsonObject.optString("sendingTime", "неизвестное время")
    val text = jsonObject.optString("text", "пустое сообщение")
    val userName = jsonObject.optString("userName", "Неизвестный пользователь")

    if (id == -1) {
        Log.e("WebSocket", "ID is missing in the received message.")
        return null
    }

    return MessageInfo(
        chatId = chatId,
        id = id,
        senderId = senderId,
        sendingTime = sendingTime,
        text = text,
        userName = userName
    )
}


@Composable
fun ConfirmDeleteDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Подтверждение удаления") },
        text = { Text("Вы уверены, что хотите удалить этот чат?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Да")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Нет")
            }
        }
    )
}
@Composable
fun ConfirmAddUserDialog(UserName: String?, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Подтверждение добавления $UserName") },
        text = { Text("Вы уверены, что хотите добавить этого пользователя?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Да")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Нет")
            }
        }
    )
}
@Composable
fun ConfirmAddContactDialog(UserName: String?, UserLogin: String?, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Подтверждение добавления $UserName #$UserLogin") },
        text = { Text("Вы уверены, что хотите добавить этого пользователя в свои контакты?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Да")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Нет")
            }
        }
    )
}
@Composable
fun ConfirmDeleteContactDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Подтверждение удаления") },
        text = { Text("Вы уверены, что хотите удалить этот контакт?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Да")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Нет")
            }
        }
    )
}

@Composable
fun MessageItem(message: MessageInfo) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .clickable { }
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = if (message.userName.isNotEmpty()) message.userName else "Неизвестный пользователь",
                color = Color.Gray,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (message.text.isNotEmpty()) message.text else "Нет текста",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (message.sendingTime.isNotEmpty()) message.sendingTime else "null",
                color = Color.Gray,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatMembers(context: Context, navController: NavHostController, chatId: Int?, chatName: String?, onBack: () -> Unit) {
    val apiService = ApiService()
    var chatMembers by remember { mutableStateOf<List<ChatMember>?>(null) }
    var loading by remember { mutableStateOf(true) }
    val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val accessToken = sharedPreferences.getString(KEY_ACCESS_TOKEN, null)

    LaunchedEffect(chatId) {
        if (chatId != null) {
            apiService.getChatMembers(accessToken, chatId) { members ->
                chatMembers = members
                loading = false
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Участники чата $chatName") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color(0xFFE8F5E9))
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (loading) {
                    CircularProgressIndicator()
                } else {
                    var showDialog by remember { mutableStateOf(false) }
                    var userName by remember { mutableStateOf<String?>(null) }
                    var userLogin by remember { mutableStateOf<String?>(null) }
                    chatMembers?.let { members ->
                        LazyColumn {
                            items(members) { member ->
                                Text(text = "${member.UserName} #${member.Login}",  modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(onClick={
                                        userName = member.UserName
                                        userLogin = member.Login
                                        showDialog = true
                                    })
                                    .padding(24.dp))

                            }
                        }
                        if (showDialog) {
                            ConfirmAddContactDialog(
                                UserName = userName ?: "Неизвестный",
                                UserLogin = userLogin ?: "Логин",
                                onConfirm = {
                                    showDialog = false
                                    AddUserToContact(context, navController = navController, UserLogin = userLogin)
                                },
                                onDismiss = {
                                    showDialog = false
                                }
                            )
                        }
                    } ?: run {
                        Text(text = "Нет участников для отображения.")
                    }
                }
            }
        }
    )
}
private fun AddUserToContact(context: Context, navController: NavHostController, UserLogin: String?) {
    if (UserLogin == null) {
        Log.e("AddUser ToContact", "User  Name or User Login is null")
        showToast(context, "Не удалось добавить контакт: отсутствует имя или логин пользователя")
        return
    }

    val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val accessToken = sharedPreferences.getString(KEY_ACCESS_TOKEN, null)

    val client = OkHttpClient()

    val jsonBody = JSONObject()
    jsonBody.put("contact_login", UserLogin)

    val requestBody = jsonBody.toString().toRequestBody("application/json".toMediaTypeOrNull())

    val request = Request.Builder()
        .url("http://193.124.33.25:8080/api/v1/contact")
        .addHeader("Authorization", "Bearer $accessToken")
        .post(requestBody)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("AddUser ToContact", "Request failed: ${e.message}", e)
            (context as? Activity)?.runOnUiThread {
                showToast(context, "Не удалось добавить контакт")
            }
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                Log.i("AddUser ToContact", "Contact added successfully")
                (context as? Activity)?.runOnUiThread {
                    showToast(context, "Контакт успешно добавлен!")
                }
            } else {
                Log.e("AddUser ToContact", "Error: ${response.code} - ${response.message}")
                (context as? Activity)?.runOnUiThread {
                    showToast(context, "Не удалось добавить контакт: ${response.message}")
                }
            }
        }
    })
}


@Composable
private fun DeleteChat(context: Context, navController: NavHostController, chatId: Int?, onBack: () -> Unit) {
    if (chatId == null) {
        Log.e("DeleteChat", "Chat ID is null")
        showToast(context, "Не удалось удалить чат: отсутствует идентификатор чата")
        return
    }

    val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val accessToken = sharedPreferences.getString(KEY_ACCESS_TOKEN, null)

    val client = OkHttpClient()
    val request = Request.Builder()
        .url("http://193.124.33.25:8080/api/v1/chat/$chatId")
        .addHeader("Authorization", "Bearer $accessToken")
        .delete()
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("DeleteChat", "Request failed: ${e.message}", e)
            (context as? Activity)?.runOnUiThread {
                showToast(context, "Не удалось удалить чат")
            }
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                Log.i("DeleteChat", "Chat deleted successfully")

                (context as? Activity)?.runOnUiThread {
                    navController.navigate("main") {
                        popUpTo("main") { inclusive = true }
                    }
                    showToast(context, "Чат успешно удален!")
                }
            } else {
                Log.e("DeleteChat", "Error: ${response.code}")
                (context as? Activity)?.runOnUiThread {
                    showToast(context, "Не удалось удалить чат: ${response.message}")
                }
            }
        }
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddUserScreen(context: Context, navController: NavHostController, chatId: Int?, onBack: () -> Unit) {
    val apiService = ApiService()
    var chatMembers by remember { mutableStateOf<List<ChatMember>?>(null) }
    var loading by remember { mutableStateOf(true) }
    val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val accessToken = sharedPreferences.getString(KEY_ACCESS_TOKEN, null)

    LaunchedEffect(1) {
        apiService.getChatMembers(accessToken, 1) { members ->
            chatMembers = members
            loading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Все пользователи") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color(0xFFE8F5E9))
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (loading) {
                    CircularProgressIndicator()
                } else {
                    var showDialog by remember { mutableStateOf(false) }
                    var MemberId by remember { mutableStateOf(0) }
                    var MemberName by remember { mutableStateOf<String?>(null) }
                    chatMembers?.let { members ->
                        LazyColumn {
                            items(members) { member ->
                                Text(text = "${member.UserName} #${member.Login}",  modifier = Modifier
                                    .clickable (onClick = {
                                        MemberId = member.ID
                                        MemberName = member.UserName
                                        showDialog = true
                                    })
                                    .fillMaxWidth()
                                    .padding(24.dp))
                            }
                        }
                        if (showDialog) {
                            ConfirmAddUserDialog(
                                UserName = MemberName ?: "Неизвестный пользователь",
                                onConfirm = {
                                    showDialog = false
                                    //Log.d("CLICKERR", "DOBAVIT ${MemberName} #id: ${MemberId} V CHAT ${chatId}")
                                    AddUser(context, navController = navController, userId = MemberId, chatId = chatId)
                                },
                                onDismiss = {
                                    showDialog = false
                                }
                            )
                        }
                    } ?: run {
                        Text(text = "Нет участников для отображения.")
                    }
                }
            }
        }
    )
}
private fun AddUser(context: Context, navController: NavHostController, userId: Int?, chatId: Int?) {
    if (chatId == null || userId == null) {
        Log.e("AddUser ", "Chat ID or User ID is null")
        showToast(context, "Не удалось добавить пользователя: отсутствует идентификатор чата или пользователя")
        return
    }

    val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val accessToken = sharedPreferences.getString(KEY_ACCESS_TOKEN, null)

    val client = OkHttpClient()

    val jsonBody = JSONObject()
    jsonBody.put("chat_id", chatId)
    jsonBody.put("members_ids", JSONArray().put(userId))

    val requestBody = jsonBody.toString().toRequestBody("application/json".toMediaTypeOrNull())

    val request = Request.Builder()
        .url("http://193.124.33.25:8080/api/v1/chat/add/members")
        .addHeader("Authorization", "Bearer $accessToken")
        .post(requestBody)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("AddUser ", "Request failed: ${e.message}", e)
            (context as? Activity)?.runOnUiThread {
                showToast(context, "Не удалось добавить пользователя")
            }
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                Log.i("AddUser ", "User  added successfully")

                (context as? Activity)?.runOnUiThread {
                    showToast(context, "Пользователь успешно добавлен в чат!")
                }
            } else {
                Log.e("AddUser ", "Error: ${response.code} - ${response.message}")
                (context as? Activity)?.runOnUiThread {
                    showToast(context, "Не удалось добавить пользователя: ${response.message}")
                }
            }
        }
    })
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenusScreen(navController: NavHostController, onBack: () -> Unit) {
    val context = LocalContext.current
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val login = sharedPreferences.getString(KEY_LOGIN, null)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Логин пользователя: " + login) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                }
            )
        },
        ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFBBECBF))
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(64.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.clear()
                        editor.apply()
                        navController.navigate("main") {
                            popUpTo("main") { inclusive = true }
                        }
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .width(200.dp)
                        .height(90.dp),
                ) { Text("Выйти из аккаунта", fontSize = 20.sp) }
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = {
                        navController.navigate("ChangeNameScreen")
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .width(200.dp)
                        .height(90.dp)
                ) { Text("Установить имя пользователя", fontSize = 20.sp) }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeNameScreen(navController: NavHostController, onBack: () -> Unit) {
    var username by remember { mutableStateOf(TextFieldValue()) }
    var responseMessage by remember { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Установка имени пользователю") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color(0xFFE8F5E9))
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                TextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Новое имя пользователя") }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        ChangeUserName (context, navController, username.text)
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .width(200.dp)
                        .height(90.dp)
                ) {
                    Text("Ввести", fontSize = 20.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(responseMessage, color = Color.Red)
            }
        }
    )
}
private fun ChangeUserName (context: Context, navController: NavHostController, UserName: String) {
    val client = OkHttpClient()
    val json = """{"username": "$UserName"}"""
    val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

    val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val accessToken = sharedPreferences.getString(KEY_ACCESS_TOKEN, null)

    val request = Request.Builder()
        .url("http://193.124.33.25:8080/api/v1/user/set/username")
        .addHeader("Authorization", "Bearer $accessToken")
        .post(requestBody)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("ChangeUserName  ", "Request failed: ${e.message}", e)
            (context as? Activity)?.runOnUiThread {
                showToast(context, "Не удалось изменить имя аккаунта")
            }
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                response.body?.string()?.let {

                    Log.i("ChangeUserName  ", "Response: $it")

                    (context as? Activity)?.runOnUiThread {
                        navController.navigate("Menus"){
                            popUpTo(navController.graph.startDestinationId) {}
                        }
                        showToast(context, "Удалось изменить имя аккаунта!")
                    }
                }
            } else {
                Log.e("ChangeUserName  ", "Error: ${response.code}")
                (context as? Activity)?.runOnUiThread {
                    showToast(context, "Не удалось изменить имя аккаунта")
                }
            }
        }
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen(navController: NavHostController, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Создание чата / Поиск пользователя") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        },

        ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFBBECBF)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    navController.navigate("CreateChat")
                },
                modifier = Modifier
                    .padding(8.dp)
                    .width(200.dp)
                    .height(90.dp)
            ) { Text("Создать чат", fontSize = 20.sp) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateChatScreen(context: Context, navController: NavHostController, onBack: () -> Unit) {
    var chatnamee by remember { mutableStateOf(TextFieldValue()) }
    val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val userId = sharedPreferences.getInt(KEY_USER_ID, 0)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Создание чата") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color(0xFFE8F5E9))
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = chatnamee,
                    onValueChange = { chatnamee = it },
                    label = { Text("Название чата") }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        CreateChat(context, chatnamee.text, navController)
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .width(200.dp)
                        .height(90.dp)
                ) {
                    Text("Создать", fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    )
}
private fun CreateChat (context: Context, ChatName: String, navController: NavController) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val accessToken = sharedPreferences.getString(KEY_ACCESS_TOKEN, null)
    val userId = sharedPreferences.getInt(KEY_USER_ID, 0)

    val json = """
        {
            "is_direct": false,
            "members_ids": [${userId}],
            "name": "$ChatName"
        }
    """.trimIndent()

    val client = OkHttpClient()
    val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

    val request = Request.Builder()
        .url("http://193.124.33.25:8080/api/v1/chat")
        .addHeader("Authorization", "Bearer $accessToken")
        .post(requestBody)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("CreateChat", "Request failed: ${e.message}", e)
            (context as? Activity)?.runOnUiThread {
                showToast(context, "Не удалось создать чат")
            }
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                response.body?.string()?.let { responseBody ->
                    Log.i("CreateChat", "Response: $responseBody")

                    val jsonResponse = JSONObject(responseBody)
                    val chatId = jsonResponse.getInt("chat_id")

                    (context as? Activity)?.runOnUiThread {
                        navController.navigate("InChat/${chatId}/${ChatName}/${userId}"){
                            popUpTo(navController.graph.startDestinationId) {}
                        }
                        showToast(context, "Чат успешно создан!")
                    }
                }
            } else {
                Log.e("CreateChat", "Error: ${response.code}")
                (context as? Activity)?.runOnUiThread {
                    showToast(context, "Не удалось создать чат: ${response.message}")
                }
            }
        }
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalChatScreen(navController: NavHostController, onBack: () -> Unit) {
    val contactsState = remember { mutableStateOf<List<Contact>?>(null) }
    val isLoading = remember { mutableStateOf(true) }
    val apiService = ApiService()

    val context = LocalContext.current
    val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val accessToken = sharedPreferences.getString(KEY_ACCESS_TOKEN, null)

    LaunchedEffect(Unit) {
        apiService.getContacts(accessToken) { contacts ->
            contactsState.value = contacts
            isLoading.value = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Контакты") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFBBECBF))
        ) {
            if (isLoading.value) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                contactsState.value?.let { contacts ->
                    if (contacts.isEmpty()) {
                        Text("Нет контактов", modifier = Modifier.align(Alignment.CenterHorizontally))
                    } else {
                        LazyColumn {
                            items(contacts) { contact ->
                                ContactItem(contact = contact) { contactId ->
                                    contactsState.value = contactsState.value?.filter { it.ID != contactId }
                                }
                            }
                        }
                    }
                } ?: run {
                    Text("Ошибка загрузки контактов", modifier = Modifier.align(Alignment.CenterHorizontally))
                }
            }
        }
    }
}
@Composable
fun ContactItem(contact: Contact, onDelete: (Int) -> Unit) {
    val backgroundColor = Color(0xFF84c88a)
    val context = LocalContext.current
    var showDialog1 by remember { mutableStateOf(false) }
    var contactId by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .background(backgroundColor)
            .clickable(onClick = {
                showDialog1 = true
                contactId = contact.ID
            })
    ) {
        if (showDialog1) {
            ConfirmDeleteContactDialog(
                onConfirm = {
                    showDialog1 = false
                    DeleteContact(context, contactId)
                    onDelete(contactId)
                },
                onDismiss = {
                    showDialog1 = false
                }
            )
        }
        Text(
            text = "${contact.UserName} #${contact.Login}",
            modifier = Modifier.padding(16.dp)
        )
    }
}
private fun DeleteContact(context: Context, ContactId: Int?) {
    if (ContactId == null) {
        Log.e("DeleteContact", "Contact ID is null")
        showToast(context, "Не удалось удалить контакт: отсутствует идентификатор контакта")
        return
    }

    val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val accessToken = sharedPreferences.getString(KEY_ACCESS_TOKEN, null)

    val client = OkHttpClient()
    val request = Request.Builder()
        .url("http://193.124.33.25:8080/api/v1/contact/$ContactId")
        .addHeader("Authorization", "Bearer $accessToken")
        .delete()
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("DeleteContact", "Request failed: ${e.message}", e)
            (context as? Activity)?.runOnUiThread {
                showToast(context, "Не удалось удалить контакт")
            }
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                Log.i("DeleteContact", "Contact deleted successfully")

                (context as? Activity)?.runOnUiThread {
                    showToast(context, "Контакт успешно удален!")
                }
            } else {
                Log.e("DeleteContact", "Error: ${response.code}")
                (context as? Activity)?.runOnUiThread {
                    showToast(context, "Не удалось удалить контакт: ${response.message}")
                }
            }
        }
    })
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MessangerZhukovTheme {
        val navController = rememberNavController()
        MainScreen(navController)
    }
}