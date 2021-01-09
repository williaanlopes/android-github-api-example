package com.gurpster.github.data.remote

import com.gurpster.github.data.remote.Status.*

/**
 * Created by Williaan Souza (dextter) on 09/01/2021
 * Contact williaanlopes@gmail.com
 */

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(LOADING, data, null)
        }
    }
}