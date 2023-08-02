# MediaPipe and Tensorflow-lite about Hands sign detect

### Overview

This is a camera app that can detects hand landmarks either from continuous camera frames seen by your device's front camera, an image, or a video from the device's gallery using a custom **task** file.

The task file is downloaded by a Gradle script when you build and run the app. You don't need to do any additional steps to download task files into the project explicitly unless you wish to use your own landmark detection task. If you do use your own task file, place it into the app's *assets* directory.

This application should be run on a physical Android device to take advantage of the camera.

![Hand Landmark Detection Demo](landmarker.gif?raw=true "Hand Landmark Detection Demo")

## Build the demo using Android Studio

### 简介

*   这是一个建立在Mediapipe官方提供的示例基础上，结合Tensorflow lite 模型进行手势预测的项目

### Building

*   使用Android Studio进行项目的构建。
### 8.2

*   目前项目以及可以结合MP和TF进行第一帧的预测，接下来需要完成实时检测的内容 --zzy

