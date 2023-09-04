# Personal Finance Tracker
The tracker keeps a record of financial transactions for a user. 
All CRUD operations are enabled Trough RESTful APIs, as well as basic `filtering by type (income or expenses)`


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

```json
{
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
"description": "salary"
},
{
"id": 2,
"type": "income",
"amount": 100.0,
"description": "got on poker"
},
{
"id": 3,
"type": "expense",
"amount": 1300.0,
"description": "car repair"
},
{
"id": 4,
"type": "expense",
"amount": 150.0,
"description": "house expences"
}
]
}
```

- #### respose with applied filter  `filterByType = income` 
```json
{
"summary": {
"totalIncome": 2200.0
},
"transactions": [
{
"id": 1,
"type": "income",
"amount": 2100.0,
"description": "salary"
},
{
"id": 2,
"type": "got on poker",
"amount": 150.0,
"description": "weekend trip"
}
]
}
```

- #### respose with applied filter  `filterByType = expense`
```json
{
  "summary": {
    "totalExpense": 1350.0
  },
  "transactions": [
    {
      "id": 3,
      "type": "expense",
      "amount": 1300.0,
      "description": "car repair"
    },
    {
      "id": 4,
      "type": "expense",
      "amount": 150.0,
      "description": "house expences"
    }
  ]
}
```
 <br>

We're getting only Transactions of the given type and Summary section shows only total income
<br><br>

`POST` `/transactions/new`
- Endpoint for creating new Transactions
- #### example request body
```json
{
"type": "expense",
"amount": 50.0,
"description": "new expense"
}
```
<br>

`PUT` `/transactions/{id}`
- updates Transaction
- #### example POST request:

- url: `/transactions/3`
- request body:
```json
{
    "type": "income",
    "amount": 1100.0,
    "description": "upaded amount, changed type"
}
```
<br>

- `DELETE` `/transactions/{id}`
- deletes Transaction with the given Id from the database





