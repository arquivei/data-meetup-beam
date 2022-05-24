package com.arquivei.config

/** The case class ApacheBeamArgs provides the most common Apache Beam args in a case class.
  *
  * The case class accepts any arguments via extraArgs argument.
  * Expected format of extraArgs (mainly used in Scio Tests):
  *    Array("--config1=value1", "--config2=value2", ..., "--configN=valueN")
  */
case class ApacheBeamArgs(
    runner: Option[String] = None,
    project: Option[String] = None,
    jobName: Option[String] = None,
    zone: Option[String] = None,
    region: Option[String] = None,
    network: Option[String] = None,
    subnetwork: Option[String] = None,
    usePublicIps: Option[String] = None,
    serviceAccount: Option[String] = None,
    templateLocation: Option[String] = None,
    stagingLocation: Option[String] = None,
    tempLocation: Option[String] = None,
    gcpTempLocation: Option[String] = None,
    numWorkers: Option[String] = None,
    maxNumWorkers: Option[String] = None,
    workerMachineType: Option[String] = None,
    diskSizeGb: Option[String] = None,
    autoscalingAlgorithm: Option[String] = None,
    labels: Option[String] = None,
    numberOfWorkerHarnessThreads: Option[String] = None,
    workerLogLevelOverrides: Option[String] = None,
    workerHarnessContainerImage: Option[String] = None,
    experiments: Option[String] = None,
    transformNameMapping: Option[String] = None
) {

  /** Apache Beam via Scio requires a configuration passed as an array of Strings in the format
    * Array("--config1=value1", "--config2=value2", ..., "--configN=valueN")
    *
    * This function converts the apacheBeam args case class instance to the list of arguments, merging with the
    * optional extraArgs
    *
    * @return List of arguments for Apache Beam
    */
  def getArgsAsArray(extraArgs: Array[String] = Array()): Array[String] = {
    Array(
      runner.map("--runner=" + _),
      project.map("--project=" + _),
      jobName.map("--jobName=" + _),
      zone.map("--zone=" + _),
      region.map("--region=" + _),
      network.map("--network=" + _),
      subnetwork.map("--subnetwork=" + _),
      usePublicIps.map("--usePublicIps=" + _),
      serviceAccount.map("--serviceAccount=" + _),
      templateLocation.map("--templateLocation=" + _),
      stagingLocation.map("--stagingLocation=" + _),
      tempLocation.map("--tempLocation=" + _),
      gcpTempLocation.map("--gcpTempLocation=" + _),
      numWorkers.map("--numWorkers=" + _),
      maxNumWorkers.map("--maxNumWorkers=" + _),
      workerMachineType.map("--workerMachineType=" + _),
      diskSizeGb.map("--diskSizeGb=" + _),
      autoscalingAlgorithm.map("--autoscalingAlgorithm=" + _),
      labels.map("--labels=" + _),
      numberOfWorkerHarnessThreads.map("--numberOfWorkerHarnessThreads=" + _),
      workerLogLevelOverrides.map("--workerLogLevelOverrides=" + _),
      workerHarnessContainerImage.map("--workerHarnessContainerImage=" + _),
      experiments.map("--experiments=" + _),
      transformNameMapping.map("--transformNameMapping=" + _)
    ).flatten ++ extraArgs
  }
}
