# Architecture

- `keyboard/`: Android InputMethodService, custom keyboard view, and input dispatch.
- `layouts/`: declarative key definitions and layout data.
- `suggestions/`: reserved for local, lightweight suggestion logic.
- `settings/`: reserved for user preferences and configuration UI.
- `themes/`: reserved for keyboard colors and appearance.
- `clipboard/` and `emoji/`: reserved for optional keyboard panels.

The keyboard is drawn on one custom View rather than creating a View for every key. This keeps the initial rendering path small and reduces view-hierarchy overhead on the Galaxy A05 target.
