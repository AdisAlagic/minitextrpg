# MiniTextRPG 🗡️🛡️

[![Platform](https://img.shields.io/badge/Platform-Android-green.svg?style=flat&logo=android)](https://www.android.com/)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.0+-purple.svg?style=flat&logo=kotlin)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-blue.svg?style=flat&logo=jetpackcompose)](https://developer.android.com/jetpack/compose)
[![NDK](https://img.shields.io/badge/NDK-C%2B%2B%20%2F%20CMake-orange.svg?style=flat&logo=cplusplus)](https://developer.android.com/ndk)
[![API](https://img.shields.io/badge/Min%20SDK-24-blue.svg)](https://developer.android.com/about/dashboards)
[![Target SDK](https://img.shields.io/badge/Target%20SDK-34-brightgreen.svg)](https://developer.android.com/about/versions/14)

**MiniTextRPG** — это пошаговая текстовая RPG для Android, реализованная на **Kotlin** и **Jetpack Compose**, совмещающая механики классических текстовых квестов и скрытый **Reverse Engineering / CTF-челлендж** с защитой секретов на уровне нативного кода (C++ / NDK).

---

## 📖 О проекте

Игрок начинает приключение в саду заброшенного замка и продвигается в тронный зал, где ему предстоит столкнуться с могущественным противником — **Маркусом, рыцарем, поглощенным бездной**. 

Чтобы одолеть босса и получить секретный ключ, игроку предстоит грамотно использовать боевую систему или... исследовать исходный код приложения и раскрыть его скрытые механики!

### Ключевые особенности
* 🎨 **Современный UI**: Полностью написан на **Jetpack Compose** (Material 3) с реактивным автоскроллом лога событий и анимациями.
* 🏛️ **Архитектура MVVM**: Четкое разделение ответственности с `MainViewModel`, реактивными потоками `StateFlow` и иммутабельным состоянием игры.
* ⚔️ **Пошаговая боевая система**:
  * Фазы ходов игрока и противника (`PLAYER` ➔ `MIDDLE` ➔ `ENEMY`).
  * Механика рандомизации урона, учета брони и стойки защиты (`Defend`), усиливающей сопротивление в 1.5 раза.
  * ИИ противника с принятием решений (атака, блок, лечение при падении здоровья ниже 30%).
* 🎒 **Инвентарь и лут**: Система вероятностного дропа предметов (`ItemDrop`), экипировка оружия и использование зелий исцеления.
* 🔐 **Защита данных через NDK / C++**: Хранение и извлечение победного флага с использованием обфускации (SHA-256 + XOR-шифрование, привязанное к идентификатору пакета приложения) через библиотеку `secrets.so`.

---

## 🕹️ Игровые механики

### 🗺️ Локации
1. **Сад (`Garden`)**:
   - Начальная точка.
   - Действие **«Осмотреться»**: позволяет найти зелья лечения. В таблице лута также спрятан *«Фальшивый меч героя»* (с уроном 3000), шанс выпадения которого изначально выставлен в `0.0%`.
   - Действие **«Вперед»**: переход в главный зал замка.
2. **Зал (`Hall`)**:
   - Локация битвы. Входные двери закрываются за спиной героя.
   - Действие **«Поговорить»**: попытка диалога с безмолвным рыцарем.
   - Действие **«Атаковать»**: инициация битвы с боссом.

### ⚔️ Персонажи и баланс

| Персонаж / Сущность | HP | Броня | Оружие | Описание |
| :--- | :---: | :---: | :--- | :--- |
| **Игрок (Player)** | 5 000 | 100 | Меч героя (150 урона) | Стартовая экипировка героя. |
| **Маркус (MarcusKnight)** | 10 000 | 130 | Меч Маркуса (500 урона) | В инвентаре 4 больших зелья здоровья (+5000 HP каждое). |
| *Фальшивый меч героя* | — | — | 3 000 урона | Скрытое оружие, способное сокрушить броню Маркуса. |

---

## 🕵️ Reverse Engineering & CTF челлендж

Проект содержит интересную задачу на анализ мобильных приложений:
1. Одолеть Маркуса стандартным мечом практически невозможно: броня Маркуса (130) поглощает почти весь базовый урон (150), а сам он наносит ощутимый урон и лечится.
2. В файле `Garden.kt` объявлен дроп оружия `FakeHeroSword` с вероятностью `0.0`. Изменение шанса дропа или модификация инвентаря позволяет получить преимущество.
3. При победе над боссом вызывается нативный метод `PlayersInfo.getanbshagndshgnwiahgfnehfgje(...)`, который расшифровывает спрятанный UUID-ключ/флаг из `secrets.cpp`.

---

## 🛠️ Стек технологий

* **Язык**: Kotlin 2.0+
* **UI**: Jetpack Compose, Material 3
* **State Management**: Android ViewModel, StateFlow, Coroutines
* **Native / NDK**: C++ (CMake 3.4.1+, JNI)
* **Плагины безопасности**: [klaxit/hidden-secrets-gradle-plugin](https://github.com/klaxit/hidden-secrets-gradle-plugin)
* **Сборщик**: Gradle с поддержкой Version Catalogs (`libs.versions.toml`)

---

## 📁 Структура проекта

```text
minitextrpg/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── cpp/                     # Нативный C++ код
│   │   │   │   ├── CMakeLists.txt       # Конфигурация сборки NDK библиотеки secrets
│   │   │   │   ├── secrets.cpp          # JNI реализация деобфускации строки флага
│   │   │   │   └── sha256.cpp / .hpp    # Вычисление SHA-256 ключа обфускатора
│   │   │   └── java/com/adisalagic/minitextrpg/
│   │   │       ├── entities/            # Игровые сущности (Player, Enemy, Weapons, Potions)
│   │   │       ├── locations/           # Локации (Garden, Hall, Location interface)
│   │   │       ├── ui/theme/            # Compose темы, шрифты и цвета
│   │   │       ├── viewmodels/          # MainViewModel (логика игры и стейт-машина)
│   │   │       ├── MainActivity.kt      # Основной UI экран на Jetpack Compose
│   │   │       ├── PlayersInfo.kt       # JNI мост для вызова нативной библиотеки
│   │   │       └── Utils.kt             # Вспомогательные функции (RNG, расширения)
│   │   └── res/                         # Ресурсы приложения (иконки, стили)
│   └── build.gradle                     # Зависимости и конфигурация модуля app
├── gradle/
│   └── libs.versions.toml               # Каталог версий зависимостей
├── player.properties                    # Конфигурация исходного секрета
└── settings.gradle                      # Настройки проекта
```

---

## 🚀 Сборка и запуск

### Требования
* **Android Studio** (Koala / Ladybug / Meerkat или новее)
* **JDK 17** или новее
* **Android SDK** с установленным API 34
* **Android NDK** и **CMake** (устанавливаются через SDK Manager в Android Studio)

### Установка и запуск

1. Склонируйте репозиторий:
   ```bash
   git clone https://github.com/AdisAlagic/minitextrpg.git
   cd minitextrpg
   ```

2. Соберите проект через терминал:
   ```bash
   # Windows
   ./gradlew.bat assembleDebug

   # Linux / macOS
   ./gradlew assembleDebug
   ```

3. Либо откройте проект в **Android Studio** и запустите на эмуляторе или физическом устройстве (`Run 'app'`).

---

## 👤 Автор

**Adis Alagic**
* GitHub: [@AdisAlagic](https://github.com/AdisAlagic)
