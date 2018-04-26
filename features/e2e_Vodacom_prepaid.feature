Feature: Vodacom PCTV User Journeys with Prepaid number
  Description: This feature mainly contains scenarios to test Vodacom PCTV Login

  @Registration1 @Regression
  Scenario Outline: User Registration
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When user clicks on SignUp button and fill in the details of "<username>"
    Then OTP box is displayed and it is entered for "<username>"
    And Login with the registered "<username>" and message "<Expected message>" is displayed
    Then Home page with respective user appears
    And Close the application
    And DeRegister the User "<username>"

    Examples: 
      | username    | Expected message                                      |
      | 27829815403 | Congratulations, you are now registered to Video Play |

  @SVODVideoPurchase @Regression
  Scenario Outline: SVOD video purchase and Playback
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When User enters username "<username>" and its password "<password>"
    Then Home page with respective user appears
    And Search for the "<content>"
    Then User subscribes the video
    Then OTP box is displayed and it is entered for "<username>"
    And Refresh the page to see watch button
    And Sign out
    And Close the application

    Examples: 
      | username    | password    | content              |
      | 27829807094 | Vodacom@123 | Lady Zamar - My Baby |

  @TVODVideoPurchase @Regression
  Scenario Outline: TVOD video purchase and Playback
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When User enters username "<username>" and its password "<password>"
    Then Home page with respective user appears
    And Search for the "<content>"
    When The content is purchased for Rent
    Then OTP box is displayed and it is entered for "<username>"
    And Refresh the page to see watch button
    And Sign out
    And Close the application

    Examples: 
      | username    | password    | content |
      | 27829807094 | Vodacom@123 | spider  |

  @ChangePassword @Regression @Sanity
  Scenario Outline: Change Password
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When User enters username "<username>" and its password "<password>"
    Then Home page with respective user appears
    When User clicks my account from user profile
    Then Change the "<password>" for the "<username>" from My accounts page and message "<Expected message>" is displayed
    #   And Sign out
    And Close the application

    Examples: 
      | username    | password    | Expected message                                 |
      | 27829807091 | Vodacom@123 | Congratulations! Your password has been changed. |

  @Login1 @Regression
  Scenario Outline: Login with new password after being changed in Change Password section
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When User enters username "<username>" and its password "<password>"
    Then Home page with respective user appears
    And Sign out
    And Close the application

    Examples: 
      | username    | password    |
      | 27829807091 | Vodacom@123 |

  @ForgotPassword @Regression
  Scenario Outline: Forgot Password
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When user clicks on ForgotPassword link and fill the user "<username>" and "<newPassword>" details
    Then OTP box is displayed and it is entered for "<username>"
    Then successfull message "<Expected message>" is displyed and click on ok button and Change it to original "<password>" for "<username>"
    And Close the application

    Examples: 
      | username    | newPassword | password    | Expected message                              |
      | 27829807091 | Vodacom@134 | Vodacom@123 | Password successfully changed. Please log in. |

  @Login2 @Regression
  Scenario Outline: Login with new password after being changed in Forgot Password section
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When User enters username "<username>" and its password "<password>"
    Then Home page with respective user appears
    And Sign out
    And Close the application

    Examples: 
      | username    | password    |
      | 27829807091 | Vodacom@123 |

  @Registration2 @Regression
  Scenario Outline: User Registration with '0' in phone number
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When user clicks on SignUp button and fill in the details of "<username>"
    Then OTP box is displayed and it is entered for "<username>"
    And Login with the registered "<username>" and message "<Expected message>" is displayed
    Then Home page with respective user appears
    And Close the application
    And DeRegister the User "<username>"

    Examples: 
      | username   | Expected message                                      |
      | 0829807541 | Congratulations, you are now registered to Video Play |

  @TandCActivation @Regression @Sanity
  Scenario: Validate whether T&C page is accessible or not
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When user clicks on SignUp button
    Then click on the Terms and Conditions link and verify it is accessible
    Then Close Terms and Conditions window
    And Close the application

  @VerifyMostPopularRail @Regression
  Scenario Outline: Verify whether Most popular rail is present in recommendations
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When user clicks on SignUp button and fill in the details of "<username>"
    Then OTP box is displayed and it is entered for "<username>"
    And Login with the registered "<username>" and message "<Expected message>" is displayed
    Then Home page with respective user appears
    Then scroll down for the recommendations and verify most popular rail is displayed
    And Close the application
    And DeRegister the User "<username>"

    Examples: 
      | username    | Expected message                                      |
      | 27829807583 | Congratulations, you are now registered to Video Play |
