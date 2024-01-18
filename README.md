# eazy-bank

Eazy Bank provides end users to fetch user details, balance enquiry, last transactions, my cards and loans.

This API connects with Keycloak for identity verification. All users are maintained in Keycloak.

When end user logs through portal, user will be redirected to Keycloak(Authorization Server) home page.
User enters his credentials, if credentials are valid then Keycloak issues access token to client (Portal).
Then client requests the resources from Eazy Bank application (Resource Server) with the help of acess
token issued by Keycloak. Eazy Bank can identify whether access token is issued by Auth server or not by
validating the signature with public certificate issued by Auth server(Keycloak).
