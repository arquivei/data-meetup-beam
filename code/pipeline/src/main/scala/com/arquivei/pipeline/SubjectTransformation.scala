package com.arquivei.pipeline

import com.arquivei.pipeline.bq2gcs.BigQuery.Row

import java.util.UUID

case class SubjectTransformation() {

  private val divider = ":"
  private val sports  = "sports"
  private val books   = "books"
  private val others  = "others"

  def filter(title: String): (String, String) = {

    val row = if (title.contains(sports)) {
      sports + divider + title
    } else if (title.contains(books)) {
      books + divider + title
    } else {
      others + divider + title
    }

    (UUID.randomUUID.toString, row)

  }
}
