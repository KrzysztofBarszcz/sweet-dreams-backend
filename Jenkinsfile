properties([pipelineTriggers([githubPush()])])
pipeline {
    agent {
        docker {
            image 'gradle:5.5.1-jdk11'
        }
    }
    stages {
        stage('Build') {
            steps {
                sh './jenkins/scripts/prepare_project.sh'
            }
        }
        stage('Test') {
            steps {
                sh 'yarn run test'
            }
        }
    }
}
