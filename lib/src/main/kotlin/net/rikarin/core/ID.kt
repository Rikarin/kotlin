package net.rikarin.core

import de.mkammerer.snowflakeid.SnowflakeIdGenerator
import de.mkammerer.snowflakeid.options.Options
import de.mkammerer.snowflakeid.structure.Structure
import de.mkammerer.snowflakeid.time.MonotonicTimeSource
import java.time.Instant

data class ID(val value: Long)

interface IdProvider {
    fun newId(): ID

    companion object {
        // Access global Instance from outside of DI
        var Instance: IdProvider? = null
    }
}

class DefaultIdProvider : IdProvider {
    private val _generator: SnowflakeIdGenerator

    init {
        val timeSource = MonotonicTimeSource(Instant.parse("2020-04-01T00:00:00Z"));

        // Use 45 bits for the timestamp, 2 bits for the generator and 16 bits for the sequence
        val structure = Structure(45, 2, 16);

        // If the sequence number overflows, throw an exception
        val options = Options(Options.SequenceOverflowStrategy.THROW_EXCEPTION);

        // generatorId must be unique over all your instances!
        val generatorId: Long = 1
        _generator = SnowflakeIdGenerator.createCustom(generatorId, timeSource, structure, options);

        IdProvider.Instance = this
    }

    override fun newId() = ID(_generator.next())
}

// TODO: refine this
data class IdConfiguration(val epoch: Instant, val generatorId: Int)