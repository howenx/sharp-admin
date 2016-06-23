#!/usr/bin/env bash
./activator-1.3.10-minimal/bin/activator clean stage
echo '123123'|sudo -S kill -9 `sudo lsof -t -i:9002`
echo '123123'|sudo -S rm -rf target/universal/stage/RUNNING_PID
target/universal/stage/bin/style-admin -Dhttp.port=9002 -Dconfig.file=target/universal/stage/conf/application.conf -Dlogger.resource=logprod.xml
echo " - Finished Deploy"
