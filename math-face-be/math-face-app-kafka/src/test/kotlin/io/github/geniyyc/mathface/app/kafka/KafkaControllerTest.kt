package io.github.geniyyc.mathface.app.kafka

import io.github.geniyyc.api.v1.apiV1RequestSerialize
import io.github.geniyyc.api.v1.apiV1ResponseDeserialize
import io.github.geniyyc.api.v1.models.ExpressionDebug
import io.github.geniyyc.api.v1.models.ExpressionGenerateRequest
import io.github.geniyyc.api.v1.models.ExpressionGenerateResponse
import io.github.geniyyc.api.v1.models.ExpressionRequestDebugMode
import io.github.geniyyc.api.v1.models.ExpressionRequestDebugStubs
import io.github.geniyyc.api.v1.models.GenerateObject
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.MockConsumer
import org.apache.kafka.clients.consumer.OffsetResetStrategy
import org.apache.kafka.clients.producer.MockProducer
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class KafkaControllerTest {
    @Test
    fun runKafka() {
        val consumer = MockConsumer<String, String>(OffsetResetStrategy.EARLIEST)
        val producer = MockProducer<String, String>(true, StringSerializer(), StringSerializer())
        val config = AppKafkaConfig()
        val inputTopic = config.kafkaTopicInV1
        val outputTopic = config.kafkaTopicOutV1

        val app = AppKafkaConsumer(config, listOf(ConsumerStrategyV1()), consumer = consumer, producer = producer)
        consumer.schedulePollTask {
            consumer.rebalance(Collections.singletonList(TopicPartition(inputTopic, PARTITION)))
            consumer.addRecord(
                ConsumerRecord(
                    inputTopic,
                    PARTITION,
                    0L,
                    "test-1",
                    apiV1RequestSerialize(
                        ExpressionGenerateRequest(
                            expression = GenerateObject(level = 2),
                            debug = ExpressionDebug(
                                mode = ExpressionRequestDebugMode.STUB,
                                stub = ExpressionRequestDebugStubs.SUCCESS,
                            ),
                        ),
                    )
                )
            )
            app.close()
        }

        val startOffsets: MutableMap<TopicPartition, Long> = mutableMapOf()
        val tp = TopicPartition(inputTopic, PARTITION)
        startOffsets[tp] = 0L
        consumer.updateBeginningOffsets(startOffsets)

        app.start()

        val message = producer.history().first()
        val result = apiV1ResponseDeserialize<ExpressionGenerateResponse>(message.value())
        assertEquals(outputTopic, message.topic())
        assertNotNull(result.expressions)
        assertEquals(1, result.expressions!!.size)
    }

    companion object {
        const val PARTITION = 0
    }
}
