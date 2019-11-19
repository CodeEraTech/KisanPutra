package com.shambhu.kisanputra.data.rest_api

class Resource<T> private constructor(val status: ApiStatus, val data: T?, val message: String?) {

    val isSuccess: Boolean
        get() = status === ApiStatus.SUCCESS && data != null

    val isLoading: Boolean
        get() = status === ApiStatus.LOADING

    val isLoaded: Boolean
        get() = status !== ApiStatus.LOADING

    companion object {

        fun <T> success(msg: String, data: T?): Resource<T> {
            return Resource(ApiStatus.SUCCESS, data, msg)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(ApiStatus.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(ApiStatus.LOADING, data, null)
        }
    }
}