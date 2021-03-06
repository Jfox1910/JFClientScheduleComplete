
Josh Fox
Student ID: #001053580
jfox76@wgu.edu

2/28/2022 to 5/3/22

TITLE:
Appointment Scheduling System.

PURPOSE:
This program is designed to assist in the scheduling of appointments for a company. It allows the the user to add, update and delete customers and appointments.
It contains a login form that can be in English and French based on the users language settings. It offers 3 reports. An output of appointments by "type", a schedule tailored to
each individual contact in the system and a 3rd report giving the total amount of appointments and customers in the system.

SCENARIO:
You are working for a software company that has been contracted to develop a GUI-based scheduling desktop application. The contract is with a global consulting organization that
conducts business in multiple languages and has main offices in Phoenix, Arizona; White Plains, New York; Montreal, Canada; and London, England. The consulting organization has provided
a MySQL database that the application must pull data from. The database is used for other systems, so its structure cannot be modified.

The organization outlined specific business requirements that must be met as part of the application. From these requirements, a system analyst at your company created solution statements
for you to implement in developing the application. These statements are listed in the requirements section.

Your company acquires Country and First-Level-Division data from a third party that is updated once per year. These tables are prepopulated with read-only data.
Please use the attachment “Locale Codes for Region and Language” to review division data. Your company also supplies a list of contacts, which are prepopulated in the Contacts table;
however, administrative functions such as adding users are beyond the scope of the application and done by your company’s IT support staff.
Your application should be organized logically using one or more design patterns and generously commented using Javadoc so your code can be read and maintained by other programmers.

VERSION: 1.1
OpenJDK Version 17.0.2

Built with:
IntelliJ IDEA 2021.3.3 (Ultimate Edition)
Build #IU-213.7172.25, built on March 15, 2022
Licensed to Joshua Fox
Subscription is active until June 29, 2022.
For educational use only.
Runtime version: 11.0.14.1+1-b1751.46 amd64
VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.
Windows 11 10.0
GC: G1 Young Generation, G1 Old Generation
Memory: 1974M
Cores: 16
Registry:
    ide.win.file.chooser.native=true

Non-Bundled Plugins:
    com.tylerthrailkill.intellij.solarized (3.0.0)
    com.samdark.intellij-visual-studio-code-dark-plus (2.5)
    com.markskelton.one-dark-theme (5.5.1)
    com.jetbrains.edu (2022.2-2021.3-864)
    com.intellij.javafx (1.0.4)
    org.jetbrains.kotlin (213-1.6.10-release-961-IJ6777.52)


INSTRUCTIONS FOR USE:
This version of the program must be run from within Intellij using the following libraries and jar files:

-> Add the MySql Connector Driver -> "8.0.28.jar" to -> File\Project Structure...\Modules NOTE version 8.0.28 must be used.

-> Ensure that you're using the correct Java version -> OpenJDK Version 17.0.2. This can be selected in -> File\Project Structure...\Modules.

->  JavaFX Version 17.0.1 added to: File\Project Structure...\Libraries Click the + button over the left pane and navigate to the folder containing
your javafx "lib" folder. For example C:\Program Files\Java\openjfx-17.0.1_windows-x64_bin-sdk\javafx-sdk-17.0.1\lib and follow the prompts.

-> The user must add the correct path to: Run\Edit Configurations\Modify options\Add VM Options
Using the following path edited to your local installation folder:

--module-path
"C:\openjfx-17.0.1_windows-x64_bin-sdk\javafx-sdk-17.0.1\lib" (YOUR PATH MAY BE DIFFERENT)
--add-modules
javafx.controls,javafx.fxml

-> Upon loading to the Login screen use the following
USERNAME: test
PASSWORD: test
Note the user "test" must already be in the database. This assumes that you are using the pre-existing DB that was given
in the class. Additionally a "user" with different credentials may be added to the user table in the DB and would have similar access.

There are several lambda functions including:
LAMBDA FUNCTIONS in ReportsController line #138
and on AddCustomerController line # 77

-> The 3rd report is a tally of all exisiting appointments and customers in the database. It consists of 2 textfields that self
populate with the totals upon opening the report screen.