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

dataDict = {"id": [], "url": [], "author": [], "title": []}
# Parse only this information
# ID of image
# URL of image
# Most popular image for author
authors = {}
# Most popular image for title
titles = {}
				#This is test data for my popular title/author for-loop stuff.
authors["DaLarm"] = 1
authors["Matt"] = 10
titles["dhan"] = 5
titles["MattLin"] = 10

#data = set()


max = 0
tmax = 0
name = ""
tname = ""

for key in authors:
                                        #Self-explanatory. This takes out the popular author and title.
	if(authors[key] >= max):
		print("current name: " + name)
		print("current max: " + str(max))
		name = key
		max = authors[key]

#data.add(name)

for key in titles:
	print(tname)
	if(titles[key] >= tmax):
		tname = key
		tmax = titles[key]


#data.add(tname)


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
			# print("i: " + str(i))
			# print("ID of the image: " + str(value[count]["subject_set_id"]))
			if(str(value[count]["subject_set_id"]) != ""):
				dataDict["id"].append(str(value[count]["subject_set_id"]))
			# print("URL:" + str(value[count]["location"]["standard"]))
			if(str(value[count]["location"]["standard"]) != ""):
				dataDict["url"].append(str(value[count]["location"]["standard"]))

			if(value[count]["type"] == "em_transcribed_author"):
				if str(value[count]["data"]["values"][0]["value"]) in authors:
					authors[str(value[count]["data"]["values"][0]["value"])] += 1
				else:
					authors[str(value[count]["data"]["values"][0]["value"])] = 1

			if(value[count]["type"] == "em_transcribed_title"):
				if str(value[count]["data"]["values"][0]["value"]) in titles:
					titles[str(value[count]["data"]["values"][0]["value"])] += 1
				else:
					titles[str(value[count]["data"]["values"][0]["value"])] = 1

			count += 1
			# print("\n\n\n\n\n")




dataDict["author"].append(name) #name and tname holds the popular author/title. I will be revising this once we have actual test data.
dataDict["title"].append(tname) 


#print(data)


	# print("authors: " + str(authors))
	# If authors is NOT empty
#	if(authors):
#		dataDict["author"].append(authors)
	# print ("titles: " + str(titles))
#	if(titles):
#		dataDict["title"].append(titles)

	# Now, we have all the assorted metadata


#for key, value in dataDict.items():
#cleanedJSON = json.dumps(value, separators=(',',':'))

#print(cleanedJSON)
#	print ("key: " + str(key))
#	print("value: " + str(value))
#	counter += 1
#	print(counter)
		# filename = "./ucla_engineering/{}".format(key) + ".txt"
		# os.makedirs(os.path.dirname(filename), exist_ok=True)
		# with open(filename, "w") as f:
		# 	f.write(menuJSON)
		# 	f.close()


#Hey Matt, I messed around with it, and I think it's better if we don't for-loop and just throw in the entire
#dataDict inside the json.dumps. If we do it that way, the xml will actually give the values a id type.
#With the for-loop, there's no indicator of which ones id/author/book/url. 
#You'd have to eye-ball it to see. But i think if we put the id type, it'll be easier for the people using the XML
#To extract the information.
cleanedJSON = json.dumps(dataDict)
loadedJSON = json.loads(cleanedJSON)
xml = dicttoxml.dicttoxml(loadedJSON)
print(xml)
print("\n\n")

# This creates the file for the Library
filename = "./json_to_xml/{}".format(xml) + ".xml"
os.makedirs(os.path.dirname(filename), exist_ok=True)
with open(filename, "w") as f:
	f.write(menuJSON)
	f.close()

# print(obj)
