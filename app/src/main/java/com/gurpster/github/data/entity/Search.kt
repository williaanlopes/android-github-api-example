package com.gurpster.github.data.entity


/**
 * Created by Williaan Souza (dextter) on 09/01/2021
 * Contact williaanlopes@gmail.com
 */

data class Search(
    val incomplete_results: Boolean,
    val items: List<Repo>,
    val total_count: Int
)