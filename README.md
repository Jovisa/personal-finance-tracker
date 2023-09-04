# Personal Finance Tracker
The tracker keeps a record of financial transactions for a user. 
Trough RESTful APIs. it is possible to do all CRUD operations with Transactions, as well as basic `filtering by type (income or expenses)`


### Stack:
- Java 17
- gradle
- Spring Boot
- H2 in memory database



## Local environment setup

### Prerequisites
* Java 17+
* Gradle 8.0.2+

### Starting the project
- clone repository on your local machine
- build project with command `gradlew clean build` or via IDE
- run project with command `gradlew bootRun` or via IDE

## API Contract

`GET` `/transactions`<br>
- returns transactions form database, with summary of total income, total expences and balance
- filtering is enabled with `optional parameter: filterByType` that can be `income` or `expenses` 
when parameter is not present endpoint returns all Transactions, if present only Trasactions of the desired type will be returned

- #### response without a filter:

`{
"summary": {
"totalIncome": 2200.0,
"totalExpense": 1350.0,
"balance": 850.0
},
"transactions": [
{
"id": 1,
"type": "income",
"amount": 2100.0,
"description": "test grrr"
},
{
"id": 2,
"type": "income",
"amount": 100.0,
"description": "test grrr"
},
{
"id": 3,
"type": "expense",
"amount": 1300.0,
"description": ""
},
{
"id": 4,
"type": "expense",
"amount": 50.0,
"description": ""
}
]
}`

- #### respose with  `filterByType = income` 
`{
"summary": {
"totalIncome": 2200.0
},
"transactions": [
{
"id": 1,
"type": "income",
"amount": 2100.0,
"description": "test grrr"
},
{
"id": 2,
"type": "income",
"amount": 100.0,
"description": "test grrr"
}
]
}` <br>

We're getting only Transactions of the given type and Summary section shows only total income
<br><br>

- `POST` `/transactions/new`
Endpoint for creating Transactions
#### example request body
`    {
"type": "expense",
"amount": 50.0,
"description": "new expense"
}`

- `PUT` `/transactions/{id}`

- `DELETE` `/transactions/{id}`





