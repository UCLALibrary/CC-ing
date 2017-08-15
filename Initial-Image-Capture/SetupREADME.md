The full process is described via the following Google Slides by Calvin Ha: https://docs.google.com/presentation/d/1RhPzRCBMn6VyBv0Hj1DoxcpD3o8EA8QBqeb4y_QMB9U/edit#slide=id.p 

For the purpose of reiteration however, the steps are further reiterated & broken down below.
The image capturing process will be completed in 3 phases:
1) Initial Image Capture via DSLR/GoPro/Phone camera 
       
       -Images to be captured: the Barcode, the Title Page, the Inner Cover Page, the Last page
       
       -The Last Page is recorded to document the number of total pages in the book/work
       
       -Ideally, images will be taken in the following order from first to last: Barcode, Title, Inner Cover Page, Last page. This will
        quick sorting of images during Phase 2 "Transfer of Image" by using  code to quickly recognize barcodes and differentiate
        them from other pictures for naming purposes. If the images are taken in order & organized in order, it'll simplify the process
        of naming files quickly because we already know what order the images would arrive in & would only need to determine the Barcode ID
        associated with the book before naming the images.
       
       -In the event that not all 4 image types (barcode,title,inner cover, last page) are taken, we will need some way of further
        identifying each image type because we would then be missing some image & that'd throw off the image sorting & naming process
2) Transfer of Image to a server/computer for file naming & sorting
        -The DSLR/GoPro/Phone Camera shall transfer the captured images via WiFi to a PC/server where they shall be named using the barcode
         tag for the book & the associated lables for each type of image (T for Title, C for Inner Cover, L for Last, and B for Barcode)
         -The Barcode servers as the baseline for identifying the book as well as the images associated with the book. (EX: If the barcode
        is "00004SB7XC" then the image file for the book's barcode is "00004SB7XC_B.jpg" & the book's title image is "00004SB7XC_T.jpg"
       
       -The image naming process may be accomplished using a batch file script to rename images based on the Barcode ID associated with
        the book & the corresponding label (B/T/C/L) associated with the image
       
       -The Barcode may be identified & processed into an inputtable string using OpenCV & Python code as described in the following
        repo provied by: https://medium.com/@yushulx/raspberry-pi-barcode-scanner-in-python-927839100c6b
3) Feeding of files to Django web server for language sorting & categorization (final step before passing to Tesseract-Scribe-Workflow).
        -Uploads can be accomplished using the Django Uploader framework previously set forth in the UCLA/CC-ING GitHub by previous 
        members.
       
       -Additionally, we may be able to set the server to request files from the PC/Server which we are uploading images to using Python 
        HTTP Request commands as described here: http://docs.python-requests.org/en/latest/index.html
        
