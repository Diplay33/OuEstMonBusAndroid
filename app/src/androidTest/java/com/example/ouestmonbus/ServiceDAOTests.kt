package com.example.ouestmonbus

import com.google.common.truth.Truth.assertThat
import com.google.transit.realtime.GtfsRealtime
import com.google.transit.realtime.GtfsRealtime.FeedMessage
import io.mockk.every
import io.mockk.mockkObject
import model.DAO.ServiceDAO
import model.DTO.Destinations
import model.DTO.Service
import model.helpers.toASCIIDecimal
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class ServiceDAOTests {
    private fun feedEntity(
        entityId: String,
        routeId: String?,
        tripId: String?,
        vehicleLabel: String? = null,
        vehicleId: String? = null,
        lat: Float = 48f,
        lon: Float = 2f,
        speed: Float? = 12f,
        bearing: Float? = 90f,
        stopId: String = "STOP"
    ): GtfsRealtime.FeedEntity {
        val trip = GtfsRealtime.TripDescriptor.newBuilder().apply {
            if (routeId != null) setRouteId(routeId)
            if (tripId  != null) setTripId(tripId)
        }.build()

        val pos = GtfsRealtime.Position.newBuilder().apply {
            latitude = lat
            longitude = lon
            if (speed != null) this.speed = speed
            if (bearing != null) this.bearing = bearing
        }.build()

        val vehDesc = GtfsRealtime.VehicleDescriptor.newBuilder().apply {
            if (vehicleId != null) id = vehicleId
            if (vehicleLabel != null) label = vehicleLabel
        }.build()

        val veh = GtfsRealtime.VehiclePosition.newBuilder()
            .setTrip(trip)
            .setPosition(pos)
            .setStopId(stopId)
            .setVehicle(vehDesc)
            .build()

        return GtfsRealtime.FeedEntity.newBuilder()
            .setId(entityId)
            .setVehicle(veh)
            .build()
    }

    private fun feedEntityNoVehicle(entityId: String = "X"): GtfsRealtime.FeedEntity =
        GtfsRealtime.FeedEntity.newBuilder()
            .setId(entityId)
            .build()

    private fun feed(vararg entities: GtfsRealtime.FeedEntity): FeedMessage {
        val header = GtfsRealtime.FeedHeader.newBuilder()
            .setGtfsRealtimeVersion("2.0")
            .build()
        val b = FeedMessage.newBuilder().setHeader(header)
        entities.forEach { b.addEntity(it) }
        return b.build()
    }

    private fun runRaw(
        feed: FeedMessage,
        network: String,
        lineId: Int? = null,
        trips: Map<String, String> = emptyMap()
    ): List<Service> {
        val latch = CountDownLatch(1)
        var out: List<Service> = emptyList()
        ServiceDAO.rawGTFSToServices(
            feedMessage = feed,
            network = network,
            lineId = lineId,
            callback = {
                out = it
                latch.countDown()
            },
            trips = trips
        )
        latch.await(3, TimeUnit.SECONDS)
        return out
    }

    @Before
    fun setUp() {
        mockkObject(Destinations)
        every { Destinations.getTripHeadsigns(any(), any(), any()) } answers {
            val ids = firstArg<List<String>>()
            val result = ids.map { id -> mapOf("trip_id" to id, "trip_headsign" to "HS_$id") }
            thirdArg<(List<Map<String, String>>) -> Unit>().invoke(result)
        }
    }

    @After fun tearDown() { }

    @Test
    fun emptyWhenRawNilOrNoVehicle() {
        val emptyFeed = feed()
        val r1 = runRaw(emptyFeed, network = "vib")
        assertThat(r1).isEmpty()

        val f2 = feed(feedEntityNoVehicle())
        val r2 = runRaw(f2, network = "vib")
        assertThat(r2).isEmpty()
    }

    @Test
    fun defaultNetworkFilterByLineNumericRouteId() {
        val e = feedEntity(entityId = "10", routeId = "12", tripId = "T1", vehicleLabel = "42")
        val res = runRaw(feed(e), network = "vib", lineId = 12)
        assertThat(res).hasSize(1)
        assertThat(res[0].lineId).isEqualTo(12)
        assertThat(res[0].destination).isEqualTo("HS_T1")
    }

    @Test
    fun starFirstTwoCharsDropped() {
        val e = feedEntity(entityId = "1", routeId = "AB12", tripId = "T2")
        val res = runRaw(feed(e), network = "star", lineId = 12)
        assertThat(res).hasSize(1)
        assertThat(res[0].lineId).isEqualTo(12)
    }

    @Test
    fun kiceoMOBICEOMapsTo999AndSAEIVNormalization() {
        val mobiceo = feedEntity(entityId = "1", routeId = "MOBICEO",    tripId = "T3")
        val other   = feedEntity(entityId = "2", routeId = "SAEIV-04.A", tripId = "T4")
        val f = feed(mobiceo, other)

        val r1 = runRaw(f, network = "kiceo", lineId = 999)
        assertThat(r1).hasSize(1)
        assertThat(r1[0].lineId).isEqualTo(999)

        val expected = "SAEIV-04.A"
            .filter { !it.isWhitespace() && it.isLetterOrDigit() }
            .replace("SAEIV", "")
            .toASCIIDecimal()

        val r2 = runRaw(feed(other), network = "kiceo", lineId = expected)
        assertThat(r2).hasSize(1)
        assertThat(r2[0].lineId).isEqualTo(expected)
    }

    @Test
    fun filbleuUsesTripsMappingForLineId() {
        val e = feedEntity(entityId = "1", routeId = "IGNORED", tripId = "trip-123")
        val trips = mapOf("trip-123" to "TTR:LINE:5")
        val res = runRaw(feed(e), network = "filbleu", lineId = 5, trips = trips)
        assertThat(res).hasSize(1)
        assertThat(res[0].lineId).isEqualTo(5)
    }

    @Test
    fun tbkRouteIdRemoveLast4() {
        val route = "15SAEIV" // dropLast(4) -> "15S"
        val expected = route.dropLast(4).toASCIIDecimal()
        val e = feedEntity(entityId = "1", routeId = route, tripId = "T7")
        val r = runRaw(feed(e), network = "tbk", lineId = expected)
        assertThat(r).hasSize(1)
        assertThat(r[0].lineId).isEqualTo(expected)
    }

    @Test
    fun filterByLinesMultiple() {
        val e1 = feedEntity(entityId = "1", routeId = "10", tripId = "A")
        val e2 = feedEntity(entityId = "2", routeId = "20", tripId = "B")
        val e3 = feedEntity(entityId = "3", routeId = "30", tripId = "C")
        val f = feed(e1, e2, e3)

        val r20 = runRaw(f, network = "vib", lineId = 20)
        val r30 = runRaw(f, network = "vib", lineId = 30)

        assertThat(r20.map { it.lineId }).containsExactly(20)
        assertThat(r30.map { it.lineId }).containsExactly(30)
    }

    @Test
    fun tripIdNormalizationAffectsHeadsignLookup() {
        val captured = mutableListOf<List<String>>()

        every { Destinations.getTripHeadsigns(any(), any(), any()) } answers {
            val ids = firstArg<List<String>>()
            captured += ids
            val result = ids.map { id -> mapOf("trip_id" to id, "trip_headsign" to "HS_$id") }
            thirdArg<(List<Map<String, String>>) -> Unit>().invoke(result)
        }

        runRaw(feed(feedEntity("1","1","TRIP_TAM")) , network = "tam")
        runRaw(feed(feedEntity("2","2","XYZ123"))  , network = "tango")
        runRaw(feed(feedEntity("3","3","SURF_001")), network = "surf")

        assertThat(captured.flatten()).isNotEmpty()
    }

    @Test
    fun noMatchReturnsEmpty() {
        val e = feedEntity(entityId = "X", routeId = "99", tripId = "T99")
        val res = runRaw(feed(e), network = "vib", lineId = 123)
        assertThat(res).isEmpty()
    }

    @Test
    fun headsignFallbackWhenNotFound() {
        every { Destinations.getTripHeadsigns(any(), any(), any()) } answers {
            thirdArg<(List<Map<String, String>>) -> Unit>().invoke(emptyList())
        }
        val e = feedEntity(entityId = "1", routeId = "12", tripId = "T1")
        val res = runRaw(feed(e), network = "vib", lineId = 12)
        assertThat(res).hasSize(1)
        assertThat(res[0].destination).isEqualTo("Destination inconnue")
    }
}