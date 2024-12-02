# ContactApi

All URIs are relative to */api/v1*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**contactAllGet**](ContactApi.md#contactAllGet) | **GET** /contact/all | Получить список контактов пользователя |
| [**contactIdDelete**](ContactApi.md#contactIdDelete) | **DELETE** /contact/{id} | Удалить контакт |
| [**contactIdGet**](ContactApi.md#contactIdGet) | **GET** /contact/{id} | Получить контакт по ID |
| [**contactPost**](ContactApi.md#contactPost) | **POST** /contact | Создать контакт |


<a id="contactAllGet"></a>
# **contactAllGet**
> kotlin.collections.List&lt;CoreUserInfo&gt; contactAllGet()

Получить список контактов пользователя

Получить список контактов пользователя

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ContactApi()
try {
    val result : kotlin.collections.List<CoreUserInfo> = apiInstance.contactAllGet()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ContactApi#contactAllGet")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ContactApi#contactAllGet")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**kotlin.collections.List&lt;CoreUserInfo&gt;**](CoreUserInfo.md)

### Authorization


Configure BearerAuth:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="contactIdDelete"></a>
# **contactIdDelete**
> contactIdDelete(id)

Удалить контакт

Удалить контакт

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ContactApi()
val id : kotlin.Int = 56 // kotlin.Int | ID контакта
try {
    apiInstance.contactIdDelete(id)
} catch (e: ClientException) {
    println("4xx response calling ContactApi#contactIdDelete")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ContactApi#contactIdDelete")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **kotlin.Int**| ID контакта | |

### Return type

null (empty response body)

### Authorization


Configure BearerAuth:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="contactIdGet"></a>
# **contactIdGet**
> CoreUserInfo contactIdGet(id)

Получить контакт по ID

Получить контакт по ID

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ContactApi()
val id : kotlin.Int = 56 // kotlin.Int | ID контакта
try {
    val result : CoreUserInfo = apiInstance.contactIdGet(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ContactApi#contactIdGet")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ContactApi#contactIdGet")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **kotlin.Int**| ID контакта | |

### Return type

[**CoreUserInfo**](CoreUserInfo.md)

### Authorization


Configure BearerAuth:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="contactPost"></a>
# **contactPost**
> contactPost(body)

Создать контакт

Создать контакт

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ContactApi()
val body : HandlerAddContact =  // HandlerAddContact | Логин контакта
try {
    apiInstance.contactPost(body)
} catch (e: ClientException) {
    println("4xx response calling ContactApi#contactPost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ContactApi#contactPost")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **body** | [**HandlerAddContact**](HandlerAddContact.md)| Логин контакта | |

### Return type

null (empty response body)

### Authorization


Configure BearerAuth:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

