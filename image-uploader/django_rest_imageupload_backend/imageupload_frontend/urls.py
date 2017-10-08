from django.conf.urls import url, include
from django.views.generic.base import RedirectView
from . import views
# Redirect any request that goes into here to static/index.html
urlpatterns = [
    url(r'^$', views.index, name='index')
]
