# AI Usage Log

## Purpose

This file records limited AI assistance used during Assignment 1. AI was used to simplify parts of the assignment wording and to help prepare and rehearse a mock Q&A. APK evidence, vulnerability selection, and final claims were checked manually.

## AI Use

- simplifying parts of the assignment specification and rubric
- helping generate and rehearse likely tutorial or presentation questions

## Manual Validation

- assignment scope checked against `assignment1-spec-2.pdf`
- marking criteria checked against `assignment1-rubric-2.pdf`
- APK evidence checked manually
- final vulnerability choice checked manually
- final report wording checked manually

## Example AI-Assisted Workflow

1. Use AI to simplify the assignment wording.
2. Re-check the official specification and rubric manually.
3. Use AI to generate and rehearse likely rubric-based questions.
4. Re-check APK evidence and final claims manually.

## Mock Q&A Summary

Question: Why is the chosen issue in scope for Assignment 1?  
Answer: Because it concerns a session-related token and whether its random-value generation is suitable for a security-sensitive role.

Question: What makes the token security-sensitive?  
Answer: It is linked to session handling rather than cosmetic randomness. In the decompiled APK, the token is associated with login, session creation, and logout behaviour.

Question: Why is this not just a normal coding mistake?  
Answer: Because the random value is being used for authenticated session state. Once a random value affects authentication, predictability becomes a security issue rather than only a code-quality issue.

Question: Which code locations were treated as the main evidence?  
Answer: The report focuses on the decompiled logic corresponding to `Login.generateSessionToken()`, `Login.createSession()`, and `Profile.clearSession()`, because these locations connect token generation, token storage, and token removal.

Question: Why is ordinary randomness not the same as session-token randomness?  
Answer: A UI-only random value can be low-risk, but a session token is part of authentication state. That means the requirement is stronger because predictability affects security rather than only display or functionality.

Question: What attacker model is being used?  
Answer: A realistic attacker can obtain the APK, decompile it, inspect the session-related logic, and observe or trigger login behaviour in a local test environment.

Question: What is the basic attack path described in the report?  
Answer: The report describes a local analysis path: obtain the APK, recover the session-token logic from decompiled code, observe how login triggers token creation, and then assess whether the token-generation approach could make future session values more predictable than intended.

Question: Why does the report avoid claiming full account takeover?  
Answer: Because the APK evidence supports a randomness misuse argument, but it does not by itself justify exaggerated claims. The report therefore keeps the impact statement conservative and tied to session-integrity and predictability risk.

Question: Why was this issue chosen instead of other random-looking code?  
Answer: Because the selected random value is connected to session handling and authentication state. Other random-looking values may exist in the APK, but they are weaker candidates if they do not protect a security-sensitive function.

Question: What is the strongest evidence supporting the claim?  
Answer: The strongest evidence is the decompiled session flow linking token generation, token storage, and token removal through `Login.generateSessionToken()`, `Login.createSession()`, and `Profile.clearSession()`.

Question: What is the main fix?  
Answer: Replace the non-cryptographic token-generation approach with `java.security.SecureRandom`.

Question: Is changing token length alone enough?  
Answer: No. The core problem is the type of random generator used for a security-sensitive value. A longer token generated in an unsuitable way does not address the main issue as well as switching to `SecureRandom`.

Question: What did this mock Q&A improve?  
Answer: It helped us make the session role of the token clearer, keep the report focused on the randomness issue, and avoid overstating impact beyond the APK evidence.
