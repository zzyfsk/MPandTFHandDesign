package com.google.mediapipe.examples.handlandmarker

import android.util.Log
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.Arrays

import android.content.Context
import com.google.mediapipe.examples.handlandmarker.util.AssetsUtil
import com.google.mediapipe.examples.handlandmarker.util.ResultUtil


class HandDetectTensorflow(val context: Context) {
    private val TAG = "PersonalTest"

    /**
     * 使用Tensorflow lite 模型进行检测
     * @author zzy
     * */
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
        Log.e(TAG, "initC:" + output)
        val inputTensorBuffer = TensorBuffer.createFixedSize(intArrayOf(1, 3), DataType.FLOAT32)
        inputTensorBuffer.loadBuffer(output)
        val inputArray = inputTensorBuffer.floatArray
        val inputArrayString = Arrays.toString(inputArray)

        val confidences: FloatArray = inputArray
        // 选择排序找最大的
        var maxPos = 0
        var maxConfidence = 0f
        for (i in confidences.indices) {
            if (confidences[i] > maxConfidence) {
                maxConfidence = confidences[i]
                maxPos = i
            }
        }
        val classes = arrayOf("thank", "iloveyou", "hello")
        Log.e(TAG, "initC: $inputArrayString")
        Log.e(TAG, "initC: " + classes[maxPos])
    }


    // 尚未完成
    private fun transformListToArray(mutableList: MutableList<Float>): FloatArray {
        Log.e(TAG, "transformListToArray: ${mutableList.size}")
        val floatArray = FloatArray(1 * 30 * 126)
        var i = 0
        mutableList.forEach {
            floatArray[i] = it
            i++
        }
        return floatArray
    }
}