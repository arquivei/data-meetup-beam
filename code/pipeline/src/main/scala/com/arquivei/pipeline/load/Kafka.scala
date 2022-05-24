package com.arquivei.pipeline.load

import com.arquivei.pipeline.PipelineConfig
import org.apache.beam.sdk.io.kafka.KafkaIO
import org.apache.kafka.common.serialization.StringSerializer

import scala.jdk.CollectionConverters._

case class Kafka(config: PipelineConfig) {

  val kafkaProperties: Map[String, AnyRef] = Map(
    "bootstrap.servers" -> config.kafkaConfig.bootstrapServer,
    "sasl.jaas.config" -> s"""org.apache.kafka.common.security.plain.PlainLoginModule required username=\"${config.kafkaConfig.username}\" password=\"${config.kafkaConfig.password}\";""",
    "security.protocol" -> "SASL_PLAINTEXT",
    "sasl.mechanism"    -> "PLAIN"
  )

  def client: KafkaIO.Write[String, String] = {
    KafkaIO
      .write[String, String]()
      .withTopic(config.kafkaConfig.topicName)
      .withKeySerializer(classOf[StringSerializer])
      .withValueSerializer(classOf[StringSerializer])
      .withProducerConfigUpdates(kafkaProperties.asJava)
  }

}
