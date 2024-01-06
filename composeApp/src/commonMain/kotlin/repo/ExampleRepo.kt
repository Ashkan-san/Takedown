package repo

import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.AppConfiguration
import io.realm.kotlin.mongodb.Credentials
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class ExampleRepo {
    // Realm Instanz
    private lateinit var realm: Realm

    // App Service Instanz
    private val appId = "takedown-yoxyi"
    private val app by lazy {
        val configuration = AppConfiguration.create(appId)
        App.create(configuration)
    }

    init {
        CoroutineScope(Dispatchers.IO).launch {
            setupRealmSync()
        }
    }

    private suspend fun setupRealmSync() {
        // Anonym anmelden, weil noch kein login
        val user = app.login(Credentials.anonymous())

        // Database erstellen
        val config = SyncConfiguration
            .Builder(user = user, schema = setOf(RealmExample::class))
            .name("example")
            .initialSubscriptions { realm ->
                add(
                    query = realm.query<RealmExample>(
                        //user.id
                    ),
                    name = "example-objects",
                    updateExisting = true
                )
            }
            /*.initialData {
                val example = RealmExample().apply {
                    name = "Initial"
                    date = 40
                }
                copyToRealm(example)

            }*/
            .log(LogLevel.ALL)
            .build()

        realm = Realm.open(config)
    }

    suspend fun addExample() {
        println("REALM ADD")
        val example = RealmExample().apply {
            name = "Carlo"
            date = 20
        }

        /*// Persist it in a transaction
        realm.writeBlocking {
            val managedExample = copyToRealm(example)
        }*/

        println("REALM INSTANZ: $realm")
        // Asynchronous updates with Kotlin coroutines
        realm.write {
            val managedExample = copyToRealm(example)

        }
    }

    /*fun addExpression(expression: String): Expression {
        return realm.writeBlocking {
            copyToRealm(Expression().apply { expressionString = expression })
        }
    }

    fun expressions(): List<Expression> {
        return realm.query<Expression>().find()
    }

    fun observeChanges(): Flow<List<Expression>> {
        return realm.query<Expression>().asFlow().map { it.list }
    }*/
}