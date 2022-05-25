package com.arquivei.pipeline.bq2kafka

import com.arquivei.pipeline.PipelineConfig
import com.arquivei.pipeline.SubjectTransformation
import com.spotify.scio.ScioContext
import com.spotify.scio.bigquery._
import com.spotify.scio.transforms.RateLimiterDoFn
import org.apache.beam.sdk.coders.KvCoder
import org.apache.beam.sdk.coders.StringUtf8Coder
import org.apache.beam.sdk.transforms.ParDo
import org.apache.beam.sdk.values.KV

class PipelineFromBqToKafka(config: PipelineConfig) {

  def build(sc: ScioContext): Unit = {

    val extractQuery     = Query(config.bigQueryConfig.inputQuery)
    val transformSubject = SubjectTransformation()
    val load             = Kafka(config)

    sc
      .withName("Read data from BigQuery")
      .typedBigQuery[BigQuery.Row](extractQuery)
      .withName("Extract Title from BigQuery Row")
      .map(row => row.title)
      .withName("Transform data")
      .map(transformSubject.filter)
      .withName("Generate KV for each encoded GenericMessage")
      .map(idAndMessage => KV.of(idAndMessage._1, idAndMessage._2))
      .applyTransform(ParDo.of(new RateLimiterDoFn[KV[String, String]](100)))
      .setCoder(KvCoder.of(StringUtf8Coder.of(), StringUtf8Coder.of()))
      .withName("Send data to Kafka")
      .saveAsCustomOutput(
        "KafkaIO",
        load.client
      )

  }

}

object BigQuery {
  @BigQueryType.toTable
  /** Row is an output data from BigQuery
    * @param title The title of the page, as displayed on the page
    */
  case class Row(
      title: String
  )
}
