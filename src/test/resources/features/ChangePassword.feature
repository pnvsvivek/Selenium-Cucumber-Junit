Feature: Change Password Negative Scenarios

  @PasswordMismatch @Regression
  Scenario Outline: Password Mismatch in Change Password Section
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When User enters valid "<username>" and its password
    Then Home page with respective user appears
    When User clicks my account from user profile
    Then User tries to change the password by provding mismatch passwords
    And "<Expected error message>" is displayed
    And Sign out
    And Close the application

    Examples: 
      | username    | Expected error message                                     |
      | 27829807287 | The passwords you entered did not match, please try again. |

  @NotComplexPassword @Regression
  Scenario Outline: Password Mismatch in Change Password Section
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When User enters valid "<username>" and its password
    Then Home page with respective user appears
    When User clicks my account from user profile
    Then User tries to change the password by provding non-complex passwords
    And Error message "<Expected error message>" is displayed
    And Sign out
    And Close the application

    Examples: 
      | username    | Expected error message                                                     |
      | 27829807287 | Please choose a password 8-32 characters long, with following constraints: |
