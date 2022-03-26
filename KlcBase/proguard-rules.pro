# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-ignorewarnings

-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-keepattributes EnclosingMethod
-keepattributes SourceFile,LineNumberTable
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.app.IntentService
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.view.View

-keepattributes *Annotation*
-keep public class * extends android.app.backup.BackupAgent
-keep public class * extends android.preference.Preference
-keep public class * extends android.app.Fragment
-keep public class * extends androidx.fragment.app.**
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**
-keep public class * extends androidx.lifecycle.**

# 保留support下的所有类及其内部类
-keep class android.support.** {*;}

#Base
-keep class com.klc.bean.*{*;}
-keep class com.klc.httpApi.*{*;}
-keep class com.klc.IMApi.*{*;}
-keep class com.klc.model.*{*;}
-keep class com.klc.model_fun.*{*;}
-keep class * extends com.kalacheng.base.base.BaseFragment { *; }
-keep class * extends com.kalacheng.base.base.BaseMVVMFragment { *; }
-keep class * extends com.kalacheng.base.base.BaseViewHolder { *; }
-keep class * extends com.kalacheng.base.base.BaseMVVMViewHolder { *; }

#Beans
-keep class com.kalacheng.baseLive.**{*;}
-keep class com.kalacheng.buschatroom.model.**{*;}
-keep class com.kalacheng.buscommon.**{*;}
-keep class com.kalacheng.busdynamiccircle.socketmsg.**{*;}
-keep class com.kalacheng.busfinance.**{*;}
-keep class com.kalacheng.busgame.**{*;}
-keep class com.kalacheng.busgraderight.**{*;}
-keep class com.kalacheng.buslive.**{*;}
-keep class com.kalacheng.buslivebas.**{*;}
-keep class com.kalacheng.busliveplugin.socketmsg.**{*;}
-keep class com.kalacheng.busnobility.**{*;}
-keep class com.kalacheng.busooolive.**{*;}
-keep class com.kalacheng.buspersonalcenter.**{*;}
-keep class com.kalacheng.busshop.**{*;}
-keep class com.kalacheng.busshortvideo.**{*;}
-keep class com.kalacheng.bususer.socketmsg.**{*;}
-keep class com.kalacheng.busvoicelive.**{*;}
-keep class com.kalacheng.game.socketmsg.**{*;}
-keep class com.kalacheng.libbas.model.**{*;}
-keep class com.kalacheng.libuser.**{*;}
-keep class com.kalacheng.shop.socketmsg.**{*;}
-keep class com.kalacheng.shortvideo.socketmsg.**{*;}
-keep class com.kalacheng.template.**{*;}

#直播购添加地址对象
-keep class com.kalacheng.util.bean.AddressBean{*;}
-keep class com.kalacheng.util.bean.AddressBean$CitiesBean{*;}
-keep class com.kalacheng.util.bean.AddressBean$CitiesBean$CountiesBean{*;}

#消息
-keep class com.kalacheng.message.bean.*{*;}

#glide
-dontwarn com.bumptech.glide.**

#知乎Matisse额外规则
-dontwarn com.squareup.picasso.**
-keep class com.zhihu.matisse.**{*;}

#微信开放平台
-keep class com.tencent.mm.opensdk.** {
*;
}
-keep class com.tencent.wxop.** {
*;
}
-keep class com.tencent.mm.sdk.** {
*;
}

#ucrop
-dontwarn com.yalantis.ucrop**
-keep class com.yalantis.ucrop** { *; }
-keep interface com.yalantis.ucrop** { *; }

#腾讯实时音视频
-keep class com.tencent.**{*;}
-dontwarn com.tencent.**
-keep class tencent.**{*;}
-dontwarn tencent.**
-keep class qalsdk.**{*;}
-dontwarn qalsdk.**

#支付宝支付
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
-keep class com.alipay.sdk.app.H5PayCallback {
    <fields>;
    <methods>;
}
-keep class com.alipay.android.phone.mrpc.core.** { *; }
-keep class com.alipay.apmobilesecuritysdk.** { *; }
-keep class com.alipay.mobile.framework.service.annotation.** { *; }
-keep class com.alipay.mobilesecuritysdk.face.** { *; }
-keep class com.alipay.tscenter.biz.rpc.** { *; }
-keep class org.json.alipay.** { *; }
-keep class com.alipay.tscenter.** { *; }
-keep class com.ta.utdid2.** { *;}
-keep class com.ut.device.** { *;}

#Mina
-keep class org.apache.mina.** { *; }
-keep class org.slf4j.** { *; }
#Socket
-keep class com.wengying666.imsocket.** { *; }

