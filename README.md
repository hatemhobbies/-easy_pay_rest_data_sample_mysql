To build:

mvn install -DskipTests

To test:
sh ./startWalletAppAsContainer.sh
Then
mvn clean install

When completed use
cleanDocker.sh

Another option use

docker compose up

Do the postman testing 

Then 
docker compose down

Create a new wallet

	http://localhost:8080/wallets
	Http Method - POST

	{
	  "name" : "John Smith Wallet" 
	}

Create a new transactions

	http://localhost:8080/transactions
	Http Method - POST
	{
	  "amount" : "100.00", 
	  "purpose": "Payment for subcription"
	}


Assign transaction to wallet

	http://localhost:8080/transactions/1/wallet
	Http Method - PUT
	Content-Type:text/uri-list

	http://localhost:8080/wallet/1
	
	

	http://localhost:8080/transactions/2/wallet
	Http Method - PUT
	Content-Type:text/uri-list

	http://localhost:8080/wallets/1

Notes:

=============================  DOCKER SETUP ======================================

=============================  DOCKER CLEAN UP ======================================
Useful commands to clean up docker related containers, images, network and volumes
To clean up all the containers (BE CAREFUL)
docker rm -f $(docker ps -a -q)

To remove the local docker image for capstone-project/wallet:2.0
docker rmi capstone-project/wallet:2.0

To remove the easypaynet netowrk
docker network rm easypaynet

To remove all the volumes (BE CAREFUL)
docker volume rm $(docker volume ls -q)

To remove everything 
docker image prune --all     