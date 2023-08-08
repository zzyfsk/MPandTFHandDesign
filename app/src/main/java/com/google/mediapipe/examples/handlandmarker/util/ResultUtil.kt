package com.google.mediapipe.examples.handlandmarker.util

import android.util.Log
import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarkerResult


/**
 * 逻辑：
 * 首先调用setAFrameToList方法设置单帧Array
 * 然后调用addFrameToList 方法将一帧的内容加入List中
 * 最后外界通过getList方法经历上述过程取出list
 * @author zzy
 * */
object ResultUtil {
    // 一些常量
    // 从上至下分别为  帧数 关键点数 手数量 侦测的坐标轴数
    private const val TAG = "MediaPipeResult"
    private const val NUMFRAME = 30
    private const val NUMKEYPOINTS = 21
    private const val NUMHANDS = 2
    private const val NUMAXIS = 3
    private var result: HandLandmarkerResult? = null

    // 所用到的列表
    private val frameFloatArray: FloatArray = FloatArray(NUMKEYPOINTS * NUMAXIS * NUMHANDS)
    private val leftFloatArray: FloatArray = FloatArray(NUMKEYPOINTS * NUMAXIS)
    private val rightFloatArray: FloatArray = FloatArray(NUMKEYPOINTS * NUMAXIS)
    private val twoHandsResultList: MutableList<Float> = mutableListOf()
    private val resultDetect = mutableListOf<String>()


    fun getList(): MutableList<Float> {
        addFrameToList()
        return twoHandsResultList
    }


    fun setResults(
        handLandmarkerResults: HandLandmarkerResult,
        imageHeight: Int,
        imageWidth: Int,
    ) {
        result = handLandmarkerResults
        print(imageWidth + imageHeight)
    }

    fun setAFrameToList(result: HandLandmarkerResult) {
        var i = 0
        leftFloatArray.forEachIndexed { index, value ->
            rightFloatArray[index] = value * 0
        }
        rightFloatArray.forEachIndexed { index, value ->
            rightFloatArray[index] = value * 0
        }
        // 收集到手的数量
        when (result.handednesses().size) {
            0 -> {
                frameFloatArray.forEachIndexed { index, value ->
                    frameFloatArray[index] = value * 0
                }
            }

            1 -> {
                if (result.handednesses()[0][0].displayName() == "Left") {
                    i = 0
                    for (normalizedLandmark in result.landmarks().first()) {
                        leftFloatArray[i] = normalizedLandmark.x()
                        i++
                        leftFloatArray[i] = normalizedLandmark.y()
                        i++
                        leftFloatArray[i] = normalizedLandmark.z()
                        i++
                    }
                } else {
                    i = 0
                    for (normalizedLandmark in result.landmarks().first()) {
                        rightFloatArray[i] = normalizedLandmark.x()
                        i++
                        rightFloatArray[i] = normalizedLandmark.y()
                        i++
                        rightFloatArray[i] = normalizedLandmark.z()
                        i++
                    }
                }
            }

            2 -> {
                if (result.handednesses()[0][0].displayName() == "Left") {
                    i = 0
                    for (normalizedLandmark in result.landmarks().first()) {
                        leftFloatArray[i] = normalizedLandmark.x()
                        i++
                        leftFloatArray[i] = normalizedLandmark.y()
                        i++
                        leftFloatArray[i] = normalizedLandmark.z()
                        i++
                    }
                    i = 0
                    for (normalizedLandmark in result.landmarks()[1]) {
                        rightFloatArray[i] = normalizedLandmark.x()
                        i++
                        rightFloatArray[i] = normalizedLandmark.y()
                        i++
                        rightFloatArray[i] = normalizedLandmark.z()
                        i++
                    }
                } else {
                    i = 0
                    for (normalizedLandmark in result.landmarks().first()) {
                        rightFloatArray[i] = normalizedLandmark.x()
                        i++
                        rightFloatArray[i] = normalizedLandmark.y()
                        i++
                        rightFloatArray[i] = normalizedLandmark.z()
                        i++
                    }
                    i = 0
                    for (normalizedLandmark in result.landmarks()[1]) {
                        leftFloatArray[i] = normalizedLandmark.x()
                        i++
                        leftFloatArray[i] = normalizedLandmark.y()
                        i++
                        leftFloatArray[i] = normalizedLandmark.z()
                        i++
                    }
                    Log.e(TAG, "setAFrameToList: 2TestAccess")
                }
            }
        }

        for (j in 0 until NUMAXIS * NUMKEYPOINTS) {
            frameFloatArray[j] = leftFloatArray[j]
            frameFloatArray[j + NUMAXIS * NUMKEYPOINTS] = rightFloatArray[j]
        }
    }

    private fun addFrameToList() {
        for (i in 1..NUMHANDS * NUMKEYPOINTS * NUMAXIS) {
            twoHandsResultList.removeFirst()
        }
        frameFloatArray.forEach {
            twoHandsResultList.add(it)
        }

        //输出到一行上
        Log.e(TAG, twoHandsResultList.joinToString())
    }

    /**
     * 4、递推平均滤波法（又称滑动平均滤波法）
    A、方法：
    把连续取N个采样值看成一个队列
    队列的长度固定为N
    每次采样到一个新数据放入队尾，并扔掉原来队首的一次数据.(先进先出原则)
    把队列中的N个数据进行算术平均运算，就可获得新的滤波结果
    N值的选取：流量，N=12；压力：N=4；液面，N=4~12；温度，N=1~4
     * */
    fun input(result: String) {
        if (resultDetect.size < 30) {
            resultDetect.add(result)
        } else {
            resultDetect.removeFirst()
            resultDetect.add(result)
        }
    }

    fun test() {
        var a = 0
        var b = 0
        resultDetect.forEach {
            if (it == "a") a++
            if (it == "b") b++
        }
        if (a>b) {
            a++
        } else{
            b++
        }
    }


    init {
        for (i in 1..NUMFRAME * NUMHANDS * NUMKEYPOINTS * NUMAXIS) {
            twoHandsResultList.add(0f)
        }
    }
}