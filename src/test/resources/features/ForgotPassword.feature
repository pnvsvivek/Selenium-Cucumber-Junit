#Author: d.shiva.kumar
Feature: Forgot Password Negative scenario

  @Regression
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

  @Regression
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
      | 28829807092 | Tester@123 | Tester@123          | Invalid Username. Please try again |

  #@Regression
  #Scenario Outline: Forgot password with invalid OTP
  # Given Application URL is url
  # When Open the Application in given browser
  # Then Landing page is displayed
  # Given User enters PIN to go to Home Page
  #  And User clicks on Login icon on top rght hand side
  #  When user clicks on ForgotPassword link
  #  Then Enter the username "<username>"
  #  And Enter password "<password>" confirmPassword "<Repeat new password>"
  #  Then User click on submit button
  #  And OTP box is displayed and invalid OTP is entered
  #  Then user gets a popup message "<Expected message>"
  #  Then Close the application

  # Examples: 
  #   | username    | password   | Repeat new password | Expected message                                 |
  #   | 27829807092 | Tester@123 | Tester@123          | OTP is invalid or has expired. Please try again. |
