package com.example.splashscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.splashscreen.ui.theme.SplashScreenTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SplashScreenTheme {
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
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SplashScreenTheme {
        Greeting("Android")
    }
}

// В активности (или фрагменте) в методе onCreate

val sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
val isFirstRun = sharedPreferences.getBoolean("isFirstRun", true)

if (isFirstRun) {
    // Выполняем запрос на сервер Firebase, Keitaro или собственный сервер
    // Сохраняем информацию о первом запуске
    sharedPreferences.edit().putBoolean("isFirstRun", false).apply()
}

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class WebViewActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        webView = findViewById(R.id.webView)

        // Проверка условий перед открытием Webview
        if (shouldOpenWebView()) {
            configureWebView()
            loadWebViewUrl()
        } else {
            // Если условия не выполняются, выполняем действия для отображения экрана заглушки
            // например, переходим на другой экран или показываем фрагмент с заглушкой
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun configureWebView() {
        // Настройка WebView
        val settings: WebSettings = webView.settings
        settings.javaScriptEnabled = true // Включение поддержки JavaScript

        // Другие настройки WebView, если необходимо
    }

    private fun loadWebViewUrl() {
        // Загрузка URL в WebView
        val url = "https://example.com" // Заменяем на наш URL
        webView.loadUrl(url)
    }

    private fun shouldOpenWebView(): Boolean {
        val networkName = getNetworkName()
        val userCountry = getUserCountry()
        val hasSimCard = hasSimCard()
        val isDeviceFromGoogle = isDeviceFromGoogle()
        // Другие условия проверки, например, количество приложений

        return (!networkName.contains("google") &&
                userCountry in listOf("country1", "country2", "country3") &&
                hasSimCard &&
                !isDeviceFromGoogle)
    }

    private fun getNetworkName(): String {
        // Получение названия сети, например, через ConnectivityManager
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            return capabilities?.transportNames?.joinToString(", ") ?: ""
        } else {
            return ""
        }
    }

    private fun getUserCountry(): String {
        // Получение страны пользователя, например, из данных, полученных с сервера
        return "user_country" // Заменяем на фактическое значение
    }

    private fun hasSimCard(): Boolean {
        // Проверка наличия сим-карты
        val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return telephonyManager.simState != TelephonyManager.SIM_STATE_ABSENT
    }

    private fun isDeviceFromGoogle(): Boolean {
        // Проверка на принадлежность устройства к Google
        // Реализация этой проверки зависит от наших требований
        return false
    }
}
