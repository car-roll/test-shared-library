#!/usr/bin/env groov

def call() {
    def minMajor = 2
    def minMinor = 164
    jenkinsVersion = sh returnStdout: true, script: 'mvn help:evaluate -Dexpression=jenkins.version -q -DforceStdout'
    echo "jenkins version is: ${jenkinsVersion}"
    versionSegments = jenkinsVersion.tokenize('.')
    if (versionSegments.size() > 2) {
        if (versionSegments[0] >= minMajor) {
            if (versionSegments[1] >= minMinor) {
                return true
            }
        }
    }
    return false
}
