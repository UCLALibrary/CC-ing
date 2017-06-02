# Tutorial
If you follow this step-by-step tutorial (or the [video series on YouTube](https://www.youtube.com/watch?v=hMiNTCIY7dw)) you should be able to re-create the app which is provided in this repository.

## Before you start...

For an optimal tutorial experience I recommend that
 1. you have mastered the [Django Tutorial Polls App](https://docs.djangoproject.com/en/1.10/intro/tutorial01/)
 1. you know your way around IDEs and editors (this tutorial is not about PyCharm, we only use it; any other editor - e.g. sublime text - should work fine too)
 1. you have a basic knowledge on how to use Linux Shell
 1. you know how to install packages on your system
 1. you are comfortable using [virtual environments](http://docs.python-guide.org/en/latest/dev/virtualenvs/) (you can probably work without them, but it is highly recommended to use them!)
 1. you know how to debug your web application in a Browser (we are using FireFox here, but any other Browser should work too)
 1. you can do basic programming tasks in JavaScript and Python (I recommend at least 3 months experience in both)
 
As a warning: This written tutorial is very superficial. I will however create a YouTube video about it, which I will link within this repository.


## Chapter 1: The Base Application
In this chapter we are going to set up the development environment and create a basic REST API as well as a frontend for it.

 * [Step 1](chapter1/step1.md): Set Up the Basic Django Project Structure
 * [Step 2](chapter1/step2.md): Create the `UploadedImage` Model and Migrations
 * [Step 3](chapter1/step3.md): Create a Basic RESTful Endpoint for `UploadedImage`
 * [Step 4](chapter1/step4.md): Uglify/scramble Image names on Upload to Avoid Duplicates
 * [Step 5](chapter1/step5.md): Prepare the Frontend Application
 * [Step 6](chapter1/step6.md): Set up a Basic AngularJS Single Page Application
 * [Step 7](chapter1/step7.md): Uploading Images using AngularJS
 * [Step 8](chapter1/step8.md): Deleting an Uploaded Image
 * [Step 9](chapter1/step9.md): Getting Bootstrap CSS Theme for a nicer Look and Feel
 
 
## Chapter 2: Generating Thumbnails and Adding Title and Description
In this chapter we are going to add more fields to our data model and automatically generate thumbnails for the uploaded images.

 * [Step 1](chapter2/step1.md): Automatically Creating the Thumbnail in the Django Model
 * [Step 2](chapter2/step2.md): Extending the Backend and the Frontend to provide the Thumbnail
 * [Step 3](chapter2/step3.md): Adding a Title and Description to each Image
 * [Step 4](chapter2/step4.md): Preview the image before uploading
 * [Step 5](chapter2/step5.md): Fixing the flickering when deleting images
 * [Step 6](chapter2/step6.md): Adding toast notifications

## Chapter 3: User Management
In this chapter we are going to add users and authentication to our project. 

## Chapter 4: Comments
Now that we have users, we can let users comment on pictures.