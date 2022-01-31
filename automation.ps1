<#
    the scirpt is used for automation of the docker container creation and deployment process

    $Path The location of the project
    
    DEPENDENCIES this script requires docker and maven to be installed and docker
     image docker_database and docker network bugtracker_network to be set up
     
     commands for setting them up can be found in a file called setup.txt
#>

$Path = Split-Path $psISE.CurrentFile.FullPath

cd $Path
rm bugtracker_server.jar
mvn clean
mvn install -DskipTests
Copy-Item -Path $Path\target\bugtracker_server.jar -Destination .\
docker stop bugtracker_server
docker rm bugtracker_server
docker rmi $(docker images -f “dangling=true” -q)
docker build . -t bugtracker_server
docker start bugtracker_database
docker run --name bugtracker_server --network bugtracker_network -p8080:8080 -d bugtracker_server