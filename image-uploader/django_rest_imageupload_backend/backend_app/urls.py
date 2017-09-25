from django.conf.urls import url, include
from django.contrib import admin
from django.conf import settings
from django.conf.urls.static import static
from documents.views import ocr_view, ocr_form_view

# Routing of our Django App

urlpatterns = [
    # anything going to '/' should be redirected to the urls of imageupload_frontend
    url(r'^', include('imageupload_frontend.urls', namespace='frontend')),
    # anything going to /admin gets redirected to the admin stuff (makes sense...)
    url(r'^admin/', admin.site.urls),
    # anything going to /api gets redirected to the django rest API stuff
    url(r'^api/', include('imageupload_rest.urls', namespace='api')),
    url(r'^ocr/', ocr_view, name='ocr_view'),
    url(r'^imageocr/', ocr_form_view, name='ocr_form_view'),
] + static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)
# we are serving static and media files here at the moment - if we deploy this app to a server, we do necessarily want this

