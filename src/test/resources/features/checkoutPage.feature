Feature: CHECKOUT


  @visa
  Scenario: visa
    Given Go to 'https://ais.usvisa-info.com/en-ca/niv/users/sign_in' page
    And Wait 6 Second
   # And Press on '(//button[contains(@class,'ui-button ui-corner-all')])[2]' Button By Xpath  Click on the Secure Checkout
    And Scroll To Element By Xpath '//div[contains(@class,'icheckbox icheck-item')]'
    And Enter 'kornulo@gmail.com' text by id 'user_email' Field
    And Enter 'Maluy013+' text by id 'user_password' Field
    And Press on '//div[contains(@class,'icheckbox icheck-item')]' Button By Xpath  Click on the Secure Checkout
    And Press on '//input[@class='button primary']' Button By Xpath  Click on the Secure Checkout
    And Press on '(//a[@href='/en-ca/niv/account'])[2]' Button By Xpath  Click on the Secure Checkout
    And Press on '//a[contains(@class,'button primary')]' Button By Xpath  Click on the Secure Checkout
    And Press on '//h5[text()[normalize-space()='Reschedule Appointment']]' Button By Xpath  Click on the Secure Checkout
    And Press on '//a[@href='/en-ca/niv/schedule/49885979/appointment']' Button By Xpath.*
    And Press on '//input[@class='required hasDatepicker']' Button By Xpath.*
    And Press on Calendar By Xpath.*
    And Press on '(//li[contains(@class,'select input')]//select)[2]' Button By Xpath.*
    And Press on '((//li[contains(@class,'select input')]//select)[2]/option)[2]' Button By Xpath.*
    And Press on '//input[@class='button primary']' Button By Xpath.*
    And Wait 30 Second
    And Press on '//a[@class='button alert']' Button By Xpath.*











