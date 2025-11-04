# AI Collaboration Notes

- **Assistance scope:** AI helped scaffold the Jetpack Compose navigation flow, ViewModel-driven state, and screen layouts. AI also write sample recipes.
- **Manual review & corrections:** During implementation I reviewed and adjusted AI output, especially around navigation edge-cases. For example, AI initially suggested reusing the home icon for the back action on the detail screen—a navigation affordance mismatch. I corrected this to use the proper back arrow and verified stack behavior with `popUpTo` and `launchSingleTop`.
- **Unresolved misunderstandings:** The main misinterpretation was the icon choice noted above; the navigation logic itself aligned once I clarified the intent to keep the detail screen off the bottom bar and manage the back stack explicitly.
- **Human-authored pieces:** In-memory persistence, validation, and documentation were tailored manually to satisfy the assignment requirements.