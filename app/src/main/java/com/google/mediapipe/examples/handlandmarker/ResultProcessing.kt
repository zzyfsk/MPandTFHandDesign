package com.google.mediapipe.examples.handlandmarker


// 这个是用来对结果进行滤波的
class ResultProcessing {
    private val mapNameAndNum = mutableMapOf<String,Int>()

    fun createListResult(array: Array<String>){
        array.forEach {
            mapNameAndNum[it] = 60 / array.size
        }
    }

    fun countResult(string: String){
        mapNameAndNum[string]?.plus(mapNameAndNum.size)
        mapNameAndNum.forEach { (s, _) ->
            mapNameAndNum[s]?.minus(1)
        }
    }

    fun getResult(): String? {
        return mapNameAndNum.maxByOrNull { it.value }?.key
    }
}