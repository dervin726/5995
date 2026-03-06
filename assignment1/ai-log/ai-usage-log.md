# AI Usage Log

## Scope
This log records AI-assisted workflow support used during APK decompilation, code navigation, vulnerability assessment, and report drafting.

## Summary of AI-Assisted Interactions

### 1. Decompilation workflow selection
- Goal: decide how to inspect the provided APK.
- AI support used: suggested a workflow centered on JADX for Java source recovery and manifest/resource browsing.
- Outcome: the APK contents were inspected under `code/resources` and `code/sources`, including `AndroidManifest.xml`, `MainActivity.java`, `Login.java`, and `Profile.java`.

### 2. Login and registration behavior tracing
- Goal: understand registration, login, and logout behavior.
- AI support used: directed searches for `credentials.txt`, click handlers, session state, and layout files.
- Outcome: identified that registration appends plaintext credentials to `credentials.txt`, login reads the same file, and logout only removes a local session token.

### 3. Randomness inventory and vulnerability selection
- Goal: locate all values treated as random, tokens, or identifiers.
- AI support used: guided searches for `Random(`, `sessionToken`, and related APIs.
- Outcome: two random uses were identified:
  - `MainActivity.randomNumberGenerator()` using `new Random().nextInt(100)` for non-security/demo behavior.
  - `Login.generateSessionToken()` using `new Random()` for a session token. This was selected as the core vulnerability because it affects authenticated state.

### 4. Threat model and exploitation reasoning
- Goal: explain why the session token generator is unsuitable and what attacker knowledge is needed.
- AI support used: validated that `java.util.Random` is not cryptographically secure and helped structure a realistic attacker model and attack path.
- Outcome: the report frames the weakness around session token predictability under a local reverse-engineering attacker model.

### 5. Report drafting and mock Q&A
- Goal: turn the analysis into a concise report and rubric-ready defense.
- AI support used: condensed findings into report sections and produced a short rubric-driven mock Q&A.
- Outcome: created `report.tex`, `report.md`, and this log.

## Evidence Extracted From the Decompiled App
- Package: `com.example.mastg_test0016`
- Launcher activity: `MainActivity`
- Credential storage: `MainActivity.saveCredentialsToFile()`
- Credential validation: `Login.checkCredentials()`
- Session token creation: `Login.createSession()` and `Login.generateSessionToken()`
- Logout behavior: `Profile.clearSession()`

## Rubric-Driven Mock Q&A

**Q1. Why is this a cryptographic vulnerability rather than only a code-quality issue?**  
Because the app uses `java.util.Random` to generate a value that represents authenticated state (`sessionToken`). A predictable generator is acceptable for UI randomness but not for authentication material.

**Q2. What makes the token security-relevant in this app?**  
`Login.createSession()` writes the token to `SharedPreferences` and `Profile.clearSession()` removes it during logout, so the token is intended to model a logged-in session.

**Q3. What are the attacker capabilities in your model?**  
The attacker can obtain and decompile the APK, inspect the exact token generator, and observe or trigger login events on a device, emulator, or rooted environment.

**Q4. Did you find any other weaknesses?**  
Yes. The app stores plaintext credentials in `credentials.txt`, the login UI can hide the password field due to broken constraints, and logout only removes a token without redirecting or enforcing authorization checks.

**Q5. Why did you choose the token issue as the core vulnerability?**  
It best matches the assignment hint about randomness and crypto, and it directly affects an authentication-related value rather than a cosmetic random number.

## Key Improvements Identified During Mock Q&A
- Make the threat model explicit about local vs. remote impact.
- Distinguish the demo random number from the security-sensitive session token.
- State clearly that the current impact is local session integrity, with higher risk if the pattern is reused in a backend-connected app.
