# senlatest
SENLA technical test

The task:
Implement a simple webapp, when you can:
Upload an xml file that contains a list of books, example would be.
 
<Books>
  <Book isbn="12383929">
    <Title>Harry Walter</Title>
    <Author>James Romanov</Author>  
  </Book>
  ...
</Books>
 
Display the content of an uploaded xml file as a table. It might be as simple as an html page and 2 buttons, without ajax or anything.
 
Add the capability to add/remove/edit a book in a table.
 
Add the capability to download the table contents as an xml file.
 
Please provide instructions on how to launch the project.
Host it on github (public or private repo — as you choose) and make sure you commit your work regularly, not just push completed work at the end.
You have 3-4 days to complete the task. We are not looking for perfect code, but to see your ability to reason, solve tasks, and progress.
 
Bonus part: add search and sort capabilities to the table.

////

How to launch a project?

If using IDE (IntelliJ):
- File - New - Project from version control
- Apply URL of this project
- "Clone"
- When you have all the files, press green arrow or Shift+F9

If using command line
- Download as zip, extract the content in directory
- Navigate to that directory (you will know you are in right place when you see a file mvnw.cmd there)
- Type "mvnw spring-boot:run"(without "")
- In browser, go to "localhost:8080" to see the app
