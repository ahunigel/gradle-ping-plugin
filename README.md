# gradle-ping-plugin
[![](https://jitpack.io/v/ahunigel/gradle-ping-plugin.svg)](https://jitpack.io/#ahunigel/gradle-ping-plugin)


## How to use

### Add the JitPack repository and dependency to your build script
```groovy
buildscript {
    repositories {
        maven { url 'https://jitpack.io' }
    }
    dependencies {
        classpath group: 'com.github.ahunigel', name: 'gradle-ping-plugin', version: '1.0-SNAPSHOT'
    }
}
apply plugin: 'ping'
```

_Refer to https://jitpack.io/#ahunigel/gradle-ping-plugin for details._
