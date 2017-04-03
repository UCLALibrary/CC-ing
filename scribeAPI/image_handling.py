# Image Handling Script
#
# Parse through new images found in /var/www/html/test_images
# Add new images to Scribe
# Initiate OCR

import os
import requests, subprocess
import time

start_time = time.time()
num_secs_in_day = 24 * 60 * 60
num_days_passed = 0
minutes_to_wait = 24 * 60

image_names = []
image_urls = []

OCR_POST_URL = "http://ec2-54-173-153-28.compute-1.amazonaws.com:8080/fileAPI/imageURL"

to_ocr = [] # list of urls to do ocr on still because failed first time

while(True):

    ###############################################
    curr_time = time.time()

    if curr_time - start_time < num_days_passed * num_secs_in_day:
        print "Too soon to look for new images! Waiting", minutes_to_wait, "minutes\n"
        minutes_to_wait -= 1
        time.sleep(60) # wait a minute
        continue

    print "Time to look for new images!\n"
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

    # grab all new images from server not found in Scribe and initiate OCR
    base_url = "http://ec2-54-173-153-28.compute-1.amazonaws.com/test_images/"
    with open("project/cc-ing/subjects/group_only_one_group.csv", "a") as scribeFile:
        for imageFile in os.listdir("/var/www/html/test_images"):
            if imageFile.endswith(".jpg"):
                url = base_url + imageFile
                if url not in file_contents:
                    to_write = url + ",100,100"
                    
                    # initiate OCR
                    print "OCR'ing url", url, "..." 
                    try:
                        r = requests.post(OCR_POST_URL, data={'imageURL': url})
                        print r.json()
                    except:
                        print "ERROR !!! - Could not OCR image at", url, "\n"
                        to_ocr.append(url)
                    else:
                        scribeFile.write(to_write + "\n")
                        print "Successfully OCR'd image and added to Scribe!", "\n"  

        # attempt to ocr images that failed in past
        temp = []
        for url in to_ocr:
            to_write = url + ",100,100"
            print "OCR'ing url", url, "..." 
            try:
                r = requests.post(OCR_POST_URL, data={'imageURL': url})
                print r.json()
            except:
                print "ERROR !!! - Could not OCR image at", url, "\n"
                temp.append(url)
            else:
                scribeFile.write(to_write + "\n")
                print "Successfully OCR'd image and added to Scribe!", "\n"  
        to_ocr.extend(temp)

    subprocess.call(['rake', 'project:load[cc-ing, subjects]'])
        
# end while

