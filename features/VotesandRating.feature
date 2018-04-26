#Author: d.shiva.kumar
@VotesandRating
Feature: Votes and Rating

  @Regression @Rating @Sanity
  Scenario Outline: Rating a video
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When User enters valid username "<username>" and its password "<password>"
    Then Home page with respective user appears
    And User Searches for the "<content>"
    Then user gives rating as "5"
    And Sign out
    And Close the application

    Examples: 
      | username    | password    | content |
      | 27822384348 | Vodacom@123 | spider  |

  @Regression @FavAdd @Sanity
  Scenario Outline: Adding and deletitng VOD to favourites
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When User enters valid username "<username>" and its password "<password>"
    Then Home page with respective user appears
    And User Searches for the "<content>"
    Then Add VOD to favourites list "<folder_name>"
    And Delete VOD "<vod_name>" from favourites list
    And Close the application

    Examples: 
      | username    | password    | content | folder_name | vod_name |
      | 27822384348 | Vodacom@123 | pirates | vodocom     | Spider   |
