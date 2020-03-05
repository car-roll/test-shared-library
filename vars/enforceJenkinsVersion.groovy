#!/usr/bin/env groovy

def call() {
    def minVersionString = "2.164.3-cb-2"
    def minVersion = [2, 164, 3]
    def jenkinsVersion = sh returnStdout: true, script: 'mvn help:evaluate -Dexpression=jenkins.version -q -DforceStdout'
    versionSegments = jenkinsVersion.tokenize('.')
    if (versionSegments.size() > 2) {
        rev = versionSegments[2].tokenize('-')[0].toInteger()
        if (versionSegments[0].toInteger() >= minVersion[0]) {
            if (versionSegments[1].toInteger() >= minVersion[1]) {
                if (rev >= minVersion[2]) {
                    return jenkinsVersion
                }
            }
        }
    }
    return minVersionString
}
