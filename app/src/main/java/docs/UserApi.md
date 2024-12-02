# UserApi

All URIs are relative to */api/v1*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**userRefreshPost**](UserApi.md#userRefreshPost) | **POST** /user/refresh | Обновить токены |
| [**userSetUsernamePost**](UserApi.md#userSetUsernamePost) | **POST** /user/set/username | Установить имя пользователя |
| [**userSignInPost**](UserApi.md#userSignInPost) | **POST** /user/sign-in | Войти |
| [**userSignUpPost**](UserApi.md#userSignUpPost) | **POST** /user/sign-up | Зарегистрироваться |


<a id="userRefreshPost"></a>
# **userRefreshPost**
> HandlerRefreshRes userRefreshPost(body)

Обновить токены

Обновить токены

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = UserApi()
val body : HandlerRefresh =  // HandlerRefresh | Данные для регистрации
try {
    val result : HandlerRefreshRes = apiInstance.userRefreshPost(body)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling UserApi#userRefreshPost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UserApi#userRefreshPost")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **body** | [**HandlerRefresh**](HandlerRefresh.md)| Данные для регистрации | |

### Return type

[**HandlerRefreshRes**](HandlerRefreshRes.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="userSetUsernamePost"></a>
# **userSetUsernamePost**
> userSetUsernamePost(body)

Установить имя пользователя

Установить имя пользователя

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = UserApi()
val body : HandlerUserName =  // HandlerUserName | Данные для установки имя пользователя
try {
    apiInstance.userSetUsernamePost(body)
} catch (e: ClientException) {
    println("4xx response calling UserApi#userSetUsernamePost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UserApi#userSetUsernamePost")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **body** | [**HandlerUserName**](HandlerUserName.md)| Данные для установки имя пользователя | |

### Return type

null (empty response body)

### Authorization


Configure BearerAuth:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="userSignInPost"></a>
# **userSignInPost**
> userSignInPost(body)

Войти

Войти

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = UserApi()
val body : HandlerSign =  // HandlerSign | Данные для регистрации
try {
    apiInstance.userSignInPost(body)
} catch (e: ClientException) {
    println("4xx response calling UserApi#userSignInPost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UserApi#userSignInPost")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **body** | [**HandlerSign**](HandlerSign.md)| Данные для регистрации | |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="userSignUpPost"></a>
# **userSignUpPost**
> userSignUpPost(body)

Зарегистрироваться

Зарегистрироваться

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = UserApi()
val body : HandlerSign =  // HandlerSign | Данные для регистрации
try {
    apiInstance.userSignUpPost(body)
} catch (e: ClientException) {
    println("4xx response calling UserApi#userSignUpPost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UserApi#userSignUpPost")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **body** | [**HandlerSign**](HandlerSign.md)| Данные для регистрации | |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

