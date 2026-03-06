# Presentation Outline (<= 5 minutes)

## Slide 1: App Overview
- Package: `com.example.mastg_test0016`
- Main features: register, login, profile, logout
- Assets: plaintext credentials and session token

## Slide 2: System and Threat Model
- Components: `MainActivity`, `Login`, `Profile`
- Storage: `credentials.txt`, `SharedPreferences`
- Attacker: can decompile APK and inspect token generation

## Slide 3: Core Vulnerability
- `Login.generateSessionToken()` uses `java.util.Random`
- Token is used as session state
- `Random` is predictable and not suitable for security-sensitive values

## Slide 4: Attack Path
1. Decompile APK
2. Recover token algorithm
3. Observe or trigger token creation
4. Attempt token prediction or impersonation

## Slide 5: Fix and Lessons
- Replace `Random` with `SecureRandom`
- Enforce token checks on protected pages
- Avoid plaintext credential storage
