from django.shortcuts import render

def index(request):
	return render(request, 'imageupload_frontend/index.html')
# Create your views here.
