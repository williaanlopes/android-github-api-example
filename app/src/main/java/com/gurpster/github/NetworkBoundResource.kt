package com.gurpster.github

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.gurpster.github.data.remote.Resource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.coroutines.coroutineContext


/**
 * Created by Williaan Souza (dextter) on 10/01/2021
 * Contact williaanlopes@gmail.com
 */

abstract class NetworkBoundResource<ResultType, RequestType> {

    private val result = MutableLiveData<Resource<ResultType>>()
    private val supervisorJob = SupervisorJob()

    private val dataFlow = MutableSharedFlow<Unit>(replay = 0)

    suspend fun build(): NetworkBoundResource<ResultType, RequestType> {
        withContext(Dispatchers.Main) {
            result.value = Resource.loading(null)
        }
        CoroutineScope(coroutineContext).launch(supervisorJob) {
//            val dbResult = loadFromDb()
//            if (shouldFetch(dbResult)) {
            try {
                println("call fetchFromNetwork()")
                fetchFromNetwork(null)
            } catch (e: Exception) {
                Log.e("NetworkBoundResource", "An error happened: $e")
                setValue(Resource.error(e.toString(), loadFromDb()))
            }
//            } else {
//                Log.d(NetworkBoundResource::class.java.name, "Return data from local database")
//                setValue(Resource.success(dbResult))
//            }
        }
        return this
    }

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    suspend fun asData() = result.value

    private suspend fun fetchFromNetwork(dbResult: ResultType?) {
        coroutineScope {
//            Log.d(NetworkBoundResource::class.java.name, "Fetch data from network")
            setValue(Resource.loading(dbResult)) // Dispatch latest value quickly (UX purpose)
            val apiResponse = async { createCallAsync() }.await()
            println("receive apiResponse")
//        val apiResponse = createCallAsync().await()
//            Log.e(NetworkBoundResource::class.java.name, "Data fetched from network")
            saveCallResults(processResponse(apiResponse))
            setValue(Resource.success(processResponse(apiResponse)))
//            setValue(Resource.success(loadFromDb()))
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.postValue(newValue)
        }
    }

    @WorkerThread
    protected abstract fun processResponse(response: RequestType): ResultType

    @WorkerThread
    protected abstract suspend fun saveCallResults(items: ResultType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract suspend fun loadFromDb(): ResultType?

    @MainThread
    protected abstract suspend fun createCallAsync(): RequestType
}