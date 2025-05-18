package rudzki.marek.shelfie.account.repositories

import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth

class AuthRepository(
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
) {
    fun logout(): com.google.android.gms.tasks.Task<Void> {
        firebaseAuth.signOut()
        return Tasks.forResult(null)
    }

    fun deleteUser(): com.google.android.gms.tasks.Task<Void> {
        val user = firebaseAuth.currentUser ?: return Tasks.forException(IllegalStateException("User not logged"))
        return user.delete()
    }

    fun isUserLoggedIn(): Boolean = firebaseAuth.currentUser != null
}