#Author: d.shiva.kumar
Feature: Forgot Password Negative scenario

  @Regression @PasswordMismatch @Sanity
  Scenario Outline: Forgot password Mismatch
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When user clicks on ForgotPassword link
    Then Enter the username "<username>"
    And Enter password "<password>" confirmPassword "<Repeat new password>"
    Then User click on submit button and "<Expected message>" is displayed
    Then Close the application

    Examples: 
      | username    | password   | Repeat new password | Expected message                                                |
      | 27829807092 | Tester@123 | Tester@12345        | Passwords entered did not match. Please enter your new password |

  @Regression @PasswordWithInvalidUsername @Sanity
  Scenario Outline: Forgot password Invalid username
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When user clicks on ForgotPassword link
    Then Enter the username "<username>"
    And Enter password "<password>" confirmPassword "<Repeat new password>"
    Then User click on submit button and "<Expected message>" is displayed
    Then Close the application

    Examples: 
      | username    | password   | Repeat new password | Expected message                   |
      | 39829807092 | Tester@123 | Tester@123          | Invalid Username. Please try again |

  @Regression @CriterionNotMatch @Sanity
  Scenario Outline: Forgot password with criterion does not match
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When user clicks on ForgotPassword link
    Then Enter the username "<username>"
    And Enter password "<password>" confirmPassword "<Repeat new password>"
    Then User click on submit button
    Then User click on submit button and "<Expected message>" is displayed
    Then Close the application

    Examples: 
      | username    | password | Repeat new password | Expected message                                                |
      | 27829807092 | Voda     | Voda                | Password does not meet criteria. Please enter your new password |
