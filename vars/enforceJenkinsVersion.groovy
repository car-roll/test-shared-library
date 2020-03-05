#!/usr/bin/env groovy

def call(String jenkinsVersion, List<Integer> minVersion) {
    echo "jv: ${jenkinsVersion}"
    echo "mv: ${minVersion}"
    versionSegments = jenkinsVersion.tokenize('.')
    if (versionSegments.size() > 2) {
        rev = versionSegments[2].tokenize('-')[0].toInteger()
        if (versionSegments[0].toInteger() >= minVersion[0]) {
            if (versionSegments[1].toInteger() >= minVersion[1]) {
                if (rev >= minVersion[2]) {
                    return true
                }
            }
        }
    }
    return false
}
