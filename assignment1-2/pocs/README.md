# PoC Materials

## Purpose

This folder contains the proof-of-concept materials for Assignment 1. Its purpose is to show how the selected vulnerability was identified in the provided APK, how the session-related token logic was traced, and why the token-generation approach is unsuitable for a security-sensitive role.

## Submission Note

The assignment specification asks for `pocs/ (videos + README)`. This folder contains the recorded PoC walkthrough video together with this README.

## Scope of the PoC

The PoC supports the same finding described in the report:

- the application generates a session-related token
- the token is part of login/session handling
- the token-generation logic uses an unsuitable non-cryptographic random approach

The materials in this folder document the discovery path, the relevant evidence, and the security reasoning behind the finding.

## Files

- `README.md` - overview of the PoC materials
- `assignment1-poc-walkthrough.mp4` - recorded PoC walkthrough video showing the app flow from the main page to the profile page
- `session-token-evidence.pdf` - evidence excerpt showing the decompiled session-related logic used in the report

## Environment and Requirements

The PoC can be reproduced with a local static-analysis setup. A full exploit environment is not required.

Suggested environment:

- a desktop or laptop system capable of opening PDFs and running APK decompilation tools
- any common operating system such as macOS, Windows, or Linux
- access to the provided APK file `a1_case1.apk`

Suggested tools:

- an APK decompiler such as `jadx`
- a text or code viewer for inspecting decompiled classes
- optional emulator or test device if the app behaviour also needs to be observed dynamically

## Reproduction Steps

1. Obtain the provided APK file `a1_case1.apk`.
2. Decompile the APK with a tool such as `jadx`.
3. Search the recovered strings and classes for login-, session-, and token-related names.
4. Locate the evidence chain involving `sessionToken`, `SessionPrefs`, `createSession`, `clearSession`, and `generateSessionToken`.
5. Inspect the decompiled logic corresponding to `Login.generateSessionToken()`, `Login.createSession()`, and `Profile.clearSession()`.
6. Confirm that the token is generated as part of login/session handling rather than as a cosmetic or UI-only random value.
7. Review the token-generation logic and assess whether the random-value generation method is appropriate for a security-sensitive session token.
8. Compare the observed design with the expected use of a cryptographically secure generator such as `java.security.SecureRandom`.

## Evidence Used

The PoC is based on the provided APK and the decompiled session-related logic described in the report. The main evidence chain is:

1. identify login/session-related names in the APK
2. trace the token lifecycle through session creation and session-clearing logic
3. locate the token-generation logic
4. explain why this random-value generation method is not appropriate for a session-related token

## PoC Demonstration Summary

The demonstration can be presented as a short walkthrough:

1. Show the app flow from the main page to the profile page
2. Use the recorded video to show that the APK contains login and session-related functionality
3. Use `session-token-evidence.pdf` as supporting evidence for the token-generation and session-management logic
4. Explain that the token is used in login/session handling rather than as an ordinary UI-only random value
5. Explain why this is a randomness misuse issue rather than only a coding-style issue

## Conclusion

The selected issue is a randomness-related security problem because a session-sensitive token is generated using an unsuitable random-value generation approach.
