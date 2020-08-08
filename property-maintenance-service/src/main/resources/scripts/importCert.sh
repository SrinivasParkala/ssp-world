########################################################## {COPYRIGHT-TOP} #####
# Licensed Materials - Property of IBM
#
# 5725-E59
#
# (C) Copyright IBM Corp. 2020 All Rights Reserved.
#
# US Government Users Restricted Rights - Use, duplication, or
# disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
########################################################## {COPYRIGHT-END} #####
set -e

JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
DIR="$(CDPATH='' cd -- "$(dirname -- "$0")" && pwd -P)"

if [ -f "${DIR}"/oc.tmp ] ; then
    rm "${DIR}"/oc.tmp
fi

if [ -f "${DIR}"/oc.pem ] ; then
    rm "${DIR}"/oc.pem
fi

if [ -f "${DIR}"/oc.cer ] ; then
    rm "${DIR}"/oc.cer
fi

read -r -p  "Please provide physical hostname on which endpoint container resides : " oc_host
read -r -p  "Please provide external port number of the endpoint : " oc_port
printf "\nAcquiring certificate ...\n"

timeout 5 openssl s_client -showcerts -connect localhost:1025 -servername localhost >/dev/null 2>/dev/null </dev/null > oc.tmp
sed -ne '/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p' < oc.tmp > oc.pem
openssl x509 -inform PEM -in oc.pem -outform DER -out oc.cer </dev/null

timeout 5 openssl s_client -showcerts -connect "${oc_host}":"${oc_port}" -servername "${oc_host}" >/dev/null 2>/dev/null </dev/null > "${DIR}"/oc.tmp
sed -ne '/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p' < "${DIR}"/oc.tmp > "${DIR}"/oc.pem
openssl x509 -inform PEM -in "${DIR}"/oc.pem -outform DER -out "${DIR}"/oc.cer </dev/null

#path=${JAVA_HOME}/lib/security
#echo "Cacert path is:"${path}

#"${JAVA_HOME}"/bin/keytool -import -alias "${oc_host}""${oc_port}" -keystore  "${path}"/cacerts -file "${DIR}"/oc.cer
#keytool -import -alias "${oc_host}""${oc_port}" -keystore  /Library/Java/JavaVirtualMachines/jdk1.8.0_251.jdk/Contents/Home/jre/lib/security/cacerts -file "${DIR}"/oc.cer

rm "${DIR}"/oc.pem
rm "${DIR}"/oc.cer
rm "${DIR}"/oc.tmp

# openssl s_client -showcerts -connect genitor1.fyre.ibm.com:9443 -servername genitor1.fyre.ibm.com >/dev/null 2>/dev/null </dev/null > genitor1.tmp
# sed -ne '/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p' < genitor1.tmp > genitor1.pem
# openssl x509 -inform PEM -in genitor1.pem -outform DER -out genitor1.cer </dev/null
# sudo keytool -import -alias genitor1 -keystore /Library/Java/JavaVirtualMachines/jdk1.8.0_251.jdk/Contents/Home/jre/lib/security/cacerts -storepass changeit -file genitor1.cer
# openssl x509 -inform PEM -in falsie1.pem -outform DER -out falsie1.cer </dev/null