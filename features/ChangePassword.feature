Feature: Change Password Negative Scenarios

  @PasswordMismatch @Regression @Sanity
  Scenario Outline: Password Mismatch in Change Password Section
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When User enters valid username "<username>" and its password "<password>"
    Then Home page with respective user appears
    When User clicks my account from user profile
    Then User tries to change the password by provding mismatch passwords
    And "<Expected error message>" is displayed
    And Sign out
    And Close the application

    Examples: 
      | username    | password    | Expected error message                                     |
      | 27829807446 | Vodacom@123 | The passwords you entered did not match, please try again. |

  @NotComplexPassword @Regression @Sanity
  Scenario Outline: Password Mismatch in Change Password Section
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When User enters valid username "<username>" and its password "<password>"
    Then Home page with respective user appears
    When User clicks my account from user profile
    Then User tries to change the password by provding non-complex passwords
    And Error message "<Expected error message>" is displayed
    And Sign out
    And Close the application

    Examples: 
      | username    | password    | Expected error message         |
      | 27829807446 | Vodacom@123 | Password is not complex enough |
