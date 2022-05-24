package com.arquivei.pipeline.transform

import com.arquivei.pipeline.extract.BigQuery.Row

import java.util.UUID

case class Subject() {

  private val divider = ":"
  private val sports  = "sports"
  private val books   = "books"
  private val others  = "others"

  def filterFromBq(bqRow: Row): (String, String) = {
    filter(bqRow.title)
  }

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
