package com.arquivei.pipeline

import com.arquivei.config._
import com.arquivei.pipeline.bq2gcs._
import com.arquivei.pipeline.bq2kafka._
import com.arquivei.pipeline.gcs2gcs._
import com.spotify.scio.ContextAndArgs

object Main {
  def main(args: Array[String]): Unit = {

    val config: PipelineConfig = ArquiveiConfig[PipelineConfig]()
    val (sc, _)                = ContextAndArgs(config.apacheBeam.getArgsAsArray() ++ args)

    // pipeline from GCS to GCS
    val pipeline = new PipelineFromGCSToGCS(config)

    // pipeline from BigQuery to GCS
//    val pipeline = new PipelineFromBqToGcs(config)

    // pipeline from BigQuery to Kafka
//    val pipeline = new PipelineFromBqToKafka(config)

    pipeline.build(sc)
    sc.run().waitUntilDone()

  }
}
