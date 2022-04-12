run cashman-flask-project/bootstrap.sh

curl http://localhost:5000/expenses
curl -X POST  http://192.168.0.100:5000/expenses -H "Content-Type: application/json" -d @expense.json

curl http://localhost:5000/incomes
curl -X POST  http://192.168.0.100:5000/incomes -H "Content-Type: application/json" -d @income.json
