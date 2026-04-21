pipeline {
    agent any
    
    parameters {
        choice(name: 'ENVIRONMENT', choices: ['dev', 'staging', 'production'], description: 'Deployment Environment')
        booleanParam(name: 'SKIP_TESTS', defaultValue: false, description: 'Skip tests')
        booleanParam(name: 'DEPLOY_BACKEND', defaultValue: true, description: 'Deploy backend services')
        booleanParam(name: 'DEPLOY_FRONTEND', defaultValue: true, description: 'Deploy frontend')
        booleanParam(name: 'RUN_ROLLBACK', defaultValue: false, description: 'Rollback to previous version')
    }
    
    tools {
        maven 'Maven-3.8'
        jdk 'JDK-17'
        nodejs 'NodeJS-18'
    }
    
    environment {
        PRODUCT_SERVICE_PORT = '8081'
        ORDER_SERVICE_PORT = '8082'
        USER_SERVICE_PORT = '8083'
        FRONTEND_PORT = '4200'
        BACKUP_DIR = '/opt/backups'
        DEPLOY_DIR = '/opt/ecommerce'
    }
    
    stages {
        stage('Checkout') {
            steps {
                script {
                    echo "Checking out code from repository..."
                    checkout scm
                    env.GIT_COMMIT_SHORT = sh(returnStdout: true, script: 'git rev-parse --short HEAD').trim()
                    env.BUILD_TIMESTAMP = sh(returnStdout: true, script: 'date +%Y%m%d-%H%M%S').trim()
                }
            }
        }
        
        stage('Rollback') {
            when {
                expression { params.RUN_ROLLBACK == true }
            }
            steps {
                script {
                    echo "Rolling back to previous version..."
                    sh '''
                        if [ -d "${BACKUP_DIR}/previous" ]; then
                            cp -r ${BACKUP_DIR}/previous/* ${DEPLOY_DIR}/
                            echo "Rollback completed successfully"
                        else
                            echo "No backup found for rollback"
                        fi
                    '''
                }
            }
        }
        
        stage('Build Backend') {
            when {
                expression { params.RUN_ROLLBACK == false }
            }
            parallel {
                stage('Build Product Service') {
                    agent any
                    steps {
                        dir('backend/product-service') {
                            sh 'mvn clean compile -DskipTests'
                        }
                    }
                }
                stage('Build Order Service') {
                    agent any
                    steps {
                        dir('backend/order-service') {
                            sh 'mvn clean compile -DskipTests'
                        }
                    }
                }
                stage('Build User Service') {
                    agent any
                    steps {
                        dir('backend/user-service') {
                            sh 'mvn clean compile -DskipTests'
                        }
                    }
                }
            }
        }
        
        stage('Test Backend') {
            when {
                expression { params.SKIP_TESTS == false && params.RUN_ROLLBACK == false }
            }
            parallel {
                stage('Test Product Service') {
                    agent any
                    steps {
                        dir('backend/product-service') {
                            sh 'mvn test'
                            junit '**/target/surefire-reports/*.xml'
                        }
                    }
                }
                stage('Test Order Service') {
                    agent any
                    steps {
                        dir('backend/order-service') {
                            sh 'mvn test'
                            junit '**/target/surefire-reports/*.xml'
                        }
                    }
                }
                stage('Test User Service') {
                    agent any
                    steps {
                        dir('backend/user-service') {
                            sh 'mvn test'
                            junit '**/target/surefire-reports/*.xml'
                        }
                    }
                }
            }
            post {
                failure {
                    echo "Backend tests failed! Pipeline will stop."
                    error("Backend tests failed")
                }
            }
        }
        
        stage('Build Frontend') {
            when {
                expression { params.RUN_ROLLBACK == false }
            }
            steps {
                dir('frontend') {
                    sh 'npm install'
                    sh 'npm run build -- --configuration=${ENVIRONMENT}'
                }
            }
        }
        
        stage('Test Frontend') {
            when {
                expression { params.SKIP_TESTS == false && params.RUN_ROLLBACK == false }
            }
            steps {
                dir('frontend') {
                    sh 'npm run test:ci'
                    sh 'npm run e2e:ci || true'
                    publishHTML([
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'coverage',
                        reportFiles: 'index.html',
                        reportName: 'Frontend Coverage Report'
                    ])
                }
            }
            post {
                failure {
                    echo "Frontend tests failed! Pipeline will stop."
                    error("Frontend tests failed")
                }
            }
        }
        
        stage('Package') {
            when {
                expression { params.RUN_ROLLBACK == false }
            }
            parallel {
                stage('Package Backend') {
                    steps {
                        script {
                            dir('backend/product-service') {
                                sh 'mvn package -DskipTests'
                            }
                            dir('backend/order-service') {
                                sh 'mvn package -DskipTests'
                            }
                            dir('backend/user-service') {
                                sh 'mvn package -DskipTests'
                            }
                        }
                    }
                }
                stage('Archive Frontend') {
                    steps {
                        dir('frontend/dist') {
                            sh 'tar -czf frontend-${BUILD_TIMESTAMP}.tar.gz *'
                        }
                    }
                }
            }
        }
        
        stage('Backup Current Version') {
            when {
                expression { params.RUN_ROLLBACK == false && params.ENVIRONMENT != 'dev' }
            }
            steps {
                script {
                    sh '''
                        mkdir -p ${BACKUP_DIR}/previous
                        if [ -d "${DEPLOY_DIR}" ]; then
                            cp -r ${DEPLOY_DIR}/* ${BACKUP_DIR}/previous/
                            echo "Backup completed"
                        fi
                    '''
                }
            }
        }
        
        stage('Deploy') {
            when {
                expression { params.RUN_ROLLBACK == false }
            }
            stages {
                stage('Deploy Backend') {
                    when {
                        expression { params.DEPLOY_BACKEND == true }
                    }
                    steps {
                        script {
                            echo "Deploying backend services to ${params.ENVIRONMENT}..."
                            sh '''
                                mkdir -p ${DEPLOY_DIR}/backend
                                cp backend/product-service/target/*.jar ${DEPLOY_DIR}/backend/product-service.jar
                                cp backend/order-service/target/*.jar ${DEPLOY_DIR}/backend/order-service.jar
                                cp backend/user-service/target/*.jar ${DEPLOY_DIR}/backend/user-service.jar
                                
                                # Restart services
                                pkill -f product-service.jar || true
                                pkill -f order-service.jar || true
                                pkill -f user-service.jar || true
                                
                                nohup java -jar ${DEPLOY_DIR}/backend/product-service.jar --server.port=${PRODUCT_SERVICE_PORT} > /dev/null 2>&1 &
                                nohup java -jar ${DEPLOY_DIR}/backend/order-service.jar --server.port=${ORDER_SERVICE_PORT} > /dev/null 2>&1 &
                                nohup java -jar ${DEPLOY_DIR}/backend/user-service.jar --server.port=${USER_SERVICE_PORT} > /dev/null 2>&1 &
                                
                                sleep 10
                            '''
                        }
                    }
                }
                
                stage('Deploy Frontend') {
                    when {
                        expression { params.DEPLOY_FRONTEND == true }
                    }
                    steps {
                        script {
                            echo "Deploying frontend to ${params.ENVIRONMENT}..."
                            sh '''
                                mkdir -p ${DEPLOY_DIR}/frontend
                                tar -xzf frontend/dist/frontend-${BUILD_TIMESTAMP}.tar.gz -C ${DEPLOY_DIR}/frontend/
                            '''
                        }
                    }
                }
                
                stage('Health Check') {
                    steps {
                        script {
                            echo "Performing health checks..."
                            def services = [
                                [name: 'Product Service', port: env.PRODUCT_SERVICE_PORT],
                                [name: 'Order Service', port: env.ORDER_SERVICE_PORT],
                                [name: 'User Service', port: env.USER_SERVICE_PORT]
                            ]
                            
                            services.each { service ->
                                retry(3) {
                                    sleep 5
                                    sh "curl -f http://localhost:${service.port}/actuator/health || exit 1"
                                    echo "${service.name} is healthy"
                                }
                            }
                        }
                    }
                }
            }
            post {
                failure {
                    script {
                        echo "Deployment failed! Initiating automatic rollback..."
                        sh '''
                            if [ -d "${BACKUP_DIR}/previous" ]; then
                                cp -r ${BACKUP_DIR}/previous/* ${DEPLOY_DIR}/
                                
                                # Restart services with previous version
                                pkill -f product-service.jar || true
                                pkill -f order-service.jar || true
                                pkill -f user-service.jar || true
                                
                                nohup java -jar ${DEPLOY_DIR}/backend/product-service.jar --server.port=${PRODUCT_SERVICE_PORT} > /dev/null 2>&1 &
                                nohup java -jar ${DEPLOY_DIR}/backend/order-service.jar --server.port=${ORDER_SERVICE_PORT} > /dev/null 2>&1 &
                                nohup java -jar ${DEPLOY_DIR}/backend/user-service.jar --server.port=${USER_SERVICE_PORT} > /dev/null 2>&1 &
                                
                                echo "Rollback completed"
                            fi
                        '''
                    }
                }
            }
        }
    }
    
    post {
        always {
            script {
                echo "Cleaning up workspace..."
                deleteDir()
            }
        }
        success {
            script {
                def message = """
                    ✅ Build #${BUILD_NUMBER} SUCCESSFUL
                    
                    Environment: ${params.ENVIRONMENT}
                    Commit: ${env.GIT_COMMIT_SHORT}
                    Duration: ${currentBuild.durationString}
                    
                    View details: ${env.BUILD_URL}
                """
                
                // Email notification
                emailext(
                    subject: "✅ Jenkins Build Success - ${JOB_NAME} #${BUILD_NUMBER}",
                    body: message,
                    to: 'your-email@gmail.com',
                    mimeType: 'text/plain'
                )
                
                // Slack notification (if configured)
                slackSend(
                    color: 'good',
                    message: message,
                    channel: '#jenkins-builds'
                )
            }
        }
        failure {
            script {
                def message = """
                    ❌ Build #${BUILD_NUMBER} FAILED
                    
                    Environment: ${params.ENVIRONMENT}
                    Commit: ${env.GIT_COMMIT_SHORT}
                    Duration: ${currentBuild.durationString}
                    
                    View details: ${env.BUILD_URL}console
                """
                
                // Email notification
                emailext(
                    subject: "❌ Jenkins Build Failed - ${JOB_NAME} #${BUILD_NUMBER}",
                    body: message,
                    to: 'your-email@gmail.com',
                    mimeType: 'text/plain'
                )
                
                // Slack notification (if configured)
                slackSend(
                    color: 'danger',
                    message: message,
                    channel: '#jenkins-builds'
                )
            }
        }
        unstable {
            script {
                def message = """
                    ⚠️ Build #${BUILD_NUMBER} UNSTABLE
                    
                    Environment: ${params.ENVIRONMENT}
                    Commit: ${env.GIT_COMMIT_SHORT}
                    Duration: ${currentBuild.durationString}
                    
                    View details: ${env.BUILD_URL}
                """
                
                emailext(
                    subject: "⚠️ Jenkins Build Unstable - ${JOB_NAME} #${BUILD_NUMBER}",
                    body: message,
                    to: '${DEFAULT_RECIPIENTS}',
                    mimeType: 'text/plain'
                )
            }
        }
    }
}
