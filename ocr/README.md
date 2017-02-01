# Examples

Tesseract API:
Support for languages for both image upload and file link APIs. Use language keywords based on https://github.com/tesseract-ocr/tesseract/wiki/Data-Files, using multiple languages by delimiting with +
example command `curl -X POST http://ec2-54-173-153-28.compute-1.amazonaws.com:8080/fileAPI/imageURL  -F imageURL=http://jeroenooms.github.io/images/testocr.png -F languages=lao+eng`

Support for URL inputs via API! Currently takes jpg and png links
example command `curl -X POST http://ec2-54-173-153-28.compute-1.amazonaws.com:8080/fileAPI/imageURL  -F imageURL=http://jeroenooms.github.io/images/testocr.png`

Support for local image upload
example command `curl -X POST http://ec2-54-173-153-28.compute-1.amazonaws.com:8080/fileAPI -F file=@<file location>`

# Spring Boot tutorial
- https://spring.io/guides/gs/spring-boot/


# M2E
- maven clean and then maven install (maven build has no meaning)
- download maven global indexes (http://stackoverflow.com/questions/24252256/how-do-i-enable-index-downloads-in-eclipse-for-maven-dependency-search). Make sure you also do this: http://stackoverflow.com/questions/14059685/eclipse-maven-search-dependencies-doesnt-work

