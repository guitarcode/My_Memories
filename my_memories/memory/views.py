from django.shortcuts import render
from django.contrib.auth.decorators import login_required
from rest_framework.viewsets import ModelViewSet
from .models import Memory
from .serializers import MemorySerializers

class MemoryViewSet(ModelViewSet):
    queryset = Memory.objects.all()
    serializer_class = MemorySerializers

    # serializer.save() 재정의
    def perform_create(self, serializer):
        serializer.save(user=self.request.user)

# Create your views here.
