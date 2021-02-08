# auctionapp
To run this app folow the steps below:

### Clone this repo
**run command**: "git clone https://github.com/sozenarede/auctionapp.git"
### Start the server
"**cd**" into the local repository directory
**run command**: ./mvnw spring-boot:run
### Start Auction
Access *http://localhost:8080/api/auction/start* in your webbrowser

### Make a bid
To make a bit you need to provide two information:
- the name of bid owner (String)
- the bid value (Doble grather then zero)

**auctionapp** gets its parameters using querystring on GET requests and can handle up to 999 bids.
Use a URL similar to tis one to make bids:

*http://localhost:8080/api/auction/sendbid?bidvalue=3.25&bidowner=soze*

### Close the Auction
Closing the Auction will calculate the winner bid and total profit.
Also it will clear the objects used to keep the bid info in run time.
Use the URL below to close Auction:
*http://localhost:8080/api/auction/end*

### Get Auction final result
Access the below URL to get the results:
*http://localhost:8080/api/auction/res*

