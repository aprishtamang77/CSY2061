package com.example.notessqlite

import org.junit.Test
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
// Define the ExampleUnitTest class
class ExampleUnitTest {
    //method with @Test to indicate it's a test method
    @Test
    fun multiplication_isCorrect() {
        //the result of the multiplication 2 * 3 is equal to 6
        assertEquals(6, 2 * 3)
    }
}
