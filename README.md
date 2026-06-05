# BMRITest - Movie App

Aplikasi Android buat lihat-lihat film, datanya ambil dari API [TMDB](https://www.themoviedb.org/).
Dibikin pakai Jetpack Compose.

## Tech Stack

- Kotlin + Jetpack Compose
- MVVM + Clean Architecture (dipisah jadi module `app` sama `core`)
- Hilt
- Retrofit + OkHttp 
- Coroutines + Flow + StateFlow
- Coil 
- Compose Navigation

## Struktur folder (singkat)

```
app/                  -> tampilan (Compose), ViewModel, navigasi
core/
 ├─ data/
 │   ├─ remote/        -> DTO + service Retrofit
 │   └─ repository/    -> implementasi repository
 ├─ domain/
 │   ├─ model/         -> model bersih buat dipakai UI
 │   └─ repository/    -> interface repository
 ├─ di/                -> modul Hilt (network & repository)
 └─ utils/             -> Resource, Mapper Data, dll
```

## Cara jalanin

1. Clone project ini, buka di Android Studio.

2. Bikin token di TMDB:
    - Daftar/login di https://www.themoviedb.org/
    - Buka **Settings -> API**
    - Copy **API Read Access Token** 

3. Buka file `local.properties` (ada di folder paling luar project), tambahin access token disini:

   ```
   ACCESS_TOKEN=abc...
   ```

4. Sync Gradle, terus Run.

## Catatan

- Token disimpan di `local.properties` biar nggak ikut ke-commit ke Git.
- App ini ambil data langsung dari internet (online), jadi belum ada fitur offline.