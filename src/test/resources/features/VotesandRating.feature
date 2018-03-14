#Author: d.shiva.kumar
@VotesandRating
Feature: Votes and Rating

  Scenario Outline: Rating a video
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When User enters valid "<username>" and password
    Then Home page with respective user appears
    And Search for the "<content>"
    Then user gives rating as "5"
    And Sign out
    And Close the application

    Examples: 
      | username    | content                              |
      | 27829807989 | War And Peace (s2): ep 02 with Music |

  @Regression
  Scenario Outline: Adding and deletitng VOD to favourites
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When User enters valid "<username>" and password
    Then Home page with respective user appears
    And Search for the "<content>"

    Examples: 
      | username    | content      |
      | 27829807989 | Wonder Woman |
