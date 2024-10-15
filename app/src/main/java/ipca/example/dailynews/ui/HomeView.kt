package ipca.example.dailynews.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ipca.example.dailynews.models.Article
import ipca.example.dailynews.ui.theme.DailyNewsTheme
import java.util.Date

import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException


@Composable
fun HomeView( modifier: Modifier = Modifier) {

    var articles by remember { mutableStateOf(listOf<Article>()) }

    LazyColumn {
        itemsIndexed(
            items = articles
        ) { _, article ->
            Column {
                RowArticle(article = article)
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color.LightGray
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://newsapi.org/v2/top-headlines?country=us&apiKey=8929b43c2f9e403d856784315106d42d")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    /*
                    for ((name, value) in response.headers) {
                        println("$name: $value")
                    }

                    println(response.body!!.string())
                    */

                    val result = response.body!!.string()

                    val jsonResult = JSONObject(result)

                    var status = jsonResult.getString("status")

                    if (status == "ok") {
                        val articlesJson = jsonResult.getJSONArray("articles")

                        var articlesResult = arrayListOf<Article>()

                        for (index in 0 until articlesJson.length()) {
                            val articleJson = articlesJson.getJSONObject(index)

                            val article = Article(
                               title = articleJson.getString("title"),
                                description = articleJson.getString("description"),
                                urlToImage = articleJson.getString("urlToImage"),
                                url = articleJson.getString("url"),
                                publishedAt = Date()
                            )
                            articlesResult.add(article)
                        }
                        articles = articlesResult
                    }

                }
            }
        })

    }
}

@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    DailyNewsTheme {
        HomeView()
    }
}