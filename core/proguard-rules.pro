# --- Jaga DTO dari penghapusan & penggantian nama (untuk Gson) ---
-keep class dev.faizal.core.data.remote.dto.** { *; }

# --- Jaga model domain bila dipakai reflektif ---
-keep class dev.faizal.core.domain.model.** { *; }

# --- Gson: jaga anotasi & field ber-@SerializedName ---
-keepattributes Signature
-keepattributes *Annotation*
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}
