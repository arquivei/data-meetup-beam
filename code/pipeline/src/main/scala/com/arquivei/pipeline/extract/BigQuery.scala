package com.arquivei.pipeline.extract

import com.spotify.scio.bigquery.BigQueryType

object BigQuery {
  @BigQueryType.toTable
  /** Row is an output data from BigQuery
    * @param title The title of the page, as displayed on the page
    */
  case class Row(
      title: String
  )
}
