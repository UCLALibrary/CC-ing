import requests

OCR_POST_URL = "http://ec2-54-173-153-28.compute-1.amazonaws.com:8080/fileAPI/imageURL"
url = "https://codropspz-tympanus.netdna-ssl.com/codrops/wp-content/uploads/2015/02/TextFill_image5.png"
r = requests.post(OCR_POST_URL, data={'imageURL': url})
print r.json()['ocrDetailResult']
