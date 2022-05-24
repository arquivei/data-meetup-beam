package com.arquivei.pipeline

import com.arquivei.pipeline.extract.BigQuery
import com.arquivei.pipeline.load.Kafka
import com.arquivei.pipeline.transform.Subject
import com.spotify.scio.ScioContext
import com.spotify.scio.bigquery._
import com.spotify.scio.transforms.RateLimiterDoFn
import org.apache.beam.sdk.coders.KvCoder
import org.apache.beam.sdk.coders.StringUtf8Coder
import org.apache.beam.sdk.transforms.ParDo
import org.apache.beam.sdk.values.KV

class PipelineFromBQToKafka(config: PipelineConfig) {

  def build(sc: ScioContext): Unit = {

    val extractQuery     = Query(config.bigQueryConfig.inputQuery)
    val transformSubject = Subject()
    val load             = Kafka(config)

    sc
      .withName("Read data from BigQuery")
      .typedBigQuery[BigQuery.Row](extractQuery)
      .withName("Transform data")
      .map(transformSubject.filterFromBq)
      .withName("Generate KV for each encoded GenericMessage")
      .map(idAndMessage => KV.of(idAndMessage._1, idAndMessage._2))
      .applyTransform(ParDo.of(new RateLimiterDoFn[KV[String, String]](100)))
      .setCoder(KvCoder.of(StringUtf8Coder.of(), StringUtf8Coder.of()))
      .withName("Save to Kafka")
      .saveAsCustomOutput(
        "Save to kafka",
        load.client
      )

  }

}
