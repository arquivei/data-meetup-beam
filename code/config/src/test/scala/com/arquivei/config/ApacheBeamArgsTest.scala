package com.arquivei.config

import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ApacheBeamArgsTest extends AnyFlatSpec with Matchers {
  "getArgsAsArray" should "correctly return the Beam's string array with all args" in {
    val extraArgs = Array("--newarg1=newarg1", "--newarg2=newarg2")
    val beamArgs = ApacheBeamArgs(
      runner = Some("runner"),
      project = Some("project"),
      jobName = Some("jobName"),
      zone = Some("zone"),
      region = Some("region"),
      network = Some("network"),
      subnetwork = Some("subnetwork"),
      usePublicIps = Some("usePublicIps"),
      stagingLocation = Some("stagingLocation"),
      tempLocation = Some("tempLocation"),
      numWorkers = Some("numWorkers"),
      maxNumWorkers = Some("maxNumWorkers"),
      workerMachineType = Some("workerMachineType"),
      diskSizeGb = Some("diskSizeGb"),
      autoscalingAlgorithm = Some("autoscalingAlgorithm"),
      labels = Some("labels"),
      numberOfWorkerHarnessThreads = Some("numberOfWorkerHarnessThreads"),
      workerLogLevelOverrides = Some("workerLogLevelOverrides"),
      workerHarnessContainerImage = Some("workerHarnessContainerImage"),
      experiments = Some("experiments"),
      transformNameMapping = Some("transformNameMapping")
    )

    val expectedArgs = Array(
      "--runner=runner",
      "--project=project",
      "--jobName=jobName",
      "--zone=zone",
      "--region=region",
      "--network=network",
      "--subnetwork=subnetwork",
      "--usePublicIps=usePublicIps",
      "--stagingLocation=stagingLocation",
      "--tempLocation=tempLocation",
      "--numWorkers=numWorkers",
      "--maxNumWorkers=maxNumWorkers",
      "--workerMachineType=workerMachineType",
      "--diskSizeGb=diskSizeGb",
      "--autoscalingAlgorithm=autoscalingAlgorithm",
      "--labels=labels",
      "--numberOfWorkerHarnessThreads=numberOfWorkerHarnessThreads",
      "--workerLogLevelOverrides=workerLogLevelOverrides",
      "--workerHarnessContainerImage=workerHarnessContainerImage",
      "--experiments=experiments",
      "--transformNameMapping=transformNameMapping",
      "--newarg1=newarg1",
      "--newarg2=newarg2"
    )
    beamArgs.getArgsAsArray(extraArgs) shouldBe expectedArgs
  }

  it should "correctly get Beam arguments as Array of strings if just some of them are set" in {
    val beamArgs = ApacheBeamArgs(
      stagingLocation = Some("stagingLocation")
    )

    val expectedArgs = Array(
      "--stagingLocation=stagingLocation"
    )
    beamArgs.getArgsAsArray() shouldBe expectedArgs
  }
}
