
# CRUD Operations with Tournament Editor

## Tournament Creation

- Todo: updating eventContainer after server success
    * add handler to ajax: that is enough and easy
    * note: this can only be done once the 4th step is completed
- Todo: notify user about event creation
    * add to the previous handler
    * do last when operation succeeded or failed
- Todo: handle server error
    * as previous but add an "error handler"
- Todo: take care that also Tournament will be created
    * first create an event and then a tournament. Finally bind the events 
      to a 1-1 relation

## Tournament Reading

- Todo: clicking an event opens the dialog with the event data
- Comment: Basicly same as editor opening

## Tournament Update

- Todo: perform REST and capture success
- Todo: update eventContainer
- Todo: notify user
- Todo: let user upload PBN files
- Todo: Enable user to remove uploaded files
- Todo: Show user names of upload files if needed, perhaps a new view is needed


## Tournament Delete

- Todo: remove calendar event
- Todo: remove the Tournament instance
- Todo: handle master points
- Todo: handle uploaded PBN files
- Comment: Remove master points automatically if PBN files are removed
- Comment: PBN file upload should

# About Notification

- Todo: learn Bootstrap Toasts

