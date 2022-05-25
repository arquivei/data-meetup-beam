# data-meetup-beam

Example of Apache Beam pipeline that was presented in Arquivei's Data Meetup

## How does it work?

### Configuration

The script files inside `/code/pipeline/scripts` contains all variables used to process pipeline locally or in Dataflow.

- Locally process variables - `run_direct.sh` - need to change GCS_BUCKET:

```bash
CONFIG_APACHEBEAM_RUNNER=direct
CONFIG_APACHEBEAM_TEMPLOCATION=gs://GCS_BUCKET/templocation
CONFIG_APACHEBEAM_STAGINGLOCATION=gs://GCS_BUCKET/staginglocation

```
- Dataflow process variables - `run_dataflow.sh` - need to change GCS_BUCKET and GCP_PROJECT:
```bash
CONFIG_APACHEBEAM_RUNNER=dataflow
CONFIG_APACHEBEAM_JOBNAME=meetup-pipeline
CONFIG_APACHEBEAM_REGION=us-central1
CONFIG_APACHEBEAM_TEMPLOCATION=gs://GCS_BUCKET/templocation
CONFIG_APACHEBEAM_STAGINGLOCATION=gs://GCS_BUCKET/staginglocation
CONFIG_APACHEBEAM_NUMWORKERS=1
CONFIG_APACHEBEAM_MAXNUMWORKERS=1
CONFIG_APACHEBEAM_WORKERMACHINETYPE=n1-standard-2
CONFIG_APACHEBEAM_EXPERIMENTS=shuffle_mode
CONFIG_APACHEBEAM_PROJECT=GCP_PROJECT
CONFIG_APACHEBEAM_NETWORK=main
CONFIG_APACHEBEAM_SUBNETWORK=regions/us-central1/subnetworks/${CONFIG_APACHEBEAM_PROJECT}-main

``` 

Inside `Main.scala` file in `/code/pipeline/src/main/scala/com/arquivei/pipeline` folder there are three pipeline options that you nedd to choose and uncomment just ONE:

```bash
// pipeline from GCS to GCS
// val pipeline = new PipelineFromGCSToGCS(config)

// pipeline from BigQuery to GCS
// val pipeline = new PipelineFromBqToGcs(config)

// pipeline from BigQuery to Kafka
// val pipeline = new PipelineFromBqToKafka(config)
```

- *PipelineFromGCSToGCS* is responsible to read wikipedia titles file from GCS path, transform this data applying subject filter and save the output in another GCS location.

- *PipelineFromBqToGcs* is responsible to read wikepedia titles data from BigQuery table, transform this data applying subject filter and save the output in GCS bucket.

- *PipelineFromBqToKafka* is responsible to read wikepedia titles data from BigQuery table, transform this data applying subject filter and sending the output to Kafka topic.

The example file with wikipedia titles could be access in `/code/pipeline/example/wikipedia_titles.txt` and send to your GCS bucket with the following command from this repository root path: 

```bash
gsutil cp code/pipeline/example/wikipedia_titles.txt gs://GCS_BUCKET/input/
```

### Running

- Locally process - `run_direct.sh`:

```bash
sh code/pipeline/scripts/run_direct.sh 
```
- Dataflow process - `run_dataflow.sh`:
```bash
sh code/pipeline/scripts/run_dataflow.sh
``` 

## Setting local environment with Scala + Gradle + Intellij in Ubuntu

- Intellij install 
```bash
sudo snap install intellij-idea-community --classic
```
- Scala install with sdkman
```bash
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk install scala 2.13.7
```
- Gradle install
```bash
sudo apt install openjdk-11-jdk
wget https://services.gradle.org/distributions/gradle-7.0.2-bin.zip -P /tmp
sudo unzip -d /opt/gradle /tmp/gradle-7.0.2-bin.zip
sudo ln -s /opt/gradle/gradle-7.0.2/opt/gradle/latest
```
- Set local environment to enable gradle

1. run `sudo gedit /etc/profile.d/gradle.sh`
2. paste the following command inside the opened file and save
```bash
export GRADLE_HOME=/opt/gradle/latest
export PATH=${GRADLE_HOME}/bin:${PATH}
```
3. run `sudo chmod +x /etc/profile.d/gradle.sh && source /etc/profile.d/gradle.sh`


