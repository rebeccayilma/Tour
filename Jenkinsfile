pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        bat 'git checkout jenkins'
        bat 'gradlew build'
      }
    }

    stage('test') {
      steps {
        bat 'gradlew test'
      }
    }

    stage('deploy') {
      parallel {
        stage('deploy image') {
          steps {
            bat 'docker build -t natiaddis/tour-app .'
            bat 'docker tag natiaddis/tour-app  natiaddis/tour-app:v1'
            bat 'echo "docker push natiaddis/tour-app:v1"'
          }
        }

        stage('notify') {
          steps {
            echo 'email notification'
          }
        }

      }
    }

  }
}
