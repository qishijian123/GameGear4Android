## 手游变速器 - GameGear

安卓端免root手游变速器，支持主流游戏引擎开发的游戏，包括H5游戏。设备兼容性良好，支持大部份真机和模拟器，为玩家提供稳定流畅的游戏加速/减速体验。

本项目是快速接入变速器SDK的示例性Demo，演示了如何使用SDK提供的接口，轻松实现游戏的加速/减速。

**注意：项目中的SDK版本是beta版，仅适用于Demo，如需正式版，请联系我们获取。**

**微信：bluesky221010**

## SDK接入指引

### 1. SDK配置

将GearSDK_Android_1.0.\*.aar拷贝到项目libs目录下，在build.gradle中添加以下配置：

```gradle
repositories {
    flatDir {
        dirs 'libs'
    }
}
dependencies {
    implementation(name: 'GearSDK_Android_1.0.*', ext: 'aar')
}
```

### 2. 同意隐私协议

在用户同意隐私协议后，调用如下api同意隐私协议。注意：此api非常重要，若您没有隐私协议需求，也需要在合适的时机调用此api同意隐私协议，例如在Application的onCreate中，否则将导致SDK无法正常使用。

```java
GearSDK.setAgreePrivacy(true);
```

### 3. 使用内置UI

若您没有自定义加速器UI的需求，则可以使用我们SDK内置的加速器UI控制面板。

#### 显示悬浮球

```java
GearSDK.showGearView(activity);
```

注意：传入的Activity对象需要是游戏界面的主Activity。

#### 隐藏悬浮球

```java
GearSDK.hideGearView();
```

### 4. 自定义UI

若您需要自定义加速器UI，则可以调用以下api实现对游戏的速度控制。

#### 配置速度档位

调用如下api可配置加速器的速度档位和游戏最大加速倍速：

```java
GearSDK.config(maxSpeedIndex, maxGear);
```

maxSpeedIndex：最大速度档位，默认为10。

maxGear：最大加速倍速，即游戏的真实加速倍速，默认为5。

#### 设置加速模式下的速度档位

调用如下api可设置加速模式下的速度档位，最小速度档位为1，最大速度档位为maxSpeedIndex，默认为10。速度档位值越大，游戏速度越快，最快为maxGear，默认为5倍率。

```java
GearSDK.speedUp(speedIndex);
```

#### 设置减速模式下的速度档位

调用如下api可设置减速模式下的速度档位，最小速度档位为1，最大速度档位为maxSpeedIndex，默认为10。速度档位值越大，游戏速度越慢。

```java
GearSDK.speedDown(speedIndex);
```

#### 开始加速

调用如下api可开启游戏加速。

```java
GearSDK.start();
```

#### 停止加速

调用如下api可停止游戏加速。

```java
GearSDK.stop();
```

## 混淆配置

如果你的 apk 最终会经过代码混淆，请在 proguard 配置文件中加入以下代码：

```java
-keep class com.youtoolx.android.gear.sdk.InitProvider{
    native <methods>;
}
-keep class com.youtoolx.android.gear.sdk.GearSDK {*;}
```


## 致谢
[ShadowHook](https://github.com/bytedance/android-inline-hook): [MIT license](https://github.com/bytedance/android-inline-hook/blob/main/LICENSE)
