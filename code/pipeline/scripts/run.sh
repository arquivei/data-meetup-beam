SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
ROOT_DIR="$SCRIPT_DIR/../../.."
cd $ROOT_DIR

# run local
export CONFIG_APACHEBEAM_RUNNER="direct"
export CONFIG_APACHEBEAM_TEMPLOCATION="gs://data-meetup-beam/temp/templocation"
export CONFIG_APACHEBEAM_STAGINGLOCATION="gs://data-meetup-beam/temp/staginglocation"

# run dataflow
# export CONFIG_APACHEBEAM_RUNNER=dataflow
# export CONFIG_APACHEBEAM_JOBNAME=meetup-pipeline
# export CONFIG_APACHEBEAM_REGION=us-central1
# export CONFIG_APACHEBEAM_TEMPLOCATION=gs://data-meetup-beam/temp/templocation
# export CONFIG_APACHEBEAM_STAGINGLOCATION=gs://data-meetup-beam/temp/templocation
# export CONFIG_APACHEBEAM_NUMWORKERS=1
# export CONFIG_APACHEBEAM_MAXNUMWORKERS=1
# export CONFIG_APACHEBEAM_WORKERMACHINETYPE=n1-standard-2
# export CONFIG_APACHEBEAM_EXPERIMENTS=shuffle_mode
# export CONFIG_APACHEBEAM_PROJECT=<gcp_project>
# export CONFIG_APACHEBEAM_NETWORK=main
# export CONFIG_APACHEBEAM_SUBNETWORK=regions/us-central1/subnetworks/${CONFIG_APACHEBEAM_PROJECT}-main

export CONFIG_GCSCONFIG_INPUTPATH=gs://data-meetup-beam/input/wikipedia_titles.txt
export CONFIG_GCSCONFIG_OUTPUTPATH=gs://data-meetup-beam/titles-subjects

export CONFIG_BIGQUERYCONFIG_INPUTQUERY="SELECT title from bigquery-public-data.samples.wikipedia LIMIT 10000"

export CONFIG_KAFKACONFIG_BOOTSTRAPSERVER="localhost:9093"
export CONFIG_KAFKACONFIG_USERNAME="stark"
export CONFIG_KAFKACONFIG_PASSWORD="stark"
export CONFIG_KAFKACONFIG_TOPICNAME="com.arquivei.pipeline"

./gradlew :code:pipeline:run