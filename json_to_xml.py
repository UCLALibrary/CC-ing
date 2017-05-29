import json
import urllib
import dicttoxml

page = urllib.urlopen('http://ec2-54-173-153-28.compute-1.amazonaws.com:8000/subjects')
content = page.read()
obj = json.loads(content)

# Parse only this information
# ID of image
# URL of image
# Most popular image for author
# Most popular image for title
dataDict = []

#  To handle dictionary of list of dictionary
for key, value in obj.items():
	# data is the only meaningful key
	if(key == "data"):
		print("KEY: " + str(key))
		print("VALUES: " + str(value))
		print("\n\n\n\n\n")

		# To handle list of dictionary
		count = 0
		for i in value:
			print("i: " + str(i))

			print("ID of the image: " + str(value[count]["subject_set_id"]))
			print("URL of the image: " + str(value[count][]))


			count += 1
			print("\n\n\n\n\n")




# print(obj)
# print("\n\n\n\n\n\n\n\n\n\n")

#xml = dicttoxml.dicttoxml(obj)
#print(xml)
