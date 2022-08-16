from .models import User
from django.shortcuts import render
from .serializers import UserSerializers
from rest_framework import generics, mixins
from rest_framework.viewsets import ReadOnlyModelViewSet, ModelViewSet

class UserViewSet(ModelViewSet):
    queryset = User.objects.all()
    serializer_class = UserSerializers

# Create your views here.
