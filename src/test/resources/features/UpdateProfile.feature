Feature: Vodacom PCTV check for Negative scenarios in UpdateProfile
  Description: This feature mainly contains scenarios to test Vodacom PCTV update profile functionality

  @ChangeMSISDN @Regression
  Scenario Outline: Change the MSISDN from My account section and then continue user actions
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When User enters valid "<username>" and its password
    Then Home page with respective user appears
    When User clicks my account from user profile
    Then change "<MSISDNnumber>" to an existing user number
    Then OTP box is displayed and it is entered
    When user clicks on  Submit button and "<expected popup message>" is displayed
    Then click on ok button
    And Close the pop-up that is dispalyed
    And Sign out
    And User clicks on Login icon on top rght hand side
    When User enters valid "<MSISDNnumber>" and its password
    Then Home page with respective user appears
    And Search for the "<content>"
    When The content is purchased for Rent
    Then OTP box is displayed and it is entered
    Then Play the content for some time and close
    When User clicks my account from user profile
    Then Change the "password_updateProfile" from My accounts page and message "<Expected message>" is displayed
    And Sign out
    And Close the application

    Examples: 
      | username    | MSISDNnumber | expected popup message     | content | Expected message                               |
      | 27829807287 |  27829807580 | Please sign in to continue | pirates | Congratulation! Your password has been changed |

  #@UpdateprofileWithInvalidMSISDN @Regression
  #Scenario Outline: Update profile with
  # Given Application URL is url
  # When Open the Application in given browser
  # Then Landing page is displayed
  # Given User enters PIN to go to Home Page
  # And User clicks on Login icon on top rght hand side
  # When User enters valid "<username>" and password
  # Then Home page with respective user appears
  # When User clicks my account from user profile
  # Then change "<MSISDNnumber>" to an existing user number
  # Then OTP box is displayed and it is entered
  # Then user gets a popup message as "<expected popup message>"
  # And Close the application

   # Examples: 
    #  | username    | password    | MSISDNnumber | expected popup message    |
     # | 27829807580 | Vodacom1234 |  27829807287 | Subscriber already exists |
