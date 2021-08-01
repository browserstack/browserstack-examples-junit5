if %CI% != true (
    echo 'Generating Allure Reports'
    if not exist allure-results/history allure-results/history
    xcopy allure-report/history allure-results/history
    allure generate --clean allure-results
)