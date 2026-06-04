-keepattributes RuntimeVisibleAnnotations,AnnotationDefault
-keep,includedescriptorclasses class dev.faizal.bmritest.navigation.**$$serializer { *; }
-keepclassmembers class dev.faizal.bmritest.navigation.** {
    *** Companion;
}

-keepclasseswithmembers class dev.faizal.bmritest.navigation.** {
    kotlinx.serialization.KSerializer serializer(...);
}