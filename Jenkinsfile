pipeline {
    agent any
    tools {
        maven 'maven_3_9_3'
    }
    stages {
        stage('Build Maven'){
            steps {
                checkout scmGit(branches: [[name: '*/develop']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/akshaypandare/employee-crud-service']])
                bat 'mvn clean install'
            }
        }
    }
}