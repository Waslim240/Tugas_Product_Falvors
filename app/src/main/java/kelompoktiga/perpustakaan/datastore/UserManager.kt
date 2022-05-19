package kelompoktiga.perpustakaan.datastore

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager (context: Context){

    private val dataStore : DataStore<Preferences> = context.createDataStore(name = "data_user")

    companion object{
        val USERNAME = preferencesKey<String>("USERNAME")
        val PASSWORD = preferencesKey<String>("PASSWORD")
        val BOOLEAN = preferencesKey<Boolean>("BOOLEAN")
    }

    suspend fun saveDataUser(username: String, password: String){
        dataStore.edit {
            it[USERNAME] = username
            it[PASSWORD] = password
        }
    }

    suspend fun checkData(boolean: Boolean){
        dataStore.edit {
            it[BOOLEAN] = boolean
        }
    }

    val username : Flow<String> = dataStore.data.map {
        it[USERNAME] ?: ""
    }

    val password : Flow<String> = dataStore.data.map {
        it[PASSWORD] ?: ""
    }

    val boolean : Flow<Boolean> = dataStore.data.map {
        it[BOOLEAN] ?: false
    }

}