Feature: VOD Search

  @Regression @Sanity
  Scenario Outline: VOD Search
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When User enters valid username "<username>" and its password "<password>"
    Then Home page with respective user appears
    And Search for the title "<title name>"
    Then Sort the search results in "<order>"
    And Sign out
    And Close the application

    Examples: 
      | username    | password    | title name | order        |
      | 27822384348 | Vodacom@123 | series     | Rating (L-H) |

  # order accepts | Title (A-Z) | Title (Z-A) | Rating (H-L) | Rating (L-H) |
  @Regression @Sanity
  Scenario Outline: VOD Search
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When User enters valid username "<username>" and its password "<password>"
    Then Home page with respective user appears
    And Search for the title "<title name>"
    Then Verify no search results message
    And Sign out
    And Close the application

    Examples: 
      | username    | password    | title name |
      | 27822384348 | Vodacom@123 | hello      |
