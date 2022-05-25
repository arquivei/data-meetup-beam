package com.arquivei.pipeline.bq2gcs

import com.arquivei.pipeline.PipelineConfig
import com.arquivei.pipeline.SubjectTransformation
import com.spotify.scio.ScioContext
import com.spotify.scio.bigquery._

class PipelineFromBqToGcs(config: PipelineConfig) {

  def build(sc: ScioContext): Unit = {

    val extractQuery     = Query(config.bigQueryConfig.inputQuery)
    val transformSubject = SubjectTransformation()

    sc
      .withName("Read data from BigQuery")
      .typedBigQuery[BigQuery.Row](extractQuery)
      .withName("Extract Title from BigQuery Row")
      .map(row => row.title)
      .withName("Transform data")
      .map(transformSubject.filter)
      .withName("Send data to GCS")
      .saveAsTextFile(config.gcsConfig.outputPath)
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