#极光
-dontoptimize
-dontpreverify
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-keep class * extends cn.jpush.android.helpers.JPushMessageReceiver { *; }
-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }

#极光im  --- start----
-keepattributes  EnclosingMethod,Signature

 -dontwarn cn.jpush.**
 -keep class cn.jpush.** { *; }

 -dontwarn cn.jiguang.**
 -keep class cn.jiguang.** { *; }

 -dontwarn cn.jmessage.**
 -keep class cn.jmessage.**{ *; }

 -keepclassmembers class ** {
     public void onEvent*(**);
 }

 #========================gson================================
 -dontwarn com.google.**
 -keep class com.google.gson.** {*;}

 #========================protobuf================================
 -keep class com.google.protobuf.** {*;}

#极光im  --- end----

#Bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

#JobService
-keep public class * extends android.app.job.JobService

#MTA
-keep class com.tencent.stat.*{*;}
-keep class com.tencent.mid.*{*;}

#直播云
-keep class ttt.ijk.media.**{*;}
-keep class project.android.imageprocessing.**{*;}
-keep class org.TTTRtc.voiceengine.**{*;}
-keep class com.wushuangtech.**{*;}
-dontwarn ttt.ijk.media.**

-keep class org.webrtc.voiceengine.**{*;}
-keep class net.ossrs.**{*;}
-keep class ttt.ijk.**{*;}

#枚举
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#高德地图 3D 地图 V5.0.0之后：
-keep   class com.amap.api.maps.**{*;}
-keep   class com.autonavi.**{*;}
-keep   class com.amap.api.trace.**{*;}
#定位
-keep class com.amap.api.location.**{*;}
-keep class com.amap.api.fence.**{*;}
-keep class com.autonavi.aps.amapapi.model.**{*;}

#美颜
-keep class cn.tillusory.**{*;}
-keep class com.hwangjr.**{*;}
-keep class rx.**{*;}

#mob
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class **.R$* {*;}
-keep class **.R{*;}
-keep class com.mob.**{*;}
-keep class m.framework.**{*;}
-keep class com.bytedance.**{*;}
-dontwarn cn.sharesdk.**
-dontwarn com.sina.**
-dontwarn com.mob.**
-dontwarn **.R$*

#Parcelable
-keep class * implements android.os.Parcelable {
public static final android.os.Parcelable$Creator *;
}

# 保留Serializable序列化的类不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

#ARouter
-keep public class com.alibaba.android.arouter.routes.**{*;}
-keep public class com.alibaba.android.arouter.facade.**{*;}
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}
# If you use the byType method to obtain Service, add the following rules to protect the interface:
-keep interface * implements com.alibaba.android.arouter.facade.template.IProvider
# If single-type injection is used, that is, no interface is defined to implement IProvider, the following rules need to be added to protect the implementation
#-keep class * implements com.alibaba.android.arouter.facade.template.IProvider

#MVVM
-keep class **.*Binding {*;}
-keep class **.*BindingImpl {*;}

#rxjava rxandroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
-dontnote rx.internal.util.PlatformDependent

# 保留本地native方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

# 保留在Activity中的方法参数是view的方法，
# 这样以来我们在layout中写的onClick就不会被影响
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}

# 保留我们自定义控件（继承自View）不被混淆
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# 对于带有回调函数的onXXEvent、**On*Listener的，不能被混淆
-keepclassmembers class * {
    void *(**On*Event);
    void *(**On*Listener);
}

# webView处理，项目中没有使用到webView忽略即可
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
    public *;
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.webView, jav.lang.String);
}

#----------retrofit--------------
#-keepclassmembernames,allowobfuscation interface * {
#    @retrofit2.http.* <methods>;
#}
#-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
#
-keep class retrofit2.** { *; }
-dontwarn retrofit2.**
-keepattributes Signature
-keepattributes Exceptions
-dontwarn okio.**
-dontwarn javax.annotation.**

-dontoptimize
-dontpreverify
-keepattributes  EnclosingMethod,Signature
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }

-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }

-dontwarn cn.jmessage.**
-keep class cn.jmessage.**{ *; }

-keepclassmembers class ** {
    public void onEvent*(**);
}

#========================gson================================
-dontwarn com.google.**
-keep class com.google.gson.** {*;}

#========================protobuf================================
-keep class com.google.protobuf.** {*;}

#========================声望================================
-keep class io.agora.**{*;}

-keep public class com.xuantongyun.storagecloud.**{*;}

#叭叭云
-keep class com.qiniu.**{*;}
-keep class com.qiniu.**{public <init>();}

#叭叭美颜2
-keep class com.faceunity.wrapper.faceunity {*;}