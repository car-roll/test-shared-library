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
                          
                      jenkinsVersion = sh returnStdout: true, script: 'mvn help:evaluate -Dexpression=jenkins.version'
                      echo "jenkins version is: ${jenkinsVersion}"
                      }

              }
          }
      }
    }
}
