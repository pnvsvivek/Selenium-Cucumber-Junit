Feature: Vodacom PCTV check for Negative scenarios in Registration
  Description: This feature mainly contains scenarios to test Vodacom PCTV Registration functionality

  @InvalidUser @Regression @Sanity @Retest
  Scenario Outline: Register with invalid user
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When user clicks on SignUp button
    Then fill in the details name "<name>", password "<password>" and invalid username "<username>" details
    Then user gets a popup message as "<expected popup message>"
    Then click on ok button
    And Close the application

    Examples: 
      | name | username    | password | expected popup message             |
      | Test | 32829876487 | Test123! | Invalid Username. Please try again |

  @RegisterWithoutEnteringMandatoryFields @Regression @Sanity
  Scenario Outline: Register without entering mandatory fields
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When user clicks on SignUp button
    Then fill in the details name "<name>" and enter username "<username>" and password "<password>" fields as empty
    Then verify next step button is disabled
    Then Close the application

    Examples: 
      | name | username | password |
      | Test |          |          |

  @RegisterWithoutCheckingTandCcheckbox @Regression @Sanity
  Scenario Outline: Register without checking the T&C checkbox
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When user clicks on SignUp button
    Then fill name "<name>" , username "<username>", password "<password>"  details and not check the T&C checkbox
    Then verify next step button is disabled
    Then Close the application

    Examples: 
      | name | username    | password |
      | Test | 27456723459 | Test123! |

  @RegisterWithNonMatchingPasswords @Regression @Sanity
  Scenario Outline: Register with non matching passwords
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When user clicks on SignUp button
    Then fill in the details name "<name>" , username "<username>" and password "<password>" , confirm password "<confirm password>" as not matching
    Then verify next step button is disabled
    Then Close the application

    Examples: 
      | name | username    | password | confirm password |
      | Test | 27234562345 | Test123! | Testing          |

  @RegisterWithInvalidOTP @Regression @Sanity
  Scenario Outline: Register with invalid OTP
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When user clicks on SignUp button
    Then fill in the details name "<name>" , username "<username>" and password "<password>"
    Then OTP box is displayed and enter invalid OTP "<invalid OTP>" and submit
    Then verify error message as "<error message>"
    And click on ok button
    Then Close the application

    Examples: 
      | name | username    | password | confirm password | invalid OTP | error message        |
      | Test | 27829807586 | Test123! | Testing          |        1234 | Invalid One-Time PIN |
