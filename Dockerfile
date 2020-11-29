FROM jboss/wildfly:20.0.1.Final
COPY org /opt/jboss/wildfly/modules/org
COPY standalone.xml /opt/jboss/wildfly/standalone/configuration

ADD My-App-Docker/target/My-App-Docker.war /opt/jboss/wildfly/standalone/deployments/

RUN /opt/jboss/wildfly/bin/add-user.sh admin Admin#123 --silent


EXPOSE 9990
EXPOSE 8080

USER jboss
CMD [ "/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0" ]