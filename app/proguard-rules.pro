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
# Firebase
-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }

# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature

# Gson Converter for Retrofit
-keep class com.google.gson.** { *; }
-keep class retrofit2.converter.gson.** { *; }

# Glide
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public class * extends com.bumptech.glide.annotation.GlideModule
-keep public class * extends com.bumptech.glide.request.target.Target
-dontwarn com.bumptech.glide.**

# Lottie
-keep class com.airbnb.lottie.** { *; }

# AndroidX and Material Components
-keep class androidx.** { *; }
-keep class com.google.android.material.** { *; }

# ConstraintLayout
-keep class androidx.constraintlayout.** { *; }

# Google Play Services (Auth, Maps, Location)
-keep class com.google.android.gms.** { *; }
-keep class com.google.android.play.core.** { *; }

# CardView and CircleImageView
-keep class androidx.cardview.** { *; }
-keep class de.hdodenhof.circleimageview.** { *; }

# Legacy Support Library
-keep class android.support.v4.** { *; }

# Prevent obfuscation of custom models (if used)
-keep class * extends java.lang.annotation.Annotation { *; }

# Remove logging and debugging information
-assumenosideeffects class android.util.Log { public static *** d(...); public static *** v(...); }

# General optimization
-optimizationpasses 5
-repackageclasses
-dontpreverify
