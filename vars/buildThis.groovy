#!/usr/bin/env groovy

def call() {
    pipeline {
        agent none
        options {
            parallelsAlwaysFailFast()
            buildDiscarder logRotator(artifactNumToKeepStr: '10', daysToKeepStr: '15')
        }

      stages {
          stage('JDK 11 - Linux') {
              agent {
                //   label 'docker-dedicated-jdk11 && linux'
                label 'maven-11'
              }

              steps {
                  withMaven(
                      maven: 'maven'//,
                    //   globalMavenSettingsConfig: 'nexus_plus_incrementals',
                    //   publisherStrategy: 'EXPLICIT',
                    //   options: [
                    //       junitPublisher(healthScaleFactor: 1.0),
                    //       spotbugsPublisher()
                    //   ]
                  ) {
                      when {
                          expression {
                              def JENKINS_VERSION = sh returnStdout: true, script: 'mvn help:evaluate -Dexpression=jenkins.version | grep -Eo "^[123456789]*\\..*"'.trim()
                              if (JENKINS_VERSION < 2.164) {
                                  error("Current Jenkins version is ${JENKINS_VERSION}. Minimum Jenkins version required for JDK 11 is 2.164.3-cb-2")
                              } else {
                                  return true
                              }
                              // return JENKINS_VERSION < 2.164
                          }
                      }
                      echo 'hey, you passed!'
                    //   sh """
                    //       mvn --show-version --batch-mode --no-transfer-progress --errors \
                    //           -Dconcurrency=1 -Dmaven.test.failure.ignore=true -Dfindbugs.failOnError=false -Djava.level=8\
                    //           clean verify
                    //   """
                  }
              }
          }
      }
    }
}
