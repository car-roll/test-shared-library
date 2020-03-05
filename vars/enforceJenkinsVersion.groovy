#!/usr/bin/env groovy

def call(String jenkinsVersion, List<Integer> minVersion) {
    def minMajor = 2
    def minMinor = 164
    def minRev = 3
    // jenkinsVersion = sh returnStdout: true, script: 'mvn help:evaluate -Dexpression=jenkins.version -q -DforceStdout'
    // echo "jenkins version is: ${jenkinsVersion}"
    versionSegments = jenkinsVersion.tokenize('.')
    if (versionSegments.size() > 2) {
        rev = versionSegments[2].tokenize('-')[0].toInteger()
        if (versionSegments[0].toInteger() >= minVersion[0]) {
            if (versionSegments[1].toInteger() >= minVersion[2]) {
                if (rev >= minVersion[2]) {
                    return true
                }
            }
        }
    }
    return false
}
