pipeline {
    agent any

    tools {
        maven 'Maven-3.8'
        jdk 'JDK-11'
    }

    environment {
        NEXUS_URL = 'http://localhost:8081'
        NEXUS_DOCKER_REGISTRY = 'localhost:8082'
        NEXUS_CREDENTIALS = credentials('nexus-credentials')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Publish Artifacts to Nexus') {
            steps {
                sh 'mvn deploy -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("nexus-web-app:${env.BUILD_NUMBER}")
                }
            }
        }

        stage('Push Docker Image to Nexus') {
            steps {
                script {
                    docker.withRegistry("http://${NEXUS_DOCKER_REGISTRY}", 'nexus-credentials') {
                        def app = docker.image("nexus-web-app:${env.BUILD_NUMBER}")
                        app.push()
                        app.push('latest')
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
