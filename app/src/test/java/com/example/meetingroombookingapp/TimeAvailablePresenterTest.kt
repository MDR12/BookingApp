package com.example.meetingroombookingapp

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class TimeAvailablePresenterTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun checkIsMod(){
        assertEquals(true,check(5,5))
    }

    private fun check(a :Int , b:Int):Boolean{
        return a%b==0
    }
}
