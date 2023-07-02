package com.bso112.data.remote.datasource

import com.bso112.data.BuildConfig
import com.bso112.data.local.AppPreference
import com.bso112.data.remote.KtorClient
import com.bso112.data.remote.MessageApiModel
import com.bso112.data.remote.request.ChatRequest
import com.bso112.data.remote.request.TranslationRequest
import com.bso112.data.remote.response.ChatApiModel
import com.bso112.data.remote.response.TranslationResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType


class ChatRemoteDataSource(
    private val client: HttpClient = KtorClient.httpClient,
    private val appPreference: AppPreference
) {
    suspend fun translateMessage(
        messages: List<String>,
        from: String,
        to: String
    ): TranslationResponse {
        val projectId = BuildConfig.GOOGLE_CLOUD_PROJECT_ID
        val apiKey = BuildConfig.GOOGLE_TRANSLATION_API_KEY
        val url = "https://translate.googleapis.com/$projectId:translateText"
        val body = TranslationRequest(
            sourceLanguageCode = from,
            targetLanguageCode = to,
            contents = messages,
            mimeType = "text/plain"
        )

        return client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(body)
            headers {
                append(HttpHeaders.Authorization, "Bearer $apiKey")
            }
        }.body()
    }

    suspend fun sendChat(messages: List<MessageApiModel>): ChatApiModel {
        val url = "https://api.openai.com/v1/chat/completions"
        val apiKey = BuildConfig.CHAT_GPT_API_KEY
        val body = ChatRequest(
            model = appPreference.languageModel.getValue(),
            messages = messages,
            temperature = appPreference.temperature.getValue()
        )

        return client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(body)
            headers {
                append(HttpHeaders.Authorization, "Bearer $apiKey")
            }
        }.body()
    }
}