Feature: Vodacom PCTV check for Negative scenarios in UpdateProfile
  Description: This feature mainly contains scenarios to test Vodacom PCTV update profile functionality

  @ChangeMSISDN @Regression
  Scenario Outline: Change the MSISDN from My account section and then continue user actions
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When User enters valid username "<username>" and its password "<password>"
    Then Home page with respective user appears
    When User clicks my account from user profile
    Then change "<MSISDNnumber>" to an existing user number
    Then OTP box is being displayed and it is entered for "<MSISDNnumber>"
    When user clicks on  Submit button and "<expected popup message>" is displayed
    #   Then click on ok button
    #   And Close the pop-up that is dispalyed
    #   And Sign out
    #   And User clicks on Login icon on top rght hand side
    When User enters valid username "<MSISDNnumber>" and its password "<password>"
    Then Home page with respective user appears
    And Sign out
    And Close the application

    Examples: 
      | username    | password    | MSISDNnumber | expected popup message                    |
      | 27829807590 | Vodacom@123 |  27829807589 | Your changes have been successfully saved |

  @ChangeMSISDNtoInvalid @Regression @Sanity @Retest
  Scenario Outline: Change the MSISDN from My account section and then continue user actions
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When User enters valid username "<username>" and its password "<password>"
    Then Home page with respective user appears
    When User clicks my account from user profile
    Then change "<MSISDNnumber>" to an existing user number
    Then user gets a popup message as "<Expected message>"
    And Close the application

    Examples: 
      | username    | password    | MSISDNnumber | Expected message         |
      | 27822384348 | Vodacom@123 |  32829807590 | Invalid cellphone number |
