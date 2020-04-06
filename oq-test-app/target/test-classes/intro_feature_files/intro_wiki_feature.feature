Feature: Checking on Wikipedia

  Background: Browser Version
    Given a webbrowser

  Scenario: Searching a page
    Given the website "https://www.wikipedia.org" is displayed
    And the user enters "water pipit" into the field "searchInput"
    When the user clicks on the submit button
    Then a page containing the following text should be displayed
    """
    The water pipit (Anthus spinoletta) is a small passerine bird which breeds in the mountains of Southern Europe
    and the Palearctic eastwards to China. It is a short-distance migrant; many birds move to lower altitudes or wet
    open lowlands in winter.
    """


  Scenario: Digging deeper
    Given the website "https://en.wikipedia.org/wiki/Water_pipit" is displayed
    When the user clicks on the link "passerine"
    Then the website with title "Passerine - Wikipedia" should be displayed