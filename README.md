# reimagined-journey

This is a simple Java project to solve a very interesting matching challenge.

I'm a very strong believer in unit tests so, yes, there are unit tests with the code.  As of this submission, I skipped tests of the actual mapper, PriceListMapMatcher/Test, in lieu of the verified functionality of the actual application with the supplied product and listing files.

This project was developed in Java.  Gradle is used to help with dependency management and running both the tests and the application.  The Gradle binary (v4.0.0) is included within the project to help minimize additional configuration that might otherwise be necessary post _git clone_.  In a formal team setting, the gradle wrapper is preferred. 


## Algorithm Motivation

After reviewing the data in the files, and a couple small failed directions, I decided to:

1. Determine an _authority_ list of manufacturers (iterating through the products).
2. Group the price listings per _authority_ manufacturer.  For example, both price listing manufacturers 'Canon Canada' and 'Canon' were slotted into the product manufacturer 'Canon' (iterating through the listings).
3. For each product, find the associated authority listing group, then regex (whole-word) for the product model withing the listing title.  Matching listings were "marked" so, per the requirement, a listing can only be associated with one product.

As vaguely referred to in the Assumptions, if the data lists were longer then splitting of the work might be a better option within a different use-case/context.


## Requirements

These are above and beyond git install/config and access to GitHub.

- Java 8 is installed and configured
- Internet connection and access to download dependencies from:
  - _jcenter.bintray.com_, and
  - _repo1.maven.org/maven2_

## Assumptions

- the size and retrieval of the products and price listings will not be so large that this algorithm would be an issue.  That is, the complete lists can be obtained efficiently and processed without requiring the splitting of work.


## How-Tos

**&lt;reimagined-journey&gt;** refers to the directory into which this project was cloned.  Subsequent how-to tasks should be run from this directory.


### Clean The Project

By 'clean the project' I mean removing directories and files that are created during tasks like building the project and running tests.  This should bring the project back to what it looked like when it was first cloned.

    ./tool/gradle/bin/gradle clean

You'll see the _clean_ task included in the commands below to reset the project to a known point that helps reduce the possibility of a dirty environment and potential problems.

### Unit Tests

Although not part of the challenge requirements, but in case you're interested:

    ./tool/gradle/bin/gradle clean test

A pretty html report of the results can be seen by opening _&lt;reimagined-journey&gt;/build/reports/tests/test/index.html_ within a web browser.

Any logging from the tests outputs to _&lt;reimagined-journey&gt;/build/tests.log_.


### Running The Application

Finally, running the application as the main point of the challenge.

    ./tool/gradle/bin/gradle clean run

This command calls the _main_ method of the _matcher.MatcherChallenge_ class which starts the processing of the input files, **products.txt** and **listings.txt** located at _&lt;reimagined-journey&gt;/src/main/resources/_, and outputs the results to **&lt;reimagined-journey&gt;/matcher_challenge_results.json**.

Logging outputs to &lt;reimagined-journey&gt;/matcher_challenge.log.

________________________
