package com.example.randomstringgeneratorapp.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.randomstringgeneratorapp.R
import com.example.randomstringgeneratorapp.presentation.viewmodel.RandomStringViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: RandomStringViewModel = hiltViewModel()) {
    val strings by viewModel.randomStrings.collectAsState()
    val error by viewModel.error.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var length by remember { mutableStateOf("10") }

    val colorScheme = MaterialTheme.colorScheme

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = TextStyle(fontSize = 24.sp),
                        color = colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorScheme.primary
                )
            )
        },
        containerColor = colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = length,
                    onValueChange = { length = it.filter { it.isDigit() } },
                    label = { Text(stringResource(id = R.string.length_label)) },
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(8.dp))
                Button(
                    onClick = {
                        viewModel.getRandomString(length.toIntOrNull() ?: 10)
                    }
                ) {
                    Text(stringResource(id = R.string.generate_button))
                }
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = viewModel::deleteAll,
                colors = ButtonDefaults.buttonColors(containerColor = colorScheme.error)
            ) {
                Text(stringResource(id = R.string.delete_all_button))
            }

            if (error != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(stringResource(id = R.string.error_prefix) + error, color = Color.Red)
            }

            Spacer(Modifier.height(12.dp))

            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = colorScheme.primary)
                }
            } else {
                LazyColumn {
                    items(strings) { string ->
                        Card(
                            Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = colorScheme.surface
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                        ) {
                            Column(Modifier.padding(16.dp)) {
                                Text(
                                    stringResource(id = R.string.value_label, string.value),
                                    style = TextStyle(fontSize = 16.sp)
                                )
                                Text(
                                    stringResource(
                                        id = R.string.length_value_label,
                                        string.length
                                    ), style = TextStyle(fontSize = 16.sp)
                                )
                                Text(
                                    stringResource(id = R.string.created_label, string.created),
                                    style = TextStyle(fontSize = 16.sp)
                                )
                                Spacer(Modifier.height(6.dp))
                                Button(
                                    onClick = { viewModel.deleteItem(string) },
                                    colors = ButtonDefaults.buttonColors(containerColor = colorScheme.error)
                                ) {
                                    Text(stringResource(id = R.string.delete_button))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


