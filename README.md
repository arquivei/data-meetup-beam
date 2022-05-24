# data-meetup-beam

Example of Apache Beam pipeline that was presented in Arquivei's Data Meetup

## How does it work?

The script file inside `/code/pipeline/scripts/run.sh` contains variables to process pipeline locally or in Dataflow.

- Locally process variables:

```bash
CONFIG_APACHEBEAM_RUNNER="direct"
CONFIG_APACHEBEAM_TEMPLOCATION="gs://data-meetup-beam/temp/templocation"
CONFIG_APACHEBEAM_STAGINGLOCATION="gs://data-meetup-beam/temp/staginglocation"

``` 
- Dataflow process variables:
```bash
CONFIG_APACHEBEAM_RUNNER="dataflow"
CONFIG_APACHEBEAM_JOBNAME="meetup-pipeline"
CONFIG_APACHEBEAM_REGION="us-central1"
CONFIG_APACHEBEAM_TEMPLOCATION="gs://data-meetup-beam/temp/templocation"
CONFIG_APACHEBEAM_STAGINGLOCATION="gs://data-meetup-beam/temp/templocation"
CONFIG_APACHEBEAM_NUMWORKERS="1"
CONFIG_APACHEBEAM_MAXNUMWORKERS="1"
CONFIG_APACHEBEAM_WORKERMACHINETYPE="n1-standard-2"
CONFIG_APACHEBEAM_EXPERIMENTS="shuffle_mode"
CONFIG_APACHEBEAM_PROJECT="<gcp_project_name>"
CONFIG_APACHEBEAM_NETWORK="main"
CONFIG_APACHEBEAM_SUBNETWORK="regions/us-central1/subnetworks/${CONFIG_APACHEBEAM_PROJECT}-main"

``` 

Inside `Main.scala` file in `/code/pipeline/src/main/scala/com/arquivei/pipeline` folder there are two pipeline options: *PipelineFromFileToGCS* and *PipelineFromBQToKafka*. 

- *PipelineFromFileToGCS* is responsible to read wikipedia titles file from GCS path, transform this data applying subject filter and save the output in another GCS location.

- *PipelineFromBQToKafka* is responsible to read wikepedia titles data from BigQuery table, transform this data applying subject filter and sending the output to Kafka topic.

The example file with wikipedia titles could be access in `/code/pipeline/example/wikipedia_titles.txt`

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


