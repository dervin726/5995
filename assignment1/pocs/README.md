# PoC README

## Files
- `poc.mp4`: short demonstration video for the selected vulnerability.

## PoC Target
This PoC demonstrates the main vulnerability selected for Assignment 1: the application generates a session token with `java.util.Random`, which is not suitable for security-sensitive randomness.

## Why This PoC Matters
The assignment requires a validated vulnerability related to randomness or cryptographic misuse in the provided APK. This PoC supports that requirement by showing:
- where the session token is created,
- how it is stored and used as session state,
- why the generation method is weak,
- and what fix should be applied.

## Code Evidence Shown In The Video
- `code/sources/com/example/mastg_test0016/Login.java`
  - successful login calls `createSession()`
  - `createSession()` stores `sessionToken` in `SharedPreferences`
  - `generateSessionToken()` uses `new Random()` and `nextInt(62)`
- `code/sources/com/example/mastg_test0016/Profile.java`
  - `clearSession()` removes the same `sessionToken`
- `code/sources/com/example/mastg_test0016/MainActivity.java`
  - contains another use of `Random`, but only for a non-security random number

## PoC Video Flow
1. Show that successful login calls `createSession()`.
2. Show that `createSession()` stores a value under `sessionToken`.
3. Show that `generateSessionToken()` builds the token with `java.util.Random`.
4. Show that logout removes the same token in `Profile.clearSession()`.
5. Briefly compare this with the non-security random number in `MainActivity`.
6. Conclude that the issue affects session integrity and should be fixed with `SecureRandom`.

## Security Conclusion
The token is security-relevant because it is used as session state rather than as a cosmetic or UI-only random value. Since the token is generated with `java.util.Random`, the implementation uses predictable pseudo-randomness for an authentication-related value.

## Recommended Fix
Replace `java.util.Random` with `SecureRandom` and generate a stronger session token suitable for authentication use.

## Validation Note
The PoC is analysis-based rather than exploit-heavy. Its purpose is to provide clear code evidence that the selected issue is a real randomness/cryptography vulnerability in the APK and that the report’s claims are grounded in the decompiled source.
