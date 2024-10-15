package ipca.example.dailynews.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ipca.example.dailynews.R
import ipca.example.dailynews.models.Article
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage


@Composable
fun RowArticle(article: Article) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        /*
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color.Gray)
        )
        */

        article.urlToImage?.let {
            AsyncImage(
                model = it,
                contentDescription = "image article",
                modifier = Modifier
                    .height(60.dp)
                    .width(60.dp)
            )
        }?:run{
            Image(
                modifier = Modifier
                    .height(60.dp)
                    .width(60.dp),
                painter =  painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "image article" )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            //Text(text = index.toString())
            //Text(text = article.title!!)
            //ou melhor
            Text(text = article.title ?: "")
            Text(text = article.description ?: "")
            Text(text = article.publishedAt.toString())
        }
    }
}