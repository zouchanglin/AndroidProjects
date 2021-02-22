## 组件化的意义

1、开发效率高、维护性强

2、高内聚、低耦合

![](https://yqfile.alicdn.com/img_a0d81d6a6c254707004c4da2c17aa632.png)

## Android组件化工程搭建

四个部分：App壳子工程、common组件、order组件【订单页】、personal组件【个人中心】

0、验证Gradle的顺序

1、定义+引入公共配置

```groovy
// 加载项目的gradle的时候，就引入app_config.gradle
apply from : 'app_config.gradle'
```

2、测试公共配置的有效性

3、不同的插件不同的效果

```groovy
if(isRelease){
    apply plugin: 'com.android.library' // 集成化模式
}else {
    apply plugin: 'com.android.application' // 组件化状态
}

// 组件化状态才需要applicationId
if(!isRelease){
    applicationId "cn.tim.order"
}
```

4、Java文件的排除与

```groovy
// 源集 —— 用来设置Java目录或者资源目录
sourceSets {
  main {
    if(!isRelease){
      // 如果是组件化模式，需要单独运行时
      manifest.srcFile 'src/main/debug/AndroidManifest.xml'
    }else {
      // 集成化模式，整个项目打包
      manifest.srcFile 'src/main/AndroidManifest.xml'
      java {
        // release 时 debug 目录下文件不需要合并到主工程
        exclude '**/debug/**'
      }
    }
  }
}
```

> 注意组件化工程的文件命名与打印日志规范：
>
> order -> OrderMainActivity、order_main_activity.xml、order/OrderMainActivity/onCreate..
>
> Common -> BaseApplication、BaseNetManager...

## 组件之间的路由

ARouter