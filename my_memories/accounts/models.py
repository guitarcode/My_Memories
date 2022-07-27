from django.db import models
from django.contrib.auth.models import AbstractUser, UserManager


class MyUserManager(UserManager):
    def _create_user(self, username, email, password, **kwargs):
        if not username:
            raise ValueError('id는 필수입니다.')
        user = self.model(username = username,email=self.normalize_email(email), **kwargs)
        user.set_password(password)
        user.save()
        return user

    def create_user(self, username, email=None, password=None, **kwargs):
        kwargs.setdefault('is_superuser', False)
        kwargs.setdefault("is_staff", False)
        return self._create_user(username, email, password, **kwargs)

    def create_superuser(self, username, email=None, password=None, **kwargs):
        kwargs.setdefault("is_staff", True)
        kwargs.setdefault('is_superuser', True)
        return self._create_user(username, email, password, **kwargs)


class User(AbstractUser):
    nickname = models.CharField(max_length=32, null=True)
    birthday = models.DateField(null=True)
    objects = MyUserManager()

    class Meta:
        ordering = ('-id',)
