from fabric.api import local, task


@task
def run():
    """
    Run arduino monitor locally.
    """
    local('mvn spring-boot:run')


@task
def deploy_openshift(openshift_app_name='monitoring'):
    """
    Build and deploy arduino monitor to openshift.
    :param openshift_app_name:
    :type openshift_app_name: str
    """
    local_file = 'target/arduino-monitor-*.war'
    remote_file = 'wildfly/standalone/deployments/ROOT.war'

    local('mvn clean package')
    local('rhc scp {openshift_app_name} upload {local_file} {remote_file}'.format(
        openshift_app_name=openshift_app_name, local_file=local_file, remote_file=remote_file))
    # run health-check
