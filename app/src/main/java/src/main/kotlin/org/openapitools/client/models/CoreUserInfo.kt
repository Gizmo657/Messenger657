/**
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 *
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package org.openapitools.client.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param id 
 * @param login 
 * @param userName 
 */


data class CoreUserInfo (

    @Json(name = "id")
    val id: kotlin.Int? = null,

    @Json(name = "login")
    val login: kotlin.String? = null,

    @Json(name = "userName")
    val userName: kotlin.String? = null

) {


}

