package sikxx.toys.sikxxplaylink.data.repository

import sikxx.toys.sikxxplaylink.data.datastore.TNQRSOnboardingPrefs
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class TNQRSOnboardingRepo(
    private val tnqrsOnboardingStoreManager: TNQRSOnboardingPrefs,
    private val coroutineDispatcher: CoroutineDispatcher,
) {

    fun observeOnboardingState(): Flow<Boolean?> {
        return tnqrsOnboardingStoreManager.onboardedStateFlow
    }

    suspend fun setOnboardingState(state: Boolean) {
        withContext(coroutineDispatcher) {
            tnqrsOnboardingStoreManager.setOnboardedState(state)
        }
    }
}