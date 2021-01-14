package com.gurpster.github.data

import com.gurpster.github.data.remote.WebService
import com.gurpster.github.di.AppExecutorsModule
import javax.inject.Inject


/**
 * Created by Williaan Souza (dextter) on 08/01/2021
 * Contact williaanlopes@gmail.com
 */

class UserRepository @Inject constructor(
    private val appExecutors: AppExecutorsModule,
    private val webService: WebService
) {

}