HealthySense: Doctor Patient Appointment System
Welcome to HealthySense, a JavaFX Maven project for managing doctor-patient appointments. This application allows doctors and patients to schedule, view, and manage (cancel, reschedule) appointments, and review doctors conveniently. 
This README file provides an overview of the project and instructions for setting it up and running it on your local machine.

Features

HealthySense offers the following features:

User Roles: The system supports two user roles: doctors and patients.

Appointment Management: Doctors can manage their schedules and view consultation, Patients can schedule appointments (view,cancel,reschedule) with their preferred doctors.
Search: Users can search for doctors based on various criteria such as specialization, location, and availability and filter their search based on ratings and specializations.
User Profiles: Users can create and manage their profiles, including personal information and medical history, and certifications for doctors.

Prerequisites
Before running the HealthySense application, ensure that you have the following dependencies installed on your machine:

Java Development Kit (JDK) 11 or higher
Apache Maven
JavaFX SDK

Tested on VSCODE

Setup (VSCODE)
To set up and run the HealthySense application, follow these steps:

After Cloning the repository to your local machine using the following command:
bash
Navigate to the project directory:
Open the project in VSCODE

Build the project using Maven.
Run the application:


Configuration
The HealthySense application requires a database for storing data. By default, it is configured to create and use a database on SQLSERVER.
However, you can modify the configuration to use a different database by editing the application.properties file located in the src/main/resources directory.

Here is an example of the default configuration:


# Other Configuration Options
# ...
Make sure to provide the necessary credentials and connection details if you are using a different database.

Usage
Once the HealthySense application is up and running, you can start the project.
From there, you can explore the different functionalities provided by the application based on your user role (doctor or patient). You can schedule appointments, manage your profile, search for doctors, and perform other relevant actions.


Authors
Muhammad Abdullah
Muhammad Musa Haroon Satti
Moiz Akhtar

Contributing
We welcome contributions to the HealthySense project. If you would like to contribute, please follow these steps:

Fork the repository on GitHub.
Create a new branch for your feature or bug fix.
Commit your changes with descriptive commit messages.
Push your branch to your forked repository.
Submit a pull request to the original repository.

License
HealthySense is released under the MIT License. Feel free to use, modify, and distribute the code as per the terms of the license.

Contact
If you have any questions, suggestions, or feedback regarding the HealthySense project, please contact us at m18ab03@gmail.com

Thank you for choosing HealthySense for your doctor-patient appointment system! We hope you find it useful and easy to use.