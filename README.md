#  Android Splash Screen API Demo

Demonstracijski Android projekt, ki prikazuje uporabo **Android Splash Screen API** s pogojnim prikazom, simulacijo nalaganja podatkov in **custom exit animacijo**, implementirano v Kotlinu.

---

##  O projektu

Projekt demonstrira:
- uporabo uradnega Android Splash Screen API (`androidx.core:core-splashscreen`)
- zadrževanje splash screena dokler se podatki ne naložijo
- custom **exit animacijo** z `ObjectAnimator` in `AnimatorSet`
- simulacijo nalaganja podatkov s Kotlin Coroutines
- osnovno error handling logiko

Projekt je namenjen **učnim in demonstracijskim namenom**.

---

##  Funkcionalnosti

### 1. Splash Screen z logiko pogoja
- Splash screen ostane viden, dokler se podatki ne naložijo
- Uporaba `setKeepOnScreenCondition { !isDataLoaded }`
- Trajanje je dinamično (odvisno od nalaganja)

### 2. Custom Exit Animacija
Ob izhodu iz splash screena se izvede kombinirana animacija:
- **Fall Down** – ikona pade navzdol čez celoten zaslon
- **Rotation** – 180° rotacija ikone med padanjem
- **Fade Out** – postopno izginjanje ikone
- **Background Slide** – celoten splash screen zdrsne navzdol

Animacije se izvajajo sočasno z `AnimatorSet`.

### 3. Simulacija nalaganja podatkov
- Progress bar med nalaganjem
- Statusna besedila
- Uporaba `lifecycleScope` in `delay()`
- Različno trajanje za prvo in ponovno nalaganje

### 4. Interakcija uporabnika
- Gumb **"Ponovno naloži"**
- Prikaz tehničnih informacij po uspešnem nalaganju
- Prikaz sporočil ob napaki

---
##  Demo video

[Click here to watch the demo video](demo/demo.mp4)

---

##  Utemeljitev izbire tehnologije

###  Prednosti
- Poenoten splash screen na vseh Android napravah
- Uradna AndroidX rešitev
- Minimalna konfiguracija
- Podpora za custom exit animacije
- Dobra zmogljivost (O(1))
- Združljivo z Material Design smernicami

###  Slabosti
- Omejen dizajn (brez kompleksnih layoutov)
- Najboljša podpora na Android 12+ (API 31+)
- Splash screen ni namenjen dolgim operacijam

---

##  Časovna in prostorska zahtevnost

**Časovna zahtevnost**: `O(1)`
- Inicializacija: ~10–50 ms
- Simulirano nalaganje: 1.5–2 s
- Exit animacija: ~1.2 s

**Prostorska zahtevnost**: `O(1)`
- Minimalen memory overhead
- Brez shranjevanja podatkov v RAM

---

##  Tehnične specifikacije

### Dependencies

```kotlin
implementation("androidx.core:core-splashscreen:1.0.1")
implementation("androidx.core:core-ktx:1.12.0")
implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
```

### Build konfiguracija
- Compile SDK: 34
- Min SDK: 21
- Target SDK: 34
- Jezik: Kotlin
- Build sistem: Gradle

---

##  Potek Splash Screena

```
[App Launch]
      ↓
[Splash Screen]
      ↓  (podatki se nalagajo)
[Keep On Screen]
      ↓
[Exit Animation]
      ↓
[Main Screen]
```

---

## ️ Obdelava napak

V aplikaciji so demonstrirani primeri:
- try/catch pri inicializaciji splash screena
- logiranje napak (`Log.e`)
- fallback UI ob napaki
- uporabniku prijazna sporočila

Primer:

```kotlin
try {
    val splashScreen = installSplashScreen()
    setupSplashScreen(splashScreen)
} catch (e: Exception) {
    Log.e("SplashScreen", "Error installing splash screen", e)
}
```

---

##  Naučeno

V projektu so prikazani:
- Android Splash Screen API
- Pogojni splash screen
- Custom exit animacije
- Kotlin Coroutines (`lifecycleScope`)
- Osnovni error handling
- Android lifecycle management

---

##  Viri

- Android Splash Screen API – uradna dokumentacija
- AndroidX Core knjižnica
- Android Animation dokumentacija

---

##  Avtorica

**Nikita Lefterova**

---
