SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
ROOT_DIR="$SCRIPT_DIR/../../.."
cd $ROOT_DIR

export CONFIG_APACHEBEAM_RUNNER=direct
export CONFIG_APACHEBEAM_TEMPLOCATION=gs://GCS_BUCKET/temp/templocation
export CONFIG_APACHEBEAM_STAGINGLOCATION=gs://GCS_BUCKET/temp/templocation

export CONFIG_GCSCONFIG_INPUTPATH=gs://GCS_BUCKET/input/wikipedia_titles.txt
export CONFIG_GCSCONFIG_OUTPUTPATH=gs://GCS_BUCKET/titles-subjects

export CONFIG_BIGQUERYCONFIG_INPUTQUERY='SELECT title from `bigquery-public-data.samples.wikipedia` LIMIT 10000'

export CONFIG_KAFKACONFIG_BOOTSTRAPSERVER=""
export CONFIG_KAFKACONFIG_USERNAME=""
export CONFIG_KAFKACONFIG_PASSWORD=""
export CONFIG_KAFKACONFIG_TOPICNAME=""

./gradlew :code:pipeline:run