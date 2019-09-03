# Demo using https://github.com/paxtonhare/demo-magic/
#!/usr/bin/env bash

########################
# include the magic
########################
. ./demo-magic.sh


########################
# Configure the options
########################

#
# speed at which to simulate typing. bigger num = faster
#
# TYPE_SPEED=20

#
# custom prompt
#
# see http://www.tldp.org/HOWTO/Bash-Prompt-HOWTO/bash-prompt-escape-sequences.html for escape sequences
#
#DEMO_PROMPT="${GREEN}➜ ${CYAN}\W "

# hide the evidence
clear

#pe "./clean.sh"

p "This is how I got my Fuse/Camel/SAP quickstart https://github.com/marcredhat/camelgradlespring to run in OpenShift 4.1 / CodeReady Containers"

pe "cd /root/podmancamelsap"

pe "more Dockerfile"

pe "sudo buildah bud -t camelsap ."

pe "sudo buildah images | grep camelsap"

p "podman  login -u=<username> -p=<encrypted password from quay.io Account Settings> quay.io"

pe "./loginquay.sh"

pe "podman push localhost/camelsap quay.io/marcf5/camelsap"

pe "more generatesapfusepod.yaml"

p "oc create secret docker-registry staging-secret     --docker-server=quay.io     --docker-username=<email>  --docker-password=<password>     --docker-email=<email>     --dry-run -o yaml > /root/podmancamelsap/staging-secret.yaml"

pe "oc create -f ./staging-secret.yaml"

pe "oc create -f ./generatesapfusepod.yaml"

# This volumes needs to have the SAP jars and SAPConnectionInfo.jcoDestination
# ls podmancamelsap/
# camelsapdemo-0.0.1-SNAPSHOT.jar  conversion.xml  libsapjco3.so  SAPConnectionInfo.jcoDestination  sapidoc3.jar  sapjco3.jar

# For example on how to test persistent storage, see https://github.com/marcredhat/crcdemos/blob/master/obsolete-crconrhel76.adoc

pe "oc set  volume pod/camelsap --add --name=camelsap --claim-name=sapdata --type pvc --claim-size=1G --mount-path /home/marc/sap"


p "Wait a few seconds..."

pe "oc logs  camelsap --follow"
# show a prompt so as not to reveal our true nature after
# the demo has concluded
p ""
