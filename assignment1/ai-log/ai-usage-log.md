# AI Usage Log

## Purpose
This file records how AI was used to support Assignment 1. It focuses on prompt-response summaries, the actions taken after each response, and brief validation notes.

## AI-Assisted Steps

### Step 1: Understand the task
**Prompt used**
"Read the assignment brief and tell me what the submission must include, what the core security scope is, and what parts are most important for marks."

**AI response summary**
- The assignment focuses on one randomness or cryptography vulnerability in the provided APK.
- The required deliverables are `report.pdf`, `ai-log/`, `pocs/`, `presentation.mp4`, and `activity-log.pdf`.
- The rubric mainly rewards a clear system/threat model, correct vulnerability explanation, a concrete fix, and a defendable presentation/Q&A.

**What I did**
- Used this summary to narrow the project to one strong crypto-related issue instead of searching for many unrelated bugs.
- Built the report and presentation around model, vulnerability, attack path, and mitigation.

**Validation**
- Cross-checked the AI summary against the assignment specification and rubric PDFs before continuing.

### Step 2: Choose a decompilation workflow
**Prompt used**
"What is a simple workflow for analysing an Android APK if I only need to inspect code, manifest files, and login-related logic?"

**AI response summary**
- Use JADX to inspect decompiled Java code and resources.
- Check `AndroidManifest.xml`, launcher activities, login/registration classes, and local storage logic first.
- Record package name, key activities, and any files or preferences related to credentials or tokens.

**What I did**
- Followed the suggested workflow to inspect the APK contents.
- Located the main app components and focused on authentication-related code paths.

**Validation**
- Confirmed the package name, manifest, and relevant classes were accessible in the decompiled output.

### Step 3: Trace app behaviour
**Prompt used**
"Give me a checklist for understanding registration, login, session handling, and logout behaviour in a decompiled Android app."

**AI response summary**
- Trace input handlers, file writes, preference storage, session creation, and logout cleanup.
- Look for credential files, token generation, and state checks used after login.

**What I did**
- Followed the checklist to inspect registration, login, and logout behaviour.
- Identified where credentials were stored and where session-related values were generated and cleared.

**Validation**
- Matched the code paths across multiple classes instead of relying on a single AI suggestion.

### Step 4: Find security-sensitive randomness
**Prompt used**
"What should I search for in code if I want to find random values that may be security-sensitive?"

**AI response summary**
- Search for `Random`, token generators, codes, identifiers, keys, session values, and helper methods that create unpredictable-looking strings.
- Separate UI/demo randomness from authentication or cryptographic use.

**What I did**
- Searched the decompiled code for random number generation and token creation.
- Built a short list of random-related code locations and checked their security role.

**Validation**
- Confirmed that one random value was only cosmetic, while another was used for session handling.

### Step 5: Select the core vulnerability
**Prompt used**
"Is `java.util.Random` acceptable for generating a session token in an Android app? Explain the security problem in simple terms."

**AI response summary**
- `java.util.Random` is predictable and should not be used for security-sensitive tokens.
- A session token should be generated with a cryptographically secure source such as `SecureRandom`.

**What I did**
- Chose predictable session token generation as the main vulnerability for the report.
- Framed the issue as a randomness/crypto misuse directly tied to authenticated state.

**Validation**
- Verified that the token was actually used as session state in the app before treating it as the main issue.

### Step 6: Build the threat model and attack path
**Prompt used**
"Help me write a realistic attacker model and a short step-by-step attack scenario for a predictable session token in a decompiled APK."

**AI response summary**
- Use a local reverse-engineering attacker model.
- Explain that the attacker can decompile the APK, inspect token generation, observe app behaviour, and predict or reproduce session-related values.
- Present the attack as a short sequence from APK analysis to token prediction and misuse.

**What I did**
- Used this structure to draft the system/threat model and exploitation explanation.
- Kept the attacker capabilities realistic and linked them directly to the identified weakness.

**Validation**
- Removed claims that could not be supported by the code or by the chosen attacker model.

### Step 7: Draft the fix
**Prompt used**
"What is a concrete mitigation for predictable session token generation, and why does it work?"

**AI response summary**
- Replace `java.util.Random` with `SecureRandom`.
- Generate longer, unpredictable session identifiers and store/validate them consistently.
- The fix works because `SecureRandom` is designed for security-sensitive randomness.

**What I did**
- Used this as the basis for the mitigation section in the report and presentation.

**Validation**
- Checked that the proposed fix directly addressed the identified weakness rather than a different issue.

### Step 8: Improve the report structure
**Prompt used**
"Organise my report into a short introduction, system/threat model, discovery method, vulnerability details, attack path, and mitigation within a tight page limit."

**AI response summary**
- Suggested a compact structure that matches the assignment requirements and rubric.
- Emphasised clear evidence, concise explanation, and direct linkage between model, vulnerability, and fix.

**What I did**
- Used the structure to refine the report and keep it concise.

**Validation**
- Compared the final structure against the required report content listed in the assignment brief.

### Step 9: Rubric-driven mock Q&A
**Prompt used**
"Use the assignment rubric to ask me short tutorial-style questions about the system model, vulnerability, attack, and mitigation. Then tell me what needs improvement."

**AI response summary**
- Generated likely tutor questions about attacker capabilities, why the issue is crypto-related, how the vulnerability was found, and why the mitigation is sound.
- Highlighted weak areas such as vague threat model wording and unclear validation language.

**What I did**
- Used the questions to rehearse answers for the recorded presentation and tutorial Q&A.
- Revised explanations so they were more evidence-based and easier to defend.

**Validation**
- Checked each answer against the code evidence and removed unsupported wording.

## Key Improvements Made After AI Use
- Narrowed the assignment to one strong randomness/crypto vulnerability.
- Made the threat model more explicit and realistic.
- Separated non-security randomness from the security-sensitive token issue.
- Strengthened the explanation of why the mitigation works.
- Improved readiness for rubric-based Q&A.

## References
- Assignment specification PDF: `assignment1-spec-2.pdf`
- Assignment rubric PDF: `assignment1-rubric-2.pdf`
- Tool used for code inspection: JADX
- General platform/documentation checked during validation: Android/Java documentation for randomness APIs
