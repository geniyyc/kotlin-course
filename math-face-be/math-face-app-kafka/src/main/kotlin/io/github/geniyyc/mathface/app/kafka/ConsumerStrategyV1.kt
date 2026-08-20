package io.github.geniyyc.mathface.app.kafka

import io.github.geniyyc.api.v1.apiV1RequestDeserialize
import io.github.geniyyc.api.v1.apiV1ResponseSerialize
import io.github.geniyyc.api.v1.models.IRequest
import io.github.geniyyc.api.v1.models.IResponse
import io.github.geniyyc.mathface.common.MfContext
import io.github.geniyyc.mathface.mappers.v1.fromTransport
import io.github.geniyyc.mathface.mappers.v1.toTransportExpression

class ConsumerStrategyV1 : IConsumerStrategy {
    override fun topics(config: AppKafkaConfig): InputOutputTopics {
        return InputOutputTopics(config.kafkaTopicInV1, config.kafkaTopicOutV1)
    }

    override fun serialize(source: MfContext): String {
        val response: IResponse = source.toTransportExpression()
        return apiV1ResponseSerialize(response)
    }

    override fun deserialize(value: String, target: MfContext) {
        val request: IRequest = apiV1RequestDeserialize(value)
        target.fromTransport(request)
    }
}
