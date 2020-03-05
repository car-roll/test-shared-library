#!/usr/bin/env groovy
def call() {
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
                      jenkinsVersion = enforceJenkinsVersion()
                      echo "jenkins version is: ${jenkinsVersion}"
                  }
              }
          }
      }
    }
}
