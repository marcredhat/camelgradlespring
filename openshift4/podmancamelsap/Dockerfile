FROM registry.access.redhat.com/ubi8/ubi-minimal:latest

# File Author / Maintainer
MAINTAINER Marc Chisinevski

# Copy inside the container
ADD /podmancamelsap /home/marc/sap

# Install Java:
RUN microdnf update -y &&  microdnf -y install java  && microdnf clean all

# Set the default directory where CMD will execute
WORKDIR /home/marc/sap

# Set the default command to execute
# when creating a new container
CMD java -Djava.library.path=/home/marc/sap -jar camelsapdemo-0.0.1-SNAPSHOT.jar
