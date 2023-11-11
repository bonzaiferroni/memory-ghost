package com.bollwerks.memoryghost.utils.ezbot

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class EzBot(
    apiKey: String
){
    private val client = HttpClient(CIO) {
        defaultRequest {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer $apiKey")
            url("https://api.openai.com/v1/")
        }
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun makeRequest(
        prompt: String,
    ): String {
        val requestBody = CompletionRequestBody(
            model = "gpt-3.5-turbo",
            messages = listOf(Message(role = "user", content = prompt))
        )

        val response: HttpResponse = client.post("chat/completions") {
            setBody(requestBody)
        }

        // Parse the response
        val parsedResponse = response.body<OpenAIResponse>()

        // Extract and return the content of the first choice's message
        return parsedResponse.choices.first().message.content
    }
}

@Serializable
data class CompletionRequestBody(
    val model: String,
    val messages: List<Message>
)

@Serializable
data class Message(
    val role: String,
    val content: String
)

@Serializable
data class OpenAIResponse(
    val choices: List<Choice>
)

@Serializable
data class Choice(
    val message: Message,
    val index: Int
)