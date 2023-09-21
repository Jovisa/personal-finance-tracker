# Personal Finance Tracker
The tracker keeps a record of financial transactions for a user. 
All CRUD operations are enabled Trough RESTful APIs, as well as basic `filtering by type (income or expenses)`


### Stack:
- Java 17
- gradle 8.0.2
- Spring Boot 3
- H2 in-memory database



## Local environment setup

### Prerequisites
* Java 17+
* Gradle 8.0.2+

### Starting the project
- clone repository on your local machine
- build project with the command `gradlew clean build` or via IDE
- run the project with the command `gradlew bootRun` or via IDE

## Security

- Application has predefined in-memory Users defined in [Security Configuration](https://github.com/Jovisa/personal-finance-tracker/blob/main/src/main/java/com/tw/personalfinancetracker/config/SecurityConfiguration.java)
- endpoints can be accessed by authenticated users only
- users with the role `ADMIN` can perform all CRUD operations on all `Transactions` without any limitation
- user with the role `USER` can only access their own transactions



## API Contract

`Swagger docks:`
- http://localhost:8080/swagger-ui/index.html#/search-controller/search
</br><br>

`GET` `/transactions`<br>
- returns transactions from database, with a summary of total income, total expenses and balance
- filtering is enabled with `optional parameter: typeFilter` that can be `income` or `expenses` 
when the parameter is not present endpoint returns all Transactions, if present only Transactions of the desired type will be returned
- regular user can see only his transactions, admin can see them all

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
<br>

- with the applied filter, the user will get Transactions of the desired type, and `Summary` section will show only `totalIncome` or `totalExpense` depending on the filter
- #### response with applied filter  `typeFilter = income` 
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

- #### response with applied filter  `typeFilter = expense`
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
<br><br>

`POST` `/transactions/new`
- Endpoint for creating new Transactions
- available to all authenticated users
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
- users can update only transactions they created, admins can update any transaction
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
- deletes Transaction with the given ID from the database
- users can delete only transactions they created, admins can delete any transaction





