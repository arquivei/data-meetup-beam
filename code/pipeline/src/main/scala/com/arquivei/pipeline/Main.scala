package com.arquivei.pipeline

import com.arquivei.config._
import com.spotify.scio.ContextAndArgs

object Main {
  def main(args: Array[String]): Unit = {
    val config: PipelineConfig = ArquiveiConfig[PipelineConfig]()

    val (sc, _) = ContextAndArgs(config.apacheBeam.getArgsAsArray() ++ args)

    val pipeline = new PipelineFromFileToGCS(config)
//    val pipeline = new PipelineFromBQToKafka(config)

    pipeline.build(sc)
    sc.run().waitUntilDone()

  }
}
