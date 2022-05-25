package com.arquivei.pipeline.gcs2gcs

import com.arquivei.pipeline.PipelineConfig
import com.arquivei.pipeline.SubjectTransformation
import com.spotify.scio.ScioContext

class PipelineFromGCSToGCS(config: PipelineConfig) {

  def build(sc: ScioContext): Unit = {

    val transformSubject = SubjectTransformation()

    sc
      .withName("Read data from GCS file")
      .textFile(config.gcsConfig.inputPath)
      .withName("Transform data")
      .map(transformSubject.filter)
      .withName("Send data to GCS")
      .saveAsTextFile(config.gcsConfig.outputPath)
  }

}
