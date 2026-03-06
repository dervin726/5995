# PoCs

## PoC 1: Session Token Generated with `java.util.Random`

### Goal
Demonstrate that the application uses a predictable pseudo-random generator for an authentication-related session token.

### Evidence
- `code/sources/com/example/mastg_test0016/Login.java`
- `Login.createSession()` stores `sessionToken` in `SharedPreferences`
- `Login.generateSessionToken()` builds the token using `new Random()`

### Reproduction Steps
1. Decompile `a1_case1.apk` and inspect `Login.java`.
2. Locate `generateSessionToken()`.
3. Confirm that the token is generated from `java.util.Random` rather than `SecureRandom`.
4. Confirm that `createSession()` writes this token under the key `sessionToken`.
5. Confirm that `Profile.clearSession()` removes the same key during logout, showing that the value is used as session state.

### Security Claim
The token is not generated with a cryptographically secure RNG. An attacker who reverse-engineers the APK and can observe token creation timing or outputs has a basis for prediction attempts against session material.

## PoC 2: Supporting Weaknesses
- Registration appends plaintext credentials to local file `credentials.txt`.
- The login page can hide the password field on some layouts due to fragile constraints.
- Logout only removes a token and does not redirect or enforce access control.
