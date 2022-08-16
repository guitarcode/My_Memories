from django.db import models
from django.contrib.auth.models import AbstractUser


class User(AbstractUser):
    nickname = models.CharField(max_length=32, null=True)
    birthday = models.DateField(null=True)

    class Meta:
        ordering = ('-id',)
