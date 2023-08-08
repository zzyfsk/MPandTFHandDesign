# MediaPipe and Tensorflow-lite about Hands sign detect

## Build the demo using Android Studio

### 简介

*   这是一个建立在Mediapipe官方提供的示例基础上，结合Tensorflow lite 模型进行手势预测的项目

### mediapipe的结果集介绍

*   首先mediapipe的结果集是由三个部分组成：手的信息，手的坐标，和世界坐标（这个不用管）均为集合存储
*   然后，当其对多个手进行检测的时候是由各个列表add而来也就是说当检测两只手时，mediapipe的下属三个集合的size为2
*   所以无需增加mediapipe的结果集。

### Building

*   使用Android Studio进行项目的构建。
### 8.2

*   目前项目以及可以结合MP和TF进行第一帧的预测，接下来需要完成实时检测的内容 --zzy
*   任务:将一帧的检测完善为实时检测

### 8.3

*   每一帧的检测完成，目前不确定左右手的result是否有区分
*   get不到result的结果？？？？？？？？？？？？？？？？
*   任务:解决result的问题

### 8.4

*   result的结果可以get到了，并且知道了这个result的结构

### 8.5

*   区分左右手的逻辑已经完成，基本的流程可以走通

### 8.6-8.8

*   训练模型中

### 8.8

*   添加了一层textView以显示模型结果
*   完成了检测滤波逻辑