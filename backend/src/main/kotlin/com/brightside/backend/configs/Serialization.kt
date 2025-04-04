package com.brightside.backend.configs

import io.ktor.server.application.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import org.jetbrains.exposed.dao.id.EntityID
import java.math.BigDecimal
import java.time.Instant

// EntityID serializer
object EntityIDSerializer : KSerializer<EntityID<Int>> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("EntityID", PrimitiveKind.INT)

    override fun serialize(encoder: Encoder, value: EntityID<Int>) {
        encoder.encodeInt(value.value)
    }

    override fun deserialize(decoder: Decoder): EntityID<Int> {
        return EntityID(decoder.decodeInt(), org.jetbrains.exposed.dao.id.IntIdTable("dummy"))
    }
}

// BigDecimal serializer
object BigDecimalSerializer : KSerializer<BigDecimal> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("BigDecimal", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: BigDecimal) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): BigDecimal {
        return BigDecimal(decoder.decodeString())
    }
}

// Instant serializer
object InstantSerializer : KSerializer<Instant> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Instant", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Instant) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): Instant {
        return Instant.parse(decoder.decodeString())
    }
}

// Create the serializers module
val appSerializersModule = SerializersModule {
    contextual(EntityIDSerializer)
    contextual(BigDecimalSerializer)
    contextual(InstantSerializer)
}

fun Application.configureSerialization(): SerializersModule {
    return appSerializersModule
}