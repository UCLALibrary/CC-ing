# Image Handling Script
#
# Parse through new images found in /var/www/html/test_images
# Add new images to Scribe
# Initiate OCR

import os
import subprocess
import time

start_time = time.time()
num_secs_in_day = 24 * 60 * 60
num_days_passed = 0
minutes_to_wait = 24 * 60

image_names = []
image_urls = []

while(True):

    ###############################################
    curr_time = time.time()

    if curr_time - start_time < num_days_passed * num_secs_in_day:
        print "Too soon to look for new images! Waiting", minutes_to_wait, "minutes\n"
        minutes_to_wait -= 1
        time.sleep(60) # wait a minute
        continue

    print "Time to look for new images!"
    minutes_to_wait = 24 * 60
    num_days_passed += 1

    ###############################################

    # get all existing image urls from Scribe
    file_contents = ""
    with open("project/cc-ing/subjects/group_only_one_group.csv", "r") as file:
        file_data = file.readlines()
        for line in file_data:
            file_contents += line

    ###############################################

    # grab all new images from server not found in Scribe
    base_url = "http://ec2-54-173-153-28.compute-1.amazonaws.com/test_images/"
    with open("project/cc-ing/subjects/group_only_one_group.csv", "a") as scribeFile:
        for imageFile in os.listdir("/var/www/html/test_images"):
            if imageFile.endswith(".jpg"):
                url = base_url + imageFile
                if url not in file_contents:
                    to_write = url + ",100,100\n"
                    scribeFile.write(to_write)
                    print to_write
                    
# end while

########################################################

def do_OCR(url):
    print "wget'ing from Box img", url
    subprocess.check_call(["wget", url])
    subprocess.check_call(["mv", "thumb_94.jpg?shared_name=kanug6fkviyncr9onmjkaq4xew3x4zek", "to_be_ocrd.jpg"])

    params = {'file': "@to_be_ocrd.jpg"}
    POST_URL = "http://ec2-54-173-153-28.compute-1.amazonaws.com:8080/fileAPI"

    print "POST'ing to ocr img:", url
    subprocess.check_output(["curl", "-X POST", POST_URL, "-F file=@to_be_ocrd.jpg"])
                             
    #r = requests.post(POST_URL, data=params)
    #print str(r.text)

    print "Done OCR'ing URL", url
    subprocess.call(["rm", "to_be_ocrd.jpg"])
