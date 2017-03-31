# every day, go through all images stored in box, get new links to images
# for each image found, if the image link is not in group_only_one_group.csv, initiate OCR with it

import requests
import subprocess
import time
import urllib
import urllib2


start_time = time.time()
num_secs_in_day = 24 * 60 * 60
num_days_passed = 0
minutes_to_wait = 24 * 60
num_new_img_urls_found = 0

while(True):    
    curr_time = time.time()

    if curr_time - start_time < num_days_passed * num_secs_in_day:
        print "Too soon to look in Box! Waiting", minutes_to_wait, "minutes\n"
        minutes_to_wait -= 1
        time.sleep(60) # wait a minute
        continue

    print "Time to look in Box!"
    minutes_to_wait = 24 * 60
    num_days_passed += 1
    
    base_url = "https://ucla.app.box.com/s/kanug6fkviyncr9onmjkaq4xew3x4zek/"
    ending = "/21210641374"

    new_img_urls = []

    # get all existing urls from group_only_one_group.csv
    file_contents = ""
    with open("project/cc-ing/subjects/group_only_one_group.csv", "r") as file:
        file_data = file.readlines()
        for line in file_data:
            file_contents += line

    # get all images from box
    for i in range(1,6):
        webpage_url = base_url + str(i) + ending
        html = urllib2.urlopen(webpage_url).read()
    
        next_start_loc = 0
        next2_start_loc = 0

        while (True):
            class_loc = html.find("item_link item_name_text", next_start_loc)
            if class_loc == -1:
                break # no more images found on page
            else:
                next_start_loc = class_loc + 10

            str1 = html[class_loc - 95: class_loc + 20]
            start = str1.find("href=")
            end = str1.find("class")
            path = str1[start + 7 : end - 3].replace("\\","")
        
            url_img_prev = "http://ucla.app.box.com/" + path
            #    print url_img_prev
            html_img_prev = urllib2.urlopen(url_img_prev).read()

            loc1 = html_img_prev.find(".jpg?shared_name=kanu", next2_start_loc)
            if loc1 == -1:
                break # no more images found on page
            else:
                next2_start_loc = loc1 + 10

            loc2 = html_img_prev.find("/representat",loc1-100,loc1+100)
            loc3 = html_img_prev.find("&amp;", loc1, loc1 + 100)
        
            raw_url_img = "http://box.com" + html_img_prev[loc2:loc3]
            url_img = raw_url_img.replace("\\","")
        
            num_new_img_urls_found += 1
            print "num found:", num_new_img_urls_found
            print url_img

            if url_img not in file_contents:
                new_img_urls.append(url_img)

            #if num_new_img_urls_found >= 1:
            #    break
    #if num_new_img_urls_found >= 1:
    #    break


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
   

def add_URLs(urls):
    # get all existing urls from group_only_one_group.csv
    file_contents = ""
    with open("project/cc-ing/subjects/group_only_one_group.csv", "r") as file:
        file_data = file.readlines()
        for line in file_data:
            file_contents += line
    # add in newly found unique urls to group_only_one_group.csv
    num_added = 0
    with open("project/cc-ing/subjects/group_only_one_group.csv", "a") as file:
        for url in urls:
            if url not in file_contents:
                file.write(url)
                file.write("\n")
                print "Added", url
                num_added += 1
    return num_added

print "\n", len(new_img_urls), "new URLs found!"
num_added = add_URLs(new_img_urls)
print "Completed adding", num_added, "images!\n"

print "-------------------------"

print "Doing OCR on new images!"
num_ocr = 0
for url in new_img_urls:
    do_OCR(url)
    num_ocr += 1
print "OCR done on", num_ocr, "images!\n"

