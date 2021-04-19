Feature: Health Endpoint
    Background:
    * url baseUrl

    Scenario: access health endpoint
        Given path "/health/full"
        When method get
        Then status 200
