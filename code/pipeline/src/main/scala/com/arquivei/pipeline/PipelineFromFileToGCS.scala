package com.arquivei.pipeline

import com.arquivei.pipeline.transform.Subject
import com.spotify.scio.ScioContext

class PipelineFromFileToGCS(config: PipelineConfig) {

  def build(sc: ScioContext): Unit = {

    val transformSubject = Subject()

    sc
      .withName("Read data from Local file")
      .textFile(config.gcsConfig.inputPath)
      .map(transformSubject.filter)
      .saveAsTextFile(config.gcsConfig.outputPath)
  }

}
