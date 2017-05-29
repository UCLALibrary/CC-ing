import json
import urllib
import dicttoxml
import requests


#page = urllib.urlopen('http://ec2-54-173-153-28.compute-1.amazonaws.com:8000/subjects')
#content = page.read()
#obj = json.loads(content)


#Instead of using loads which requires a string use, read(), we can just use requests. 

page = requests.get('http://ec2-54-173-153-28.compute-1.amazonaws.com:8000/subjects')
obj = page.json()

# Parse only this information
# ID of image
# URL of image
# Most popular image for author
# Most popular image for title
dataDict = []

author = []
titles = {} 

#  To handle dictionary of list of dictionary
for key, value in obj.items():
	# data is the only meaningful key
	if(key == "data"):
		# print("KEY: " + str(key))
		# print("VALUES: " + str(value))
		print("\n\n\n\n\n")

		# To handle list of dictionary
		count = 0
		for i in value:
			print("i: " + str(i))
			print("ID of the image: " + str(value[count]["subject_set_id"]))
			print("URL:" + str(value[count]["location"]["standard"]))

			if(value[count]["type"] == "em_transcribed_author"):
				author.append(value[count]["data"]["values"][0]["value"])
			
			if(value[count]["type"] == "em_transcribed_title"):
				titles[str(value[count]["data"]["values"][0]["value"])] = 1
			
			count += 1
			print("\n\n\n\n\n")


print("author: " + str(author))
print ("title: " + str(titles))


# print(obj)
# print("\n\n\n\n\n\n\n\n\n\n")

#xml = dicttoxml.dicttoxml(obj)
#print(xml)
