package com.narenbachina.enduranceready.data


/**
 * AppContainer
 *
 * ROLE:
 * Centralized dependency provider for the entire application.
 *
 * PURPOSE:
 * - Creates and holds shared dependencies.
 * - Ensures objects are created only once.
 * - Provides dependencies to other layers (ViewModel, etc.).
 *
 * WHY THIS EXISTS:
 * Instead of creating repositories inside ViewModels (tight coupling),
 * we centralize object creation here and inject them where needed.
 *
 * BENEFITS:
 * - Loose coupling
 * - Easy swapping of implementations/Repositories (Fake → HealthConnect)
 * - Cleaner architecture
 * - Better testability
 */
class AppContainer {
     val healthRepository: HealthRepository = FakeHealthRepository()
}