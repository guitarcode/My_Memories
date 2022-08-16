from django.contrib.auth.models import User
from rest_framework import serializers
from .models import Memory

class MemorySerializers(serializers.HyperlinkedModelSerializer):
    user = serializers.ReadOnlyField(source='user.username')

    class Meta:
        model = Memory
        fields = ['id','user','date','body']

