# Android 解决重复点击
## 使用
### 1. 项目build.gradle
```gradle
buildscript {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
    dependencies {
    	...
        classpath 'com.hujiang.aspectjx:gradle-android-plugin-aspectjx:2.0.10'
    }
}
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
### 2. 在主模块build.gradle中应用aspectjx插件
```gradle
...
apply plugin: 'android-aspectjx'
android {
    ...
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}
dependencies {
    ...
    implementation 'com.github.SerenadeHL:FastClick:1.0.0'
}
```
### 3.使用

```kotlin
@FastClick(limit = 500)
fun onBtnClick(){}
```

