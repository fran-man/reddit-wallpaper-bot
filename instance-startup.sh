#!/bin/sh

# Set the metadata server to the get projct id
PROJECTID=$(curl -s "http://metadata.google.internal/computeMetadata/v1/project/project-id" -H "Metadata-Flavor: Google")
BUCKET=$(curl -s "http://metadata.google.internal/computeMetadata/v1/instance/attributes/BUCKET" -H "Metadata-Flavor: Google")

echo "Project ID: ${PROJECTID} Bucket: ${BUCKET}"

# Get the files we need
gsutil cp gs://${BUCKET}/rwb.jar .

# Install dependencies
apt-get update
apt-get -y --force-yes install openjdk-11-jre openjdk-11-jdk

# Make Java 11 default
update-alternatives --set java /usr/lib/jvm/java-11-openjdk-amd64/jre/bin/java

# Start server
java -jar rwb.jar