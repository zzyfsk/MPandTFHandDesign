package com.google.mediapipe.examples.handlandmarker.util

import android.util.Log
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarkerResult

object ResultUtil {
    /**
     * 逻辑：
     * 首先调用setAFrameToList方法设置单帧Array
     * 然后调用addFrameToList 方法将一帧的内容加入List中
     * 最后外界通过getList方法经历上述过程取出list
     * @author zzy
     * */


    // 一些常量
    private const val TAG = "MediaPipeResult"
    private const val NUMFRAME = 30
    private const val NUMKEYPOINTS = 21
    private const val NUMHANDS = 2
    private const val NUMAXIS = 3
    private var result: HandLandmarkerResult? = null
    // 所用到的列表
    private val frameFloatArray: FloatArray = FloatArray(NUMKEYPOINTS * NUMAXIS * NUMHANDS)
    private val twoHandsResultList: MutableList<Float> = mutableListOf()

    fun getList(): MutableList<Float> {
        addFrameToList()
        return twoHandsResultList
    }



    fun setResults(
        handLandmarkerResults: HandLandmarkerResult,
        imageHeight: Int,
        imageWidth: Int,
        runningMode: RunningMode = RunningMode.IMAGE
    ) {
        result = handLandmarkerResults

    }

    fun setAFrameToList(result: HandLandmarkerResult) {
        var i = 0
        for (landmark in result.landmarks()) {
            for (normalizedLandmark in landmark) {
                twoHandsResultList[i] = normalizedLandmark.x()
                i++
                twoHandsResultList[i] = normalizedLandmark.y()
                i++
                twoHandsResultList[i] = normalizedLandmark.z()
                i++
            }
        }
    }

    private fun addFrameToList() {
        for (i in 1..NUMHANDS* NUMKEYPOINTS* NUMAXIS){
            twoHandsResultList.removeFirst()
        }
        frameFloatArray.forEach {
            twoHandsResultList.add(it)
        }
    }

    init {
        for ( i in  1..NUMFRAME* NUMHANDS* NUMKEYPOINTS* NUMAXIS){
            twoHandsResultList.add(0f)
        }
    }
}