Fix the Android project at /tmp/sikxx-play-link so the failing quality-fix-1 step passes.

Use these orchestrator instructions: /home/codex-agent/codex-app-agent/AGENTS.md
Screen spec: /home/codex-agent/codex-app-agent/screens-shop.md
Do not push to GitHub, do not update Asana, and do not send Slack.
Fix formatting failures by expanding the affected Kotlin code; do not suppress or bypass the formatting checks.

Recent failure log:
```text
=== QUALITY CHECK: /tmp/sikxx-play-link ===

WARN: Only 1 commit(s) — final implementation commit may not exist yet
/home/codex-agent/codex-app-agent/quality-check.sh: line 39: [: 0
0: integer expression expected
  OK: Repository: 0
0 entries
  OK: 9 images
  OK: All images valid
  OK: No placeholder-like drawable images
  OK: No empty onClick
  OK: No obvious no-op onClick handlers
  OK: icon.png (203881B, 512x512, rounded opaque canvas, transparent corners)
FAIL: Manifest references .SkeletonApplication but class not found — CRASH
  OK: HomeScreen.kt: 225 lines
  OK: No project-local agent instruction files
  OK: dynamicColor not enabled
  OK: Google Fonts dependency found
FAIL: font_certs.xml missing
  OK: HorizontalPager used
  OK: No drawable resources detected in AsyncImage lines
  OK: Kotlin source formatting

=== RESULT: 2 error(s) ===
FIX ALL ISSUES BEFORE PUSH

```
