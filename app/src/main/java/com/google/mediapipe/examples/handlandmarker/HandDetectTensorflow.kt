package com.google.mediapipe.examples.handlandmarker

import android.util.Log
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.Arrays

import android.content.Context
import com.google.mediapipe.examples.handlandmarker.controller.MainActivityController
import com.google.mediapipe.examples.handlandmarker.util.AssetsUtil
import com.google.mediapipe.examples.handlandmarker.util.ResultUtil


/**
 * 使用Tensorflow lite 模型进行检测
 * @author zzy
 * */


class HandDetectTensorflow(val context: Context) {

    private val TAG = "PersonalTest"

    fun detect() {
        // 读取asset文件里的tflite模型
        val inputStream = AssetsUtil.getAssetsFile(context, "model.tflite")
        inputStream.bufferedReader()
        val modelData = inputStream.readBytes()
        // 将字节数据转换为 ByteBuffer 对象
        val modelBuffer = ByteBuffer.allocateDirect(modelData.size)
        modelBuffer.order(ByteOrder.nativeOrder())
        modelBuffer.put(modelData)
        modelBuffer.rewind()

        // 读取Tensorflow lite的模型
        val tfliteModel = Interpreter(modelBuffer)

        // 用于输入的TensorBuffer
        val inputModelBuffer = TensorBuffer.createFixedSize(
            tfliteModel.getInputTensor(0).shape(),
            tfliteModel.getInputTensor(0).dataType()
        )


        // 用于输出的TensorBuffer以及其shape
        val outputShape = intArrayOf(1, 3)
        val outputBuffer =
            TensorBuffer.createFixedSize(outputShape, tfliteModel.getInputTensor(0).dataType())
        // 将其转化为ByteBuffer
        val output = outputBuffer.buffer
        val mediaPipeResult = ResultUtil.getList()
        val tempFloatArray = transformListToArray(mediaPipeResult)
        inputModelBuffer.loadArray(tempFloatArray)
        val byteBuffer = inputModelBuffer.buffer
        tfliteModel.run(byteBuffer, output)
        Log.e(TAG, "initC:$output")
        val inputTensorBuffer = TensorBuffer.createFixedSize(intArrayOf(1, 3), DataType.FLOAT32)
        inputTensorBuffer.loadBuffer(output)
        val inputArray = inputTensorBuffer.floatArray
        val inputArrayString = Arrays.toString(inputArray)

        val detectProbability: FloatArray = inputArray
        // 选择排序找最大的
        var maxPos = 0
        var maxProbability = 0f
        for (i in detectProbability.indices) {
            if (detectProbability[i] > maxProbability) {
                maxProbability = detectProbability[i]
                maxPos = i
            }
        }
        val classes = arrayOf("thank", "iloveyou", "hello")
        Log.d(TAG, "TensorResult: $inputArrayString")
        Log.d(TAG, "TensorResult: " + classes[maxPos])

        MainActivityController.getMainActivityController().setText(classes[maxPos])
    }


    // 尚未完成
    private fun transformListToArray(mutableList: MutableList<Float>): FloatArray {
        Log.d(TAG, "transformListToArray: ${mutableList.size}")
        val floatArray = FloatArray(1 * 30 * 126)
        var i = 0
        mutableList.forEach {
            floatArray[i] = it
            i++
        }
        return floatArray
    }
}