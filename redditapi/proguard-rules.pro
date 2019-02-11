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

# https://github.com/kittinunf/fuel/blob/master/fuel-android/proguard-rules.pro
-keep class com.github.kittinunf.fuel.android.util.AndroidEnvironment

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-dontwarn sun.misc.**
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
#-keep class com.worker8.redditapi.model.**
#-keep class com.worker8.redditapi.model.t3_link.response.** { *; }
#-keep class com.worker8.redditapi.model.t3_link.response.RedditLinkListingObject
#-keep class com.worker8.redditapi.model.t3_link.response.RedditLinkObject
#-keep class com.worker8.redditapi.model.t3_link.response.**
#-keep class com.worker8.redditapi.model.**
#-keep class com.worker8.redditapi.** { *; }
-keep class com.worker8.redditapi.model.** { *; }
-dontshrink
-dontoptimize
-dontpreverify

# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keep class com.google.gson.** { *; }


##---------------End: proguard configuration for Gson  ----------

-keep class com.github.kittinunf.**
-keep public class com.github.kittinunf.** {
  public protected *;
}
-dontobfuscate
