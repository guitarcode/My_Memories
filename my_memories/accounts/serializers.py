from .models import User
from rest_framework import serializers
from django.contrib.auth.hashers import make_password
from dj_rest_auth.registration.serializers import RegisterSerializer

class UserSerializers(serializers.HyperlinkedModelSerializer):

    password = serializers.CharField(
        write_only=True,
        required=True,
        help_text='Leave empty if no change needed',
        style={'input_type': 'password'}
    )

    class Meta:
        model = User
        fields = ['id','username','nickname','password', 'email','birthday']

    def create(self, validated_data):
        validated_data['password'] = make_password(validated_data.get('password'))
        return super(UserSerializers, self).create(validated_data)

class CustomRegisterSerializer(RegisterSerializer):
    nickname = serializers.CharField(max_length=32)
    birthday = serializers.DateField()

    def get_cleaned_data(self):
        data = super().get_cleaned_data()
        data['nickname'] = self.validated_data.get('nickname', '')
        data['birthday'] = self.validated_data.get('birthday', '')
        return data


