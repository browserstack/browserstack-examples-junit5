
<h1 align="center">   :zap: <img src="https://avatars.githubusercontent.com/u/1119453?s=200&v=4" width="60" height="60" > BrowserStack Examples JUnit 5  <img src="https://camo.githubusercontent.com/abbaedce4b226ea68b0fd43521472b0b146d5ed57956116f69752f43e7ddd7d8/68747470733a2f2f6a756e69742e6f72672f6a756e6974352f6173736574732f696d672f6a756e6974352d6c6f676f2e706e67" width="60" height="60" >
 :zap:</h1>
 
 # :bookmark: [Overview](https://github.com/browserstack/browserstack-examples-junit5#overview)
 
 ### :label: [Introduction](https://github.com/browserstack/browserstack-examples-junit5#introduction) 
 ### :wrench:  [Repository Configuration](https://github.com/browserstack/browserstack-examples-junit5#repositoryconfiguration)

 # :label: [Introduction](https://github.com/browserstack/browserstack-examples-junit5#introduction) 

Welcome to BrowserStack JUnit 5 Examples, a sample UI testing framework empowered with **[Selenium](https://www.selenium.dev/)** and **[JUnit 5](https://junit.org/junit5/)**. Along with the framework the repository also contains a collection of sample test scripts written for **[BrowserStack Demo Application](https://bstackdemo.com/)**.

This repository includes a number of **[sample configuration files](/src/test/resources)** to run these on tests on various platforms including **on-premise browsers**, browsers running on a remote selenium grid such as **[BrowserStack Automate](https://www.browserstack.com/automate)** or in a **[Docker container](https://github.com/SeleniumHQ/docker-selenium)**. The framework and test scripts are configured to run with both **[Gradle](https://gradle.org/)** and **[Maven](https://maven.apache.org/)**. Starter **[gradle.build](/build.gradle)** and **[pom.xml](/pom.xml)** files are also included in the project.

<h1></h1>

 ## Tests Included in this Repository
 
 Following are the test cases included in this repository:

| Module   | Test Case                          | Description |
  | ---   | ---                                   | --- |
| [E2E](/src/test/java/com/browserstack/examples/tests/e2e)      | [PurchaseTest](/src/test/java/com/browserstack/examples/tests/e2e/PurchaseTest.java)                | This test scenario verifies successful product purchase lifecycle end-to-end. It demonstrates the [Page Object Model design pattern](https://www.browserstack.com/guide/page-object-model-in-selenium) and is also the default test executed in all the single test run profiles. |
| [Login](/src/test/java/com/browserstack/examples/tests/login)    | [RedirectionTest](/src/test/java/com/browserstack/examples/tests/login/RedirectionTest.java)         | This test verifies that the user needs to login to view the favourites marked by him or her. |
| Login    | Login as Locked User               | This test verifies the login workflow error for a locked user. |
| Offers   | Offers for Mumbai location     | This test mocks the GPS location for Mumbai and verifies that the product offers applicable for the Mumbai location are shown.   |
| Product  | Apply Apple Vendor Filter          | This test verifies that 9 Apple products are only shown if the Apple vendor filter option is applied. |
| Product  | Apply Lowest to Highest Order By   | This test verifies that the product prices are in ascending order when the product sort "Lowest to Highest" is applied. |
| User     | Login as User with no image loaded | This test verifies that the product images load for user: "image_not_loading_user" on the e-commerce application. Since the images do not load, the test case assertion fails.|
| User     | Login as User with existing Orders |  This test verifies that existing orders are shown for user: "existing_orders_user"  |
  
<h1> </h1>

 # :gear:  [Repository Setup](https://github.com/browserstack/browserstack-examples-junit5#repositorysetup)
 
 ## Prerequisites
 Ensure you have the following dependencies installed on the machine
 
 1. Java Development Kit (8 or above)
 2. Maven (3 or above) or Gradle ( 7 or above)
 3. Allure Command Line Tool or Allure Jenkins Plugin
 4. [Chrome Driver](https://chromedriver.chromium.org/) and [Chrome Browser](https://www.google.com/chrome/)   ![OnPrem](https://img.shields.io/badge/For-OnPrem-green)
 5. [Docker](https://www.docker.com/) and [Docker Selenium Grid](https://github.com/SeleniumHQ/docker-selenium).  ![OnDocker](https://img.shields.io/badge/For-OnDocker-blue)
 6. [BrowserStack Automate Account](https://www.browserstack.com/automate). ![BrowserStack](https://img.shields.io/badge/For-BrowserStackAutomate-orange)

 
  ## Setup with Maven ![Maven](https://img.shields.io/badge/With-maven-indigo)
 :pushpin: Clone this repository 
 <br/>
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 <code> git clone git@github.com:browserstack/browserstack-examples-junit5.git </code>
  <br/> <br/>
 :pushpin: Navigate to the repository directory
  <br/>
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 <code>cd browserstack-examples-junit5</code>
 <br/> <br/>
 :pushpin: Install the required maven JARs
  <br/>
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 <code>mvn install</code>
 
   ## Setup with Gradle ![Gradle](https://img.shields.io/badge/With-gradle-green)
 :pushpin: Clone this repository 
 <br/>
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 <code> git clone git@github.com:browserstack/browserstack-examples-junit5.git </code>
  <br/> <br/>
 :pushpin: Navigate to the repository directory
  <br/>
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 <code>cd browserstack-examples-junit5</code>
  <br/> <br/>
 :pushpin: Install the required gradle JARs
  <br/>
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 <code>gradle build</code>
 <br>
 
 # :wrench:  [Repository Configuration](https://github.com/browserstack/browserstack-examples-junit5#repositoryconfiguration)
 
The repository is designed to execute on any browser managed by a selenium webdriver. As an example the included configuration files are designed to run on the following three setups. 
1. On Premise Browsers
2. Browsers running in a docker container
3. Browsers on BrowserStack Automate

The repository is also designed to execute on both public as well as a locally hosted copy of the demo application. The prerequisites and setup details for all of these variations are labeled respectively.

 ## Configuring Concurrency Parameters ![Maven](https://img.shields.io/badge/With-maven-indigo) ![Gradle](https://img.shields.io/badge/With-gradle-green)

On a selenium Grid such as BrowserStack Automate or the one hosted on container such as Docker, you can spin multiple browser instances in parallel to reduce your over all build time. With JUnit 5 and Maven or Gradle you configure the number concurrent executions to an optimal count. In the sample POM or Gradle builld file in the repository it is set to a value 5 with <code>junit.jupiter.execution.parallel.config.fixed.parallelism</code> property. All such JUnit 5 configurations are gouped in these files together. Change these values as per your convenience to achieve the level of concurrency you desire.

 # :rocket:  [Test Execution](https://github.com/browserstack/browserstack-examples-junit5#testexecution)
 
 ## Test Execution Prerequisites ![OnPrem](https://img.shields.io/badge/For-OnPrem-green)

For this infrastructure configuration (i.e on-premise), create the drivers folder at /src/test/resources and ensure that the ChromeDriver executable is placed in the /src/test/resources/drivers folder.

Note: The ChromeDriver version must match the Chrome browser version on your machine.

 ## Test Execution Prerequisites ![OnDocker](https://img.shields.io/badge/For-OnDocker-blue)
 
Run <code>docker-compose pull</code> from the current directory of the repository.
 
Start the Docker by running the following command:
 
 <code>docker-compose up -d</code>

 ## Test Execution Prerequisites ![BrowserStack](https://img.shields.io/badge/For-BrowserStackAutomate-orange)
 
 - Create a new [BrowserStack account](https://www.browserstack.com/users/sign_up) or use an existing one.
- Identify your BrowserStack username and access key from the [BrowserStack Automate Dashboard](https://automate.browserstack.com/) and export them as environment variables using the below commands.

  - For \*nix based and Mac machines:

  ```sh
  export BROWSERSTACK_USERNAME=<browserstack-username> &&
  export BROWSERSTACK_ACCESS_KEY=<browserstack-access-key>
  ```

  - For Windows:

  ```shell
  set BROWSERSTACK_USERNAME=<browserstack-username>
  set BROWSERSTACK_ACCESS_KEY=<browserstack-access-key>
  ```

  Alternatively, you can also hardcode username and access_key objects in the [caps.json](resources/conf/caps/caps.json) file.

Note:
- We have configured a list of test capabilities in the [caps.json](resources/conf/caps/caps.json) file. You can certainly update them based on your device / browser test requirements.
- The exact test capability values can be easily identified using the [Browserstack Capability Generator](https://browserstack.com/automate/capabilities)

 ## Test Execution Prerequisites ![Local](https://img.shields.io/badge/For-Local-yellow)
 - Clone the [BrowserStack demo application](https://github.com/browserstack/browserstack-demo-app) repository.
  ```sh
  git clone https://github.com/browserstack/browserstack-demo-app
  ``` 
- Please follow the README.md on the BrowserStack demo application repository to install and start the dev server on localhost.
- In this section, we will run a single test case to test the BrowserStack Demo app hosted on your local machine i.e. localhost. Refer to the `single_local` object in `caps.json` file to change test capabilities for this configuration.
- Note: You may need to provide additional BrowserStackLocal arguments to successfully connect your localhost environment with BrowserStack infrastructure. (e.g if you are behind firewalls, proxy or VPN).
- Further details for successfully creating a BrowserStackLocal connection can be found here:

  - [Local Testing with Automate](https://www.browserstack.com/local-testing/automate)
  - [BrowserStackLocal Java GitHub](https://github.com/browserstack/browserstack-local-java)

 
 
 
## Test Execution Profiles ![Maven](https://img.shields.io/badge/With-maven-indigo) ![Gradle](https://img.shields.io/badge/With-gradle-green)

Following are the preconfigured test execution profiles available in the sample POM or Gradle build file.


  
<table>
 <tr>
  <th width='10%'>Profile</th>
  <th width='10%'>Description</th>
  <th width='10%'>Maven Command</th>
  <th width='10%'>Gradle Command</th>
  <th width='10%'>Configuration File</th>
 </tr>
 <tr>
  <td>on-prem</td>
  <td>Runs a single test on a Chrome browser instance on your own machine.</td>
  <td><code>mvn test on-prem</code></td>
  <td><code>gradle on-prem</code></td>
  <td>capabilities-on-prem.yml</td>
 </tr>
  <tr>
  <td>on-prem-suite</td>
  <td>Runs the entire test suite sequentially on Chrome browser instances, on your own machine.</td>
  <td><code>mvn test on-prem-suite</code></td>
  <td><code>gradle on-prem-suite</code></td>
  <td>capabilities-on-prem-suite.yml</td>
 </tr>
 
  <tr>
  <td>docker</td>
  <td>Runs a single test on a Firefox browser instance running in a Docker container.</td>
  <td><code>mvn test docker</code></td>
  <td><code>gradle docker</code></td>
  <td>capabilities-docker.yml</td>
 </tr>
 
  <tr>
  <td>docker-parallel</td>
  <td>Concurrently runs the entire test suite on a number of Firefox browser instances running in a Docker container.</td>
  <td><code>mvn test docker-parallel</code></td>
  <td><code>gradle docker-parallel</code></td>
  <td>capabilities-docker-parallel.yml</td>
 </tr>
 
 <tr>
  <td>bstack-single</td>
  <td>Runs a single test on a single browser on BrowserStack.</td>
  <td><code>mvn test bstack-single</code></td>
  <td><code>gradle bstack-single</code></td>
  <td>capabilities-single.yml</td>
 </tr>
 
  <tr>
  <td>bstack-local</td>
  <td>Runs a single test on a single browser on BrowserStack (On a copy demo application hosted on your internal environment or local machine).</td>
  <td><code>mvn test bstack-local</code></td>
  <td><code>gradle bstack-local</code></td>
  <td>capabilities-local.yml</td>
 </tr>
 
  <tr>
  <td>bstack-local-parallel</td>
  <td>Concurrently runs the entire test suite on a single Browser type on BrowserStack (On a copy demo application hosted on your internal environment or local machine).</td>
  <td><code>mvn test bstack-local-parallel</code></td>
  <td><code>gradle bstack-local-parallel</code></td>
  <td>capabilities-local-parallel.yml</td>
 </tr>
 
 <tr>
  <td>bstack-local-parallel-browsers</td>
  <td>Concurrently runs the entire test suite on a number of device/browser types on BrowserStack (On a copy demo application hosted on your internal environment or local machine).</td>
  <td><code>mvn test bstack-local-parallel-browsers</code></td>
  <td><code>gradle bstack-local-parallel-browsers</code></td>
  <td>capabilities-local-parallel-browsers.yml</td>
 </tr>
 
  <tr>
  <td>bstack-parallel</td>
  <td>Concurrently runs the entire test suite on a single Browser type on BrowserStack.</td>
  <td><code>mvn test bstack-parallel</code></td>
  <td><code>gradle bstack-parallel</code></td>
  <td>capabilities-parallel.yml</td>
 </tr>
 
   <tr>
  <td>bstack-parallel-browsers</td>
  <td>Concurrently runs the entire test suite on a number of device/browser types on BrowserStack.</td>
  <td><code>mvn test bstack-parallel-browsers</code></td>
  <td><code>gradle bstack-parallel-browsers</code></td>
  <td>capabilities-parallel-browsers.yml</td>
 </tr>
 </table>
 
 
 
 # :chart_with_upwards_trend:  [Test Results](https://github.com/browserstack/browserstack-examples-junit5#testresults)
 
 ## Viewing Allure Reports

 The repository is configured to generate Alllure of history with each execution. To view the results in an HTML format run the following command
  
  <code>allure serve allure-results</code>
 
 # :card_file_box: [Additional Resources](https://github.com/browserstack/browserstack-examples-junit5#additionalresources)

- View your test results on the [BrowserStack Automate dashboard](https://www.browserstack.com/automate)
- Documentation for writing [Automate test scripts in Java](https://www.browserstack.com/automate/java)
- Customizing your tests capabilities on BrowserStack using our [test capability generator](https://www.browserstack.com/automate/capabilities)
- [List of Browsers & mobile devices](https://www.browserstack.com/list-of-browsers-and-platforms?product=automate) for automation testing on BrowserStack
- [Using Automate REST API](https://www.browserstack.com/automate/rest-api) to access information about your tests via the command-line interface
- Understand how many parallel sessions you need by using our [Parallel Test Calculator](https://www.browserstack.com/automate/parallel-calculator?ref=github)
- For testing public web applications behind IP restriction, [Inbound IP Whitelisting](https://www.browserstack.com/local-testing/inbound-ip-whitelisting) can be enabled with the [BrowserStack Enterprise](https://www.browserstack.com/enterprise) offering
