
Used Page Object Model design pattern for test automation.

 To run tests
 ------------
 prerequisite: 
 * JAVA 8 
 * Maven
 
 run below command on command prompt. 
 
 *  mvn compile

 
 Default browser is chrome else you can use below options 
 - Firefox
 - Chrome
 - Opera
 - Edge
 
 * mvn test -Dbrowser="firefox" [To run tests in desired browser]
 
 
 To generate test report
 -----------------------
 prerequisite: 
 Allure report to be installed : [a link] http://allure.qatools.ru/
 
 Once allure is setup goto project directory and run below command
 * allure serve
 
 * You can check sample generated report under allure-report