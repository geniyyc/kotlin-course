package io.github.geniyyc.mathface.app.kafka

import io.github.geniyyc.mathface.common.MfContext

interface IConsumerStrategy {
    fun topics(config: AppKafkaConfig): InputOutputTopics
    fun serialize(source: MfContext): String
    fun deserialize(value: String, target: MfContext)
}
