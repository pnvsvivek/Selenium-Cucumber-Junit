Feature: This feature contains VideoPlayBack for different video types

  @VODContent @Regression
  Scenario Outline: VoD Content to checked with Anonymous and authenticating user
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And Search for the "<content>"
    And SignIn button is displayed for the content for anonymous user
    Given User clicks on Home Icon
    Then Capture the trays that are available
    And User clicks on Login icon on top rght hand side
    When User enters valid username "<username>" and its password "<password>"
    Then Home page with respective user appears
    And Compare Same trays are avilable even after logging in with user
    And Sign out
    And Close the application

    Examples: 
      | username    | password | content                         |
      | 27822384348 | Test1234 | Dash Widevine Encrypted content |

  @PurchaseHistory @Regression
  Scenario Outline: Verify purchase history details of a user
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When User enters valid username "<username>" and its password "<password>"
    Then Home page with respective user appears
    And Search for the "<content>"
    Then User subscribes the video
    Then OTP box is displayed and it is entered
    When User clicks my account from user profile
    Then Click on current subscriptions link
    Then Verify purchase details of the user
    And Close the application

    Examples: 
      | username    | password | content              |
      | 27829807583 | Test1234 | 50 Cent - In Da Club |

  @CancelSubscription
  Scenario Outline: Cancel subscription
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When User enters valid username "<username>" and its password "<password>"
    Then Home page with respective user appears
    When User clicks my account from user profile
    Then Click on current subscriptions link
    Then Click on unsubscribe link to cancel subscription
    Then verify error message as "<error message>"
    And Close the application

    Examples: 
      | username    | password | error message                                                                                                                 |
      | 27829807583 | Test1234 | This subscription is for a limited period and will expire on the date shown. It will not automatically renew after this time. |

  @ThirdPartyApps @Regression
  Scenario Outline: Launch the 3rd party app to share
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When User enters valid "<username>" and password
    Then Home page with respective user appears
    And Search for the "<content>"
    Then Share the content using "<App>"
    And Sign out
    And Close the application

    Examples: 
      | username    | App     | content      |
      | 27829807989 | Twitter | Wonder Woman |
      
     @VideoFormat/Controls @Regression
  Scenario Outline: Launch the 3rd party app to share
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When User enters valid username "<username>" and its password "<password>"
    Then Home page with respective user appears
    And Search for the "<content>"
    Then Play the content and perform video actions on it
    And Sign out
    And Close the application

   Examples: 
      | username    | password | content|
      | 27822384348 | Test1234 | Luther |

