pipeline {
	agent any

	environment {
		mavenHome = tool 'maven-3.6.3'
	}

	tools {
		jdk 'JDK-11.0.2'
	}

	stages {

		stage('build'){
			steps {
				bat "mvn clean install -DskipTests"
			}
		}

		stage('test'){
			steps{
				bat "mvn test"
			}
		}

		stage('deploy') {
			steps {
			    bat "mvn jar:jar deploy:deploy"
			}
		}
	}
}