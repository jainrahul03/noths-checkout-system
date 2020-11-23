## NOTHS Checkout System

Given an input of basket items (product codes), it will apply the pre-defined promotions
and calculate the checkout total for the Basket.

Pre-requisites:
- Java 8+ is installed (https://www.oracle.com/uk/java/technologies/javase/javase-jdk8-downloads.html)
- Apache maven is installed. (http://maven.apache.org/download.cgi)

Steps to build and run the project:
- Clone/Download the project
- Move to project directory using `cd noths-checkout-system/`
- Build the project using `mvn clean package javadoc:javadoc`
- Javadoc can be accessed at `target/site/apidocs/index.html`
- Run the application using `java -jar target/noths-checkout-system-jar-with-dependencies.jar <comma separated product codes in basket>`
- Example: `java -jar target/noths-checkout-system-jar-with-dependencies.jar 001,002,003`
