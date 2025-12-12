package com.appweek14

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.appweek14.data.RetrofitClient
import com.appweek14.data.User
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HelloRetrofitApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelloRetrofitApp() {
    val scope = rememberCoroutineScope()
    var users by remember { mutableStateOf<List<User>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hello Retrofit") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Button(
                onClick = {
                    scope.launch {
                        isLoading = true
                        errorMessage = ""
                        try {
                            val result = RetrofitClient.api.getUsers() //ApiService.kt의 RetrofitClient(object)
                            users = result  //10명의 데이터들이 들어와있음
                        } catch (e: Exception) {
                            errorMessage = "에러: ${e.message}"  //못가져오면 에러 메세지
                        } finally {
                            isLoading = false //실패든 성공이든 로딩을 시도를 하고 끝났음
                        }
                    }
                }
            ) {
                Text("Fetch Users")
            }

            Spacer(modifier = Modifier.height(16.dp))  //빈 공간 채우는 것 여백

            if (isLoading) {  //true / false가 들어올 수 있음
                CircularProgressIndicator() //어디 페이지 접속할 때 로딩할 때 동그랗게 막 돌아가는 거 로딩하는 아이콘 같은거
            } else if (errorMessage.isNotEmpty()) { //에러 메세지가 있을때
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            } else {
                LazyColumn { //리사이클러뷰를 대체 보여지는 것만  / 메모리 로딩하는데 시간 줄어듦
                    items(users) { user ->  //for문 역할 users의 아이템개수만큼 반복
                        UserItem(user)
                    }
                }
            }
        }
    }
}


//item_student.xml의 역할을 함
@Composable
fun UserItem(user: User) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(text = "${user.id}. ${user.name}")
        Text(text = user.email, style = MaterialTheme.typography.bodySmall)
    }
}