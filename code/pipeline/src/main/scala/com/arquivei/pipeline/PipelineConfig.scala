package com.arquivei.pipeline

import com.arquivei.config.ApacheBeamArgs

case class PipelineConfig(
    apacheBeam: ApacheBeamArgs,
    kafkaConfig: KafkaConfig,
    bigQueryConfig: BigQueryConfig,
    gcsConfig: GCSConfig
)

case class GCSConfig(
    inputPath: String,
    outputPath: String
)

case class BigQueryConfig(
    inputQuery: String
)

case class KafkaConfig(
    bootstrapServer: String,
    username: String,
    password: String,
    topicName: String
)
