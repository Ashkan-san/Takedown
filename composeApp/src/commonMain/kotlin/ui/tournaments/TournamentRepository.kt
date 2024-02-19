package ui.tournaments

import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.AppConfiguration
import io.realm.kotlin.mongodb.Credentials
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import kotlinx.coroutines.runBlocking
import model.tournament.Ranking
import model.tournament.Tournament
import model.tournament.TournamentClub
import model.tournament.TournamentDate
import model.tournament.WrestleClass

/**
 * Repository für alle Methoden, um Turniere aus der Realm Datenbank in die App zu fetchen
 */
class TournamentRepository {
    // Realm Instanz
    private lateinit var realm: Realm
    private lateinit var config: SyncConfiguration

    // App Service Instanz
    private val appId = "takedown-yoxyi"
    private val app by lazy {
        val configuration = AppConfiguration.create(appId)
        App.create(configuration)
    }

    init {
        runBlocking {
            val user = app.login(Credentials.anonymous())

            config = SyncConfiguration.Builder(
                user,
                setOf(
                    Tournament::class, TournamentDate::class, TournamentClub::class, WrestleClass::class, Ranking::class
                )
            ).initialSubscriptions { realm ->
                add(
                    query = realm.query<Tournament>(),
                    "Tournaments",
                    //updateExisting = true
                )
            }
                .schemaVersion(2)
                //.log(LogLevel.ALL)
                .build()

            realm = Realm.open(config)
        }
    }

    /**
     * Return all tournaments in the database
     */
    fun getAllTournaments(): List<Tournament> {
        val example = realm.query<Tournament>().find().first()
        val example2 = realm.query<Tournament>().limit(20).find()
        val example3 = realm.query<Tournament>().find()
        val example4 = realm.query<Tournament>("status == $0", "UPCOMING").find()
        println("TEST" + example)

        return example2
    }

    private fun deleteAllTournaments() {
        realm.writeBlocking {
            deleteAll()
        }
    }

    fun deleteRealm() {
        realm.close()
        Realm.deleteRealm(config)
    }

    /**
     * Add a tournament to the realm database and update existing one if the ids match
     */
    fun addTournament(tournament: Tournament) {
        // TODO ändern dass neues hinzukommt
        realm.writeBlocking {
            // Finde Turnier mit selber ID
            /*val tourney = query<Tournament>("id == $0", tournament.id).find().first()
            }*/
            copyToRealm(tournament)
        }
    }
}