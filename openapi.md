openapi: 3.0.3
info:
  title: MAD Project Backend
  description: "MAD Backend API"
  version: "1.0.0"

tags:
- name: Account
  description: To be fill in
- name: Attendance
  description: To be fill in
- name: Calendar
  description: To be fill in
- name: Home
  description: To be fill in
- name: Launch
  description: To be fill in
- name: Leave
  description: To be fill in
- name: NFC
  description: To be fill in
- name: Virtual Business Card
  description: To be fill in

paths:
  /jira/onboard/project:
    post:
      tags:
        - Onboarding
        - Ticket Controller
      description: |
        The following environment variables that requires an update in the .env file for the 'postProjOnboardTicket' lambda function
          - TICKET_CONTROLLER_DOMAIN: base url of the Jira board containing the tickets
          - TICKET_CONTROLLER_BOARD_KEY: the identifier of the Jira board
          - ATLASSIAN_PRIVATE_TOKEN: the API token used for making API calls to Atlassian products
      requestBody:
        content:
          multipart/form-data:
            schema:
              allOf:
                - $ref: "#/components/schemas/AddProjectOnboardInput"
              required:
                - onboardFormDataJSON
      responses:
        200:
          description: Successfully created project onboarding ticket in the Jira board.
          content:
            application/json:
              schema:
                type: string
                example: Project onboarding ticket created successfully...
        400:
          description: Failed to create project onboarding ticket in the Jira board.
          content:
            application/json:
              schema:
                type: string
                example: Failed to create ticket...