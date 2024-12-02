# MessengerApi

All URIs are relative to */api/v1*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**messengerConnectGet**](MessengerApi.md#messengerConnectGet) | **GET** /messenger/connect | Подключиться к мессенджеру |


<a id="messengerConnectGet"></a>
# **messengerConnectGet**
> messengerConnectGet(chatId)

Подключиться к мессенджеру

Установить websocket соединение с чатом. Чтобы отправить сообщение в чат нужно сформировать json в формате { text: string }, приходить сообщения буду в формате { id: uint, text: string, senderId: uint, userName: string, chatId: uint, sendingTime: string }

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = MessengerApi()
val chatId : kotlin.Int = 56 // kotlin.Int | ID чата подключения
try {
    apiInstance.messengerConnectGet(chatId)
} catch (e: ClientException) {
    println("4xx response calling MessengerApi#messengerConnectGet")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling MessengerApi#messengerConnectGet")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **chatId** | **kotlin.Int**| ID чата подключения | |

### Return type

null (empty response body)

### Authorization


Configure BearerAuth:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

