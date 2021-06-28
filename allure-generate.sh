#copy from allure report to allure result

echo 'Generating Allure Reports'
mkdir -p allure-results/history
cp allure-report/history/* allure-results/history
allure generate --clean allure-results
allure serve allure-results
