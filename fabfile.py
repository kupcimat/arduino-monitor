from fabric.api import local, task


@task
def run():
    """
    Run arduino monitor locally.
    """
    local('grunt clean dev')
    local('mvn spring-boot:run')


@task
def deploy(openshift_app_name='monitoring'):
    """
    Deploy arduino monitor to openshift.

    :param openshift_app_name: openshift application name
    :type openshift_app_name: str
    """
    local_file = 'target/arduino-monitor-*.war'
    remote_file = 'wildfly/standalone/deployments/ROOT.war'

    build_war()
    local('rhc scp {openshift_app_name} upload {local_file} {remote_file}'.format(
        openshift_app_name=openshift_app_name, local_file=local_file, remote_file=remote_file))
    # run health-check


@task
def test_local():
    """
    Test local application by sending mocked logs.
    """
    local('python3 bin/logger_mock.py localhost:8080')


@task
def test_remote(openshift_app_name='monitoring'):
    """
    Test deployed application by sending mocked logs.

    :param openshift_app_name: openshift application name
    :type openshift_app_name: str
    """
    local('python3 bin/logger_mock.py {openshift_app_name}-kupcimat.rhcloud.com'.format(
        openshift_app_name=openshift_app_name))


def build_war():
    local('grunt clean dist')
    local('mvn clean package')
