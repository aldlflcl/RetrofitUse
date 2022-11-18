package com.example.retrofituse.api

import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

internal class ImageApiTest {

    @Test
    fun test() = runTest {
        val images = ImageApi.retrofitService.getImages(10)
        images.forEach {
            println(it)
        }
        Assert.assertEquals(10, images.size)
    }
}