pipeline {
    agent any

    tools {
        maven 'Maven'
    }
    
    parameters {
        choice(
            name: 'SUITE_FILE',
            choices: [
                'testng.xml',
                'testng-user.xml',
                'testng-pet.xml',
                'testng-order.xml',
                'testng-regression.xml'
            ],
            description: 'Choose the TestNG suite file to run'
        )

        choice(
            name: 'ENV',
            choices: ['qa', 'dev'],
            description: 'Environment'
        )
    }

    triggers {
        cron('H 1 * * *')
    }

    options {
        timestamps()
        disableConcurrentBuilds()
        buildDiscarder(logRotator(numToKeepStr: '15'))
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Run API Tests') {
            steps {
                bat """
                    mvn clean test ^
                    -Dsurefire.suiteXmlFiles=%SUITE_FILE% ^
                    -Denv=%ENV%
                """
            }
        }

        stage('Publish Reports') {
            steps {
                junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true

                publishHTML(target: [
                    allowMissing: true,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'reports',
                    reportFiles: 'ExtentReport.html',
                    reportName: 'Extent Report',
                    reportTitles: 'API Automation Execution Report'
                ])
            }
        }

        stage('Build Links') {
            steps {
                script {
                    def junitLink = "${env.BUILD_URL}testReport/"
                    def extentLink = "${env.BUILD_URL}Extent_Report/"

                    currentBuild.description = """
                        Suite: ${params.SUITE_FILE}<br/>
                        <a href='${junitLink}'>JUnit/TestNG Results</a><br/>
                        <a href='${extentLink}'>Extent Report</a>
                    """

                    echo "JUnit/TestNG Results: ${junitLink}"
                    echo "Extent Report: ${extentLink}"
                }
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'reports/**/*, target/surefire-reports/**/*', allowEmptyArchive: true, fingerprint: true
        }

        success {
            echo "Build succeeded: ${env.BUILD_URL}"
            echo "Test Results: ${env.BUILD_URL}testReport/"
            echo "Extent Report: ${env.BUILD_URL}Extent_Report/"
        }

        failure {
            echo "Build failed: ${env.BUILD_URL}console"
            echo "Test Results: ${env.BUILD_URL}testReport/"
            echo "Extent Report: ${env.BUILD_URL}Extent_Report/"
        }
    }
}
