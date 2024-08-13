#!/usr/bin/env groovy

@Library('jenkinslibrary') _

def environment_deploy='---'
if (env.ENVIRONMENT_DEPLOY !='---') {
    environment_deploy = env.ENVIRONMENT_DEPLOY
}
def jobTask = '---'
if (env.PLAYBOOK_DEPLOY_NAME !='---') {
    jobTask = env.PLAYBOOK_DEPLOY_NAME
}
def verbosity_logs='-v'
if (env.VERBOSITY !='verbosity_logs') {
    verbosity_logs = env.VERBOSITY
}

pipeline {
    agent {
        label 'ansible'
    }
    options {
        ansiColor('xterm')
    }
    stages {
        stage('Ansible') {
            steps {
                ansiblePlaybook credentialsId: '>>>>REMPLACER_PAR_ID_DU_COMPTE_DE_SERVICE<<<<',
                        inventory: 'inventaire/'+environment_deploy,
                        playbook: 'playbook/' + jobTask + '.yml',
                        vaultCredentialsId : '>>>>REMPLACER_PAR_ID_DU_VAULT_PASSWORD_DE_VOTRE_EQUIPE<<<<',
                        installation: 'ansible',
                        colorized: true,
//                         SI BESOIN D'AJOUTER DES VARIABLES DANS ANSIBLE DEPUIS JENKINS
//                         extraVars: [
//                             HOST_NAME: host_deploy
//                         ],
                        extras:verbosity_logs
            }
        }
    }
}