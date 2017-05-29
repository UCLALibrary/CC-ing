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
for key, value in obj.items():
	print("KEY: " + str(key))
	print("VALUES: " + str(value))
	print("\n")


# print(obj)
print("\n\n\n\n\n\n\n\n\n\n")

#xml = dicttoxml.dicttoxml(obj)
#print(xml)
