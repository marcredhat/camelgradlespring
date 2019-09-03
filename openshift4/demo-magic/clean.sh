oc delete pod camelsap
oc delete pvc sapdata
oc delete -f staging-secret.yaml
oc delete -f /root/podmancamelsap/generatesapfusepod.yaml
