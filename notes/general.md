
# Security

   ## General Info
   - https://docs.spring.io/spring-security/site/docs/5.1.5.RELEASE/reference/htmlsingle/#tech-intro-authentication

   ## JDBC Authentication
   
   - https://docs.spring.io/spring-security/site/docs/current/reference/html5/ 
   - 6.9.2. JDBC Authentication
   
   ## MariaDB Default Schema 
   
   - See the above link: Appendix 15
   - Current schema works by a test
   
   ### TODOs
   
   - create a way to add new users and bind it to a player 
   - authenticate the views and the repos and do the tests 
   - requirements:
     * checks validity of new user (username and passwords)
     * allows an existing user to update password 
     * allows admin to disable the user
     * allows admin to soft delete the user
     - if creating a JPA repo does it support user creation?
     - if so how can I get jdbc manager?
      
   
   