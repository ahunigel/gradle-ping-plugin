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
        classpath group: 'com.github.ahunigel', name: 'gradle-ping-plugin', version: '{version}'
    }
}

apply plugin: 'ping'

ping {
    host = '10.161.92.100'
    timeout = 3000
    toggleOfflineMode = false
}
```

_Refer to https://jitpack.io/#ahunigel/gradle-ping-plugin for details._
