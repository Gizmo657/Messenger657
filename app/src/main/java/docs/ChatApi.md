# ChatApi

All URIs are relative to */api/v1*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**chatAddMembersPost**](ChatApi.md#chatAddMembersPost) | **POST** /chat/add/members | Добавить участника |
| [**chatAllGet**](ChatApi.md#chatAllGet) | **GET** /chat/all | Получить список чатов пользователя |
| [**chatIdDelete**](ChatApi.md#chatIdDelete) | **DELETE** /chat/{id} | Удалить чат |
| [**chatIdGet**](ChatApi.md#chatIdGet) | **GET** /chat/{id} | Получить чат по ID |
| [**chatMembersIdGet**](ChatApi.md#chatMembersIdGet) | **GET** /chat/members/{id} | Получить список участников чата |
| [**chatMessagesGet**](ChatApi.md#chatMessagesGet) | **GET** /chat/messages | Получить историю сообщений |
| [**chatPost**](ChatApi.md#chatPost) | **POST** /chat | Создать чат |


<a id="chatAddMembersPost"></a>
# **chatAddMembersPost**
> chatAddMembersPost(body)

Добавить участника

Добавить участника

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ChatApi()
val body : HandlerAddMember =  // HandlerAddMember | Список users_id
try {
    apiInstance.chatAddMembersPost(body)
} catch (e: ClientException) {
    println("4xx response calling ChatApi#chatAddMembersPost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ChatApi#chatAddMembersPost")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **body** | [**HandlerAddMember**](HandlerAddMember.md)| Список users_id | |

### Return type

null (empty response body)

### Authorization


Configure BearerAuth:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="chatAllGet"></a>
# **chatAllGet**
> kotlin.collections.List&lt;CoreChat&gt; chatAllGet()

Получить список чатов пользователя

Получить список чатов пользователя

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ChatApi()
try {
    val result : kotlin.collections.List<CoreChat> = apiInstance.chatAllGet()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ChatApi#chatAllGet")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ChatApi#chatAllGet")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**kotlin.collections.List&lt;CoreChat&gt;**](CoreChat.md)

### Authorization


Configure BearerAuth:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="chatIdDelete"></a>
# **chatIdDelete**
> chatIdDelete(id)

Удалить чат

Удалить чат

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ChatApi()
val id : kotlin.Int = 56 // kotlin.Int | ID чата
try {
    apiInstance.chatIdDelete(id)
} catch (e: ClientException) {
    println("4xx response calling ChatApi#chatIdDelete")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ChatApi#chatIdDelete")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **kotlin.Int**| ID чата | |

### Return type

null (empty response body)

### Authorization


Configure BearerAuth:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="chatIdGet"></a>
# **chatIdGet**
> CoreChat chatIdGet(id)

Получить чат по ID

Получить чат по ID

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ChatApi()
val id : kotlin.Int = 56 // kotlin.Int | ID чата
try {
    val result : CoreChat = apiInstance.chatIdGet(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ChatApi#chatIdGet")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ChatApi#chatIdGet")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **kotlin.Int**| ID чата | |

### Return type

[**CoreChat**](CoreChat.md)

### Authorization


Configure BearerAuth:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="chatMembersIdGet"></a>
# **chatMembersIdGet**
> kotlin.collections.List&lt;CoreUserInfo&gt; chatMembersIdGet(id)

Получить список участников чата

Получить список участников чата

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ChatApi()
val id : kotlin.Int = 56 // kotlin.Int | ID чата
try {
    val result : kotlin.collections.List<CoreUserInfo> = apiInstance.chatMembersIdGet(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ChatApi#chatMembersIdGet")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ChatApi#chatMembersIdGet")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **kotlin.Int**| ID чата | |

### Return type

[**kotlin.collections.List&lt;CoreUserInfo&gt;**](CoreUserInfo.md)

### Authorization


Configure BearerAuth:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="chatMessagesGet"></a>
# **chatMessagesGet**
> kotlin.collections.List&lt;CoreMessageInfo&gt; chatMessagesGet(chatId, pageId)

Получить историю сообщений

История сообщений получается постранично по 100 сообщений

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ChatApi()
val chatId : kotlin.Int = 56 // kotlin.Int | ID чата
val pageId : kotlin.Int = 56 // kotlin.Int | номер страницы
try {
    val result : kotlin.collections.List<CoreMessageInfo> = apiInstance.chatMessagesGet(chatId, pageId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ChatApi#chatMessagesGet")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ChatApi#chatMessagesGet")
    e.printStackTrace()
}
```

### Parameters
| **chatId** | **kotlin.Int**| ID чата | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **pageId** | **kotlin.Int**| номер страницы | |

### Return type

[**kotlin.collections.List&lt;CoreMessageInfo&gt;**](CoreMessageInfo.md)

### Authorization


Configure BearerAuth:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="chatPost"></a>
# **chatPost**
> HandlerChatIdResponse chatPost(body)

Создать чат

Создать чат

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ChatApi()
val body : HandlerAddChat =  // HandlerAddChat | Данные для создания чата
try {
    val result : HandlerChatIdResponse = apiInstance.chatPost(body)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ChatApi#chatPost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ChatApi#chatPost")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **body** | [**HandlerAddChat**](HandlerAddChat.md)| Данные для создания чата | |

### Return type

[**HandlerChatIdResponse**](HandlerChatIdResponse.md)

### Authorization


Configure BearerAuth:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

