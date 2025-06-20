package com.example.randomstringgeneratorapp.data.datasource

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Bundle
import com.example.randomstringgeneratorapp.data.models.RandomString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class RandomStringContentProvider @Inject constructor(
    private val context: Context
) : RandomStringDataSource {
    override suspend fun getRandomString(length: Int): Result<RandomString> = withContext(
        Dispatchers.IO
    ) {
        try {
            val uri = Uri.parse("content://com.iav.contestdataprovider/text")
            val bundle = Bundle().apply {
                putInt(ContentResolver.QUERY_ARG_LIMIT, length)
            }
            val cursor = context.contentResolver.query(uri, null, bundle, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val data = it.getString(it.getColumnIndexOrThrow("data"))
                    val json = JSONObject(data).getJSONObject("randomText")
                    val rawDate = json.getString("created")
                    val formattedDate = formatDate(rawDate)
                    val randomString = RandomString(
                        json.getString("value"),
                        json.getInt("length"),
                        formattedDate
                    )
                    return@withContext Result.success(randomString)
                }
            }
            Result.failure(Exception("Empty or invalid response"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

private fun formatDate(isoString: String): String {
    return try {
        val zonedDateTime = ZonedDateTime.parse(isoString)
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")
        zonedDateTime.format(formatter)
    } catch (e: Exception) {
        isoString
    }
}