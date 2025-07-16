package com.brightside.configs

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import org.jetbrains.exposed.dao.id.EntityID
import java.math.BigDecimal
import java.time.Instant

// Serializer for EntityID (Serialize only)
object EntityIDSerializer : KSerializer<EntityID<Int>> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("EntityID", PrimitiveKind.INT)

    override fun serialize(encoder: Encoder, value: EntityID<Int>) {
        encoder.encodeInt(value.value)
    }

    override fun deserialize(decoder: Decoder): EntityID<Int> {
        error("Deserialization of EntityID is not supported")
    }
}

// Serializer for BigDecimal
object BigDecimalSerializer : KSerializer<BigDecimal> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("BigDecimal", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: BigDecimal) {
        encoder.encodeString(value.toPlainString())
    }

    override fun deserialize(decoder: Decoder): BigDecimal {
        return BigDecimal(decoder.decodeString())
    }
}

// Serializer for Instant
object InstantSerializer : KSerializer<Instant> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Instant", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Instant) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): Instant {
        return Instant.parse(decoder.decodeString())
    }
}

// Serializers module
val appSerializersModule = SerializersModule {
    contextual(EntityIDSerializer)
    contextual(BigDecimalSerializer)
    contextual(InstantSerializer)
}

// Optional for Ktor Application module use
fun configureSerialization() = appSerializersModule
