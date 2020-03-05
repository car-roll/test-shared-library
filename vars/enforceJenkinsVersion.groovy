#!/usr/bin/env groovy

def call() {
    def minMajor = 2
    def minMinor = 164
    def minRev = 3
    jenkinsVersion = sh returnStdout: true, script: 'mvn help:evaluate -Dexpression=jenkins.version -q -DforceStdout'
    echo "jenkins version is: ${jenkinsVersion}"
    versionSegments = jenkinsVersion.tokenize('.')
    if (versionSegments.size() > 2) {
        rev = versionSegments[2].tokenize('-').toInteger()
        if (versionSegments[0].toInteger() >= minMajor) {
            if (versionSegments[1].toInteger() >= minMinor) {
                if (rev >= minRev) {
                    return true
                }
            }
        }
    }
    return false
}
