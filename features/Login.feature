Feature: Vodacom PCTV check for Negative scenarios in Login
  Description: This feature mainly contains scenarios to test Vodacom PCTV Registration functionality

  @InvalidPassword @Regression @Sanity
  Scenario Outline: Login with invalid password
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When User enters valid "<username>" and invalid "<password>"
    Then user gets a popup message as "<expected popup message>"
    Then click on ok button
    And Close the application

    Examples: 
      | username    | password | expected popup message |
      | 27829807295 | Test     | Invalid Password       |

  @DeRegisterUser @Regression
  Scenario Outline: Login with deRegistered user
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When user clicks on SignUp button and fill in the details of "<username>"
    Then OTP box is being displayed and it is entered for "<username>"
    And Login with the registered "<username>" and message "<Expected message>" is displayed
    Then Home page with respective user appears
    And Sign out
    And DeRegister the User "<username>"
    And User clicks on Login icon on top rght hand side
    When User enters valid "<username>" and password
    Then Home page with respective user appears
    And Close the application

    Examples: 
      | username    | Expected message                                      |
      | 27829856486 | Congratulations, you are now registered to Video Play |
