from fabric.api import cd, local, put, run, task
from fabric.contrib.files import exists


@task
def run_local():
    """
    Run arduino monitor locally.
    """
    local('grunt clean dev')
    local('mvn spring-boot:run')


@task
def deploy_openshift(openshift_app_name='monitoring'):
    """
    Deploy arduino monitor to openshift.

    :param openshift_app_name: openshift application name
    :type openshift_app_name: str
    """
    local_file = 'target/arduino-monitor-*.war'
    remote_file = 'wildfly/standalone/deployments/ROOT.war'

    local('grunt clean dist')
    local('mvn clean package')
    local('rhc scp {openshift_app_name} upload {local_file} {remote_file}'.format(
            openshift_app_name=openshift_app_name, local_file=local_file, remote_file=remote_file))
    # run health-check


@task
def deploy_raspberry():
    """
    Deploy arduino logger to raspberry pi.
    """
    if not exists('logger'):
        run('virtualenv -p /usr/bin/python3 logger')

    with cd('logger'):
        put('bin/logger.py', '.')
        put('bin/requirements.txt', '.')
        run('source bin/activate && pip install -r requirements.txt')


@task
def start_raspberry_logger(openshift_app_name='monitoring'):
    """
    Start arduino logger on raspberry pi.

    :param openshift_app_name: openshift application name
    :type openshift_app_name: str
    """
    with cd('logger'):
        run('source bin/activate && nohup python logger.py /dev/ttyACM0 {}-kupcimat.rhcloud.com 300 &'.format(
                openshift_app_name))


@task
def stop_raspberry_logger():
    """
    Stop arduino logger on raspberry pi.
    """
    with cd('logger'):
        # kill any running logger process
        run('kill $(ps -e -o pid,cmd | grep \'[l]ogger.py\' | awk \'{print $1}\')')


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
