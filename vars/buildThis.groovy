#!/usr/bin/env groovy
def call() {
    //def minMajor = 2
    //def minMinor = 164
    def minVersion = [2, 164, 3]
    pipeline {
        agent none
        options {
            parallelsAlwaysFailFast()
            buildDiscarder logRotator(artifactNumToKeepStr: '10', daysToKeepStr: '15')
        }

      stages {
          stage('testing') {
              agent any
              steps {
                  script {
                      jenkinsVersion = sh returnStdout: true, script: 'mvn help:evaluate -Dexpression=jenkins.version -q -DforceStdout'
                      echo "jenkins version is: ${jenkinsVersion}"
                      /*
                      versionSegments = jenkinsVersion.tokenize('.')
                      if (versionSegments.size() > 2) {
                          if (versionSegments[0].toInteger() >= minMajor) {
                              if (versionSegments[1].toInteger() >= minMinor) {
                                  return true;
                              }
                          }
                      }
                      */
                      if (!enforceJenkinsVersion(jenkinsVersion, minVersion)) {
                          error ("Minimum jenkins version required for JDK11 is: ${minVersion[0]}.${minVersion[1]}.${minVersion[2]}. Current plugin jenkins version is: ${jenkinsVersion}")
                      }
                      //error ("Minimum jenkins version required for JDK11 is: ${minMajor}.${minMinor}. Current plugin jenkins version is: ${jenkinsVersion}")
                  }
              }
          }
      }
    }
}
