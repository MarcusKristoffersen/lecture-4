# Forelesning 4: Http server

HTTP request ser ut

## Repeat av lecture 2

* [x] Maven
* [x] Github Actions
* [x] Working branch
* [x] Pull request
* [x] Test report (Maven (--fail-at-end
  - name: Publish test report
  uses: scacap/action-surefire-report@v1
  if: ${{ always () }}))

## Repeat av lecture 3: Socket, HTTP

* [x] Socket connects server
* [x] Status code
* [x] Header fields
* [x] Content-length
* [x] Message body

## TODAY: HttpServer

* [x] HttpServer should respond with 404
* [x] HttpServer should include request target in 404
* [x] Return a static content for /hello
* [x] Content-type
* [x] Return HTML file from disk
* [x] Return <form>
* [x] Process GET request for form

## Lecture 6:

* [x] styling
* [x] handle more than one reques
* [x] GET requests with more than one field
* [x] Refactor -> HttpMessage class
* [x] Process POST request from form

## Lecture 9:

* [x] Make executable JAR
* [x] Serve HTML from jar-file
* [ ] PersonDaoTest add person in database: list
* [ ] RoleDaoTest add person in database: list
* [ ] /api-requestTarget => Controllers
* [ ] Password in properties-file

## Problems left for exam

* [ ] Error handling
* [ ] URL encoding
* [ ] Update existing data in the database
* [ ] GET /api/people should return all people, 
POST api/people should create new person