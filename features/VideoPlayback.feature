Feature: This feature contains VideoPlayBack for different video types

  @VODContent @Regression @Sanity
  Scenario Outline: VoD Content to checked with Anonymous and authenticating user
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User Searches for the "<content>"
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
      | username    | password    | content |
      | 27822384348 | Vodacom@123 | Pirates |

  @PurchaseHistory @Regression
  Scenario Outline: Verify purchase history details of a user
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When User enters valid username "<username>" and its password "<password>"
    Then Home page with respective user appears
    And User Searches for the "<content>"
    When The content is being purchased for Rent
    Then OTP box is being displayed and it is entered for "<username>"
    And Refresh the page to see watch button
    When User clicks my account from user profile
    Then Click on current subscriptions link
    Then Verify purchase details of the user
    And Close the application

    Examples: 
      | username    | password    | content |
      | 27829807598 | Vodacom@123 | spider  |

  @CancelSubscription @Regression @Sanity
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
      | username    | password    | error message                                                                                                                 |
      | 27829807598 | Vodacom@123 | This subscription is for a limited period and will expire on the date shown. It will not automatically renew after this time. |

  @ThirdPartyApps @Regression @Sanity
  Scenario Outline: Launch the 3rd party app Twitter to share
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When User enters valid username "<username>" and its password "<password>"
    Then Home page with respective user appears
    And User Searches for the "<content>"
    Then Share the content using "<App>"
    And Sign out
    And Close the application

    Examples: 
      | username    | password    | App     | content      |
      | 27829807598 | Vodacom@123 | Twitter | Wonder Woman |

  @VideoFormat/Controls @Regression @Sanity
  Scenario Outline: Video PlayBack
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When User enters valid username "<username>" and its password "<password>"
    Then Home page with respective user appears
    And User Searches for the "<content>"
    Then Play the content and perform video actions on it
    And Sign out
    And Close the application

    Examples: 
      | username    | password    | content |
      | 27822384348 | Vodacom@123 | spider  |

  @DRMVideoPlayback @Regression
  Scenario Outline: Play DRM video
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When User enters valid username "<username>" and its password "<password>"
    Then Home page with respective user appears
    And User Searches for the "<content>"
    Then Play the content for some time and close
    And Sign out
    And Close the application

    Examples: 
      | username    | password    | content                         |
      | 27829805011 | Vodacom@123 | Dash Widevine Encrypted content |

  @SVODVideoPurchasePP @Regression @Sanity
  Scenario Outline: SVOD video purchase and Playback
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When User enters valid username "<username>" and its password "<password>"
    Then Home page with respective user appears
    And User Searches for the "<content>"
    Then User subscribes the video with no balance and Displays Following "<Expected Message>"
    And Close the application

    Examples: 
      | username    | password | content | Expected Message               |
      | 27829805009 | Test@123 | Pirates | Your account is Out of Balance |

  @TVODVideoPurchasePP @Regression @Sanity
  Scenario Outline: TVOD video purchase and Playback
    Given Application URL is url
    When Open the Application in given browser
    Then Landing page is displayed
    Given User enters PIN to go to Home Page
    And User clicks on Login icon on top rght hand side
    When User enters valid username "<username>" and its password "<password>"
    Then Home page with respective user appears
    And User Searches for the "<content>"
    When The content is purchased for Rent with no balance and Displays Following "<Expected Message>"
    And Close the application

    Examples: 
      | username    | password | content     | Expected Message               |
      | 27829805009 | Test@123 | Baby Driver | Your account is Out of Balance |
