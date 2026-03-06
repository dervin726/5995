# AI-Assisted Cryptographic Vulnerability Discovery in an Android APK

## Introduction
An APK is the installable package format for Android applications. Auditing an APK requires decompilation because the distributed file contains compiled bytecode, resources, and the manifest rather than readable source code. For `a1_case1.apk`, the analysis workflow was to inspect the manifest, identify the launcher activity and login-related classes, then trace registration, login, session creation, and logout behavior. The main finding is that the app uses `java.util.Random` to generate a session token, which is not suitable for security-sensitive values.

## System and Threat Model
The decompiled package is `com.example.mastg_test0016`, with `MainActivity` as the launcher activity. The app offers local registration, login, and a profile page. Registration stores plaintext credentials in `credentials.txt`; login reads that file and compares the entered values; successful login creates a `sessionToken` and stores it in `SharedPreferences` under `SessionPrefs`. The profile page shows a title, an empty `WebView`, and a logout button that only deletes the token.

Important assets are the credentials in `credentials.txt`, the session token in `SharedPreferences`, and the authenticated state implied by that token. The attacker is assumed to be able to obtain the APK, decompile it, inspect the token generation logic, and observe or trigger login events on a device, emulator, or rooted environment. The attacker goal is to predict or reconstruct a valid session token and use it to impersonate a logged-in user or bypass intended session integrity.

```text
User -> MainActivity -> credentials.txt
User -> Login -> credentials.txt -> sessionToken -> SharedPreferences
User -> Profile -> Log out -> remove(sessionToken)
```

## How the Vulnerability Was Found
The analysis began by inspecting `AndroidManifest.xml`, `MainActivity.java`, `Login.java`, and `Profile.java`. String and API searches were then used to locate security-relevant values such as credentials, tokens, and random-number generation. AI assistance was used to guide the decompilation workflow and assess whether random-value generators matched their security role. Two random-value sites were found:

- `MainActivity.randomNumberGenerator()`: `new Random().nextInt(100)`, which appears demo-only.
- `Login.generateSessionToken()`: `new Random()` used to create a 16-character session token, which is security-relevant.

## Vulnerability Details
The core vulnerability is in `Login.generateSessionToken()`. The method uses `java.util.Random` to create a 16-character token from a fixed alphanumeric alphabet. This is unsuitable because `java.util.Random` is a general-purpose pseudo-random number generator rather than a cryptographically secure one. Its outputs are deterministic from internal state, so it should not be used for authentication tokens, password reset codes, or keys.

The token is security-relevant because `Login.createSession()` writes it into `SharedPreferences` as `sessionToken`, and `Profile.clearSession()` deletes the same value on logout. This shows that the application uses the token to represent logged-in state.

## Attack Scenario
1. The attacker decompiles the APK and recovers the exact token generation algorithm.
2. The attacker observes when login occurs or collects one or more token outputs in a local test environment.
3. Because the token is generated with `java.util.Random`, the attacker exploits output predictability to reduce the internal state search space or predict future outputs.
4. The attacker injects or reuses a predicted token to impersonate a valid session if the app or a future backend relies on the token for authentication.

## Fix and Mitigation
Replace `java.util.Random` with `java.security.SecureRandom` and generate at least 128-256 bits of entropy before encoding the result in Base64 or hexadecimal. A suitable approach is to generate 32 random bytes with `SecureRandom.nextBytes()` and store the encoded value as the session token. This works because `SecureRandom` is designed to resist prediction, which is the core weakness of the current implementation. The app should also enforce session checks on protected screens and avoid storing plaintext credentials in `credentials.txt`.
