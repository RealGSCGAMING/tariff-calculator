# Tariff Calculator
This project was created for my CS class. The original aim was to provide a quick and easy way to view the current US tariffs on different countries. I quickly realized that the US tariffs have been reduced to a flat 10%, which pretty much makes the project pointless.<br>
## Features
- Tariff calculation based on data from a CSV file
- Currency exchange using an API
- That's pretty much it
## Setup
The project requires the libraries listed in the **.vscode/settings.json** file. Put the jar files in a new folder called **libraries**.<br>
To enable the currency API, create a file named **secrets.properties** and add a new property called **currencyapikey**. Set it equal to an API key from http://exchangerate-api.com. If you don't set up an API key, the program will just read the outdated values in **currency.csv**.
