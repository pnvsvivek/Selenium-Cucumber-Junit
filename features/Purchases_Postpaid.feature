Feature: Vodacom PCTV User Journeys with Postpaid number
  Description: This feature mainly contains scenarios to test Vodacom PCTV Login

  @SVODVideoPurchasePP @Regression
  Scenario Outline: SVOD video purchase and Playback
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When User enters valid username "<username>" and its password "<password>"
    Then Home page with respective user appears
    And User Searches for the "<content>"
    Then User is subscribing the video
    Then OTP box is being displayed and it is entered for "<username>"
    And Refresh the page to see watch button
    And Sign out
    And Close the application

    Examples: 
      | username    | password    | content              |
      | 27826600859 | Vodacom@123 | Lady Zamar - My Baby |

  @TVODVideoPurchasePP @Regression
  Scenario Outline: TVOD video purchase and Playback
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When User enters valid username "<username>" and its password "<password>"
    Then Home page with respective user appears
    And User Searches for the "<content>"
    When The content is being purchased for Rent
    Then OTP box is being displayed and it is entered for "<username>"
    And Refresh the page to see watch button
    And Sign out
    And Close the application

    Examples: 
      | username    | password    | content |
      | 27826600859 | Vodacom@123 | spider  |
