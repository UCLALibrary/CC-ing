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

	for key, value in authors:
		print(authors[key])

	max = 0
	name = ""
	tmax = 0 
	tname = ""
	for key, value in authors:
		if(authors[key] > max):
			name = key
			max = authors[key]
		else:
			name = key
			max = authors[key]


	for key, value in titles:
		if(titles[key] > tmax):
			tname = key
			tmax = titles[key]
		else:
			tname = key
			tmax = titles[key]

	dataDict["author"].append(name)
	dataDict["title"].append(tname)

	# print("authors: " + str(authors))
	# If authors is NOT empty
	if(authors):
		dataDict["author"].append(authors)
	# print ("titles: " + str(titles))
	if(titles):
		dataDict["title"].append(titles)

	# Now, we have all the assorted metadata

	for key, value in dataDict.items():
		cleanedJSON = json.dumps(value, separators=(',', ':'))

		print ("key: " + str(key))
		print("value: " + str(value))

		# filename = "./ucla_engineering/{}".format(key) + ".txt"
		# os.makedirs(os.path.dirname(filename), exist_ok=True)
		# with open(filename, "w") as f:
		# 	f.write(menuJSON)
		# 	f.close()

		loadedJSON = json.loads(cleanedJSON)

		xml = dicttoxml.dicttoxml(loadedJSON)
		print(xml)
		print("\n\n")

# print(obj)
