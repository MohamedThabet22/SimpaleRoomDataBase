package com.example.roomdatabase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomdatabase.mvvm.Book
import com.example.roomdatabase.mvvm.BookViewModel
import com.example.roomdatabase.ui.theme.RoomDataBaseTheme

private lateinit var viewModel: BookViewModel
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            RoomDataBaseTheme {

                viewModel = BookViewModel(application)
                BookListScreen(viewModel)
            }
        }
    }
}





@Composable
fun BookListScreen(bookViewModel: BookViewModel) {

    val books by bookViewModel.books.observeAsState(initial = emptyList())
    var selectedBook by remember { mutableStateOf<Book?>(null) }
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        BasicTextField(
            value = title,
            onValueChange = { title = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clip(RoundedCornerShape(15.dp))
            ,

            textStyle =  TextStyle(
                fontSize =  15.sp,
                color = Color.White,
                fontWeight = MaterialTheme.typography.bodyLarge.fontWeight
            )
            ,
            decorationBox = { innerTextField ->
                Row(
                    Modifier
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(16.dp)

                ) {
                    if (title.isEmpty()) {
                        Text("Title", style = MaterialTheme.typography.bodyMedium.copy(color = Color.White))
                    }
                    innerTextField()
                }
            }
        )
        BasicTextField(
            value = author,
            onValueChange = { author = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clip(RoundedCornerShape(15.dp)),
            textStyle =  TextStyle(
                fontSize =  15.sp,
                color = Color.White,
                fontWeight = MaterialTheme.typography.bodyLarge.fontWeight
            )
            ,
            decorationBox = { innerTextField ->
                Row(
                    Modifier
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(16.dp)
                ) {
                    if (author.isEmpty()) {
                        Text("Author", style = MaterialTheme.typography.bodyMedium.copy(color = Color.White))
                    }
                    innerTextField()
                }
            }
        )

        Row(modifier = Modifier.padding(
            8.dp
        )) {
            Button(onClick = {
                if (selectedBook == null) {
                    bookViewModel.addBook(Book(title = title, author = author))
                } else {
                    bookViewModel.updateBook(selectedBook!!.copy(title = title, author = author))
                    selectedBook = null
                }
                title = ""
                author = ""
            }
            ) {
                Text(if (selectedBook == null) "Add Book" else "Update Book",
                    color = Color.White)
            }

            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { bookViewModel.deleteAllBooks() }) {
                Text("Delete All",
                    color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))



    Spacer(modifier = Modifier.height(10.dp))



    val filteredBooks = if (searchQuery.isEmpty()) {
        books
    } else {
        books.filter { it.title.contains(searchQuery, ignoreCase = true) }
    }
    filteredBooks.forEach { book ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = book.title,
                    style = TextStyle(
                        fontSize = 25.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = MaterialTheme.typography.bodyMedium.fontWeight
                    )
                )
                Text(
                    text = "by ${book.author}",
                    style = TextStyle(
                        fontSize = 15.sp,
                        color = Color.Black,
                        fontWeight = MaterialTheme.typography.bodySmall.fontWeight
                    )
                )
            }
            IconButton(onClick = {
                title = book.title
                author = book.author
                selectedBook = book
            }) {
                Icon(Icons.Default.Edit, contentDescription = "Edit Book")
            }
            IconButton(onClick = { bookViewModel.deleteBook(book) }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete Book")
            }
        }

        }

    }
    Box (
        modifier = Modifier.fillMaxSize()

    ){

        CustomSearchView(
            search = searchQuery,
            modifier = Modifier.align(androidx.compose.ui.Alignment.BottomCenter),
            onValueChange = { searchQuery = it }
        )
    }



}
@Preview(showBackground = true)
@Composable
fun CustomSearchViewPreview() {
    CustomSearchView(
        search = "Sample Search",
        onValueChange = {}
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchView(
    search: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {

    Box(
        modifier = modifier
            .padding(30.dp)
            .clip(RoundedCornerShape(20.dp))
            .fillMaxWidth()
            .background(Color(0XFF101921))

    ) {
        TextField(value = search,
            modifier = Modifier.fillMaxWidth()
            ,
            onValueChange = onValueChange,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.primary,
                focusedPlaceholderColor = Color.White,
                focusedTrailingIconColor = Color.White,
                focusedLeadingIconColor = Color.White,
                focusedTextColor = Color.White,
                focusedIndicatorColor = Color.Transparent, cursorColor = Color.White
            ),
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "") },
            placeholder = { Text(text = "Search") }
        )
    }

}

@Preview
@Composable
fun BookListScreenPreview() {
    RoomDataBaseTheme {

    }
}