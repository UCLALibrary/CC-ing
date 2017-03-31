# Tesseract API

## Example Usage
Support for languages for both image upload and file link APIs. Use language keywords based on https://github.com/tesseract-ocr/tesseract/wiki/Data-Files, using multiple languages by delimiting with +

Support for **Image URL inputs**:

`curl -X POST http://ec2-54-173-153-28.compute-1.amazonaws.com:8080/fileAPI/imageURL -F imageURL=http://ec2-54-173-153-28.compute-1.amazonaws.com/test_images/902470068X.01.LZZZZZZZ.jpg -F languages=lao+eng`

Notes: 1. Specifying languages is optional
       2. Currently takes jpg only


Support for **Local image upload**

`curl -X POST http://ec2-54-173-153-28.compute-1.amazonaws.com:8080/fileAPI -F file=@<local file location>`

## Return format
API calls is returned as GSON: https://sites.google.com/site/gson/gson-user-guide
Metadata currently contains requested time in Epoch format, languagesUsed, and ocrResult

## Spring Boot tutorial
- https://spring.io/guides/gs/spring-boot/


## M2E
- Make sure you import your project for this project in eclipse as maven/gradle project. Otherwise, you may fail to get all dependencies
- maven clean and then maven install (maven build has no meaning)
- download maven global indexes (http://stackoverflow.com/questions/24252256/how-do-i-enable-index-downloads-in-eclipse-for-maven-dependency-search). Make sure you also do this: http://stackoverflow.com/questions/14059685/eclipse-maven-search-dependencies-doesnt-work
