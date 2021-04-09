docker stop $(docker ps -q)
docker rm $(docker ps -a -q)
docker rmi $(docker images -q) -f
echo Docker containers:
docker ps -a
echo Docker images:
docker images -a